import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

plugins {
  id("io.gitlab.arturbosch.detekt")
}

// Per-module DSL surface. Consumers write, e.g.:
//   swdevsmDetekt {
//       enabled = false
//   }
// Modelled as an interface so Gradle instantiates it via ObjectFactory —
// the abstract Property accessor is wired automatically.
interface SwdevsmDetektExtension {
  val enabled: Property<Boolean>
}

val swdevsmDetekt = extensions.create<SwdevsmDetektExtension>("swdevsmDetekt").apply {
  enabled.convention(true)
}

// Materializes the packaged swdevsm default config into the build directory.
// detekt's `config` is a file collection, so the jar resource cannot be
// consumed directly. Materializing through a task keeps the file collection's
// task dependency in place and stays configuration-cache safe.
abstract class SwdevsmDetektConfigTask : DefaultTask() {
  @get:OutputFile
  abstract val outputFile: RegularFileProperty

  @TaskAction
  fun materialize() {
    val resource = checkNotNull(javaClass.classLoader.getResource("swdevsm-detekt.yml")) {
      "Packaged resource swdevsm-detekt.yml not found in the detekt-conventions plugin jar."
    }
    outputFile.get().asFile.writeBytes(resource.readBytes())
  }
}

val swdevsmDetektConfig = tasks.register("swdevsmDetektConfig", SwdevsmDetektConfigTask::class.java) {
  outputFile.set(layout.buildDirectory.file("detekt/swdevsm-detekt.yml"))
}

// detekt engine version is read from a Gradle property so consumer repos
// can override it in their own gradle.properties without editing this plugin.
val detektVersion: String = providers.gradleProperty("detekt.version").getOrElse("1.23.8")

configure<DetektExtension> {
  // NOTE: the packaged default is supplied via `setFrom` here. Consumers must
  // layer their own config with `config.from(...)` — calling `config.setFrom(...)`
  // themselves would wipe the packaged swdevsm defaults.
  config.setFrom(swdevsmDetektConfig.flatMap { it.outputFile })
  buildUponDefaultConfig = true
  allRules = false
  ignoreFailures = false
  toolVersion = detektVersion
}

// Report defaults for the swdevsm profile, stated explicitly so future detekt
// default changes cannot silently alter it. Chosen on detekt's own merits and
// independent of any other convention plugin a consumer may apply.
tasks.withType<Detekt>().configureEach {
  reports {
    html.required.set(true)
    xml.required.set(true)
    txt.required.set(false)
    sarif.required.set(false)
    md.required.set(false)
  }
}

// Apply user-facing overrides after the consumer's build.gradle.kts has
// finished configuring the `swdevsmDetekt { }` extension.
afterEvaluate {
  if (!swdevsmDetekt.enabled.get()) {
    tasks.matching { it.name.startsWith("detekt") }.configureEach {
      enabled = false
    }
  }
}