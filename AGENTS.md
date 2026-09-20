# detekt-conventions

Precompiled script plugin `ru.swdevsm.detekt-conventions` (kotlin-dsl) that configures detekt with swdevsm static-analysis defaults. No tests.

## Build

- Build: `./gradlew build` (from inside this repo)
- Kotlin 2.3.10, JVM 21 toolchain.
- Wraps `io.gitlab.arturbosch.detekt:detekt-gradle-plugin` (detekt `1.23.8`).
- Resolves `ru.swdevsm.publishing-conventions` at `0.0.1` — publish it to `mavenLocal` first if resolution fails.

## Structure

- Plugin implementation: `src/main/kotlin/ru.swdevsm.detekt-conventions.gradle.kts`.
- Publishes artifact `detekt-conventions` via the swdevsm publishing conventions (`githubRepo` = `swdevsm/detekt-conventions`).
- CI: `.github/workflows/publish.yml` — `./gradlew publishToMavenCentral --no-configuration-cache` on GitHub release.