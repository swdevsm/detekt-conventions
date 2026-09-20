plugins {
    `kotlin-dsl`
    alias(libs.plugins.swdevsm.publishing.conventions)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    mavenLocal()
}

dependencies {
    implementation(libs.detekt.gradle.plugin)
}

kotlin {
    jvmToolchain(21)
}

private val pluginId = "ru.swdevsm.detekt-conventions"

gradlePlugin {
    plugins {
        named(pluginId) {
            displayName = "swdevsm detekt conventions"
            description = "Convention plugin that configures detekt with swdevsm defaults."
        }
    }
}

swdevsmPublishing {
    artifactId.set("detekt-conventions")
    name.set("detekt-conventions")
    description.set(
        "Gradle convention plugin that configures detekt with swdevsm static-analysis defaults.",
    )
    githubRepo.set("swdevsm/detekt-conventions")
}