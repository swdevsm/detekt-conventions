# detekt-conventions

[![Publish](https://github.com/swdevsm/detekt-conventions/actions/workflows/publish.yml/badge.svg)](https://github.com/swdevsm/detekt-conventions/actions/workflows/publish.yml) [![Maven Central](https://img.shields.io/maven-central/v/ru.swdevsm/detekt-conventions)](https://central.sonatype.com/artifact/ru.swdevsm/detekt-conventions)

Precompiled script plugin `ru.swdevsm.detekt-conventions` (kotlin-dsl) that configures detekt with swdevsm static-analysis defaults.

## Status

- **Build & push**: `.github/workflows/publish.yml` — runs `./gradlew publishToMavenCentral --no-configuration-cache` on GitHub release (`released`/`prereleased`) or manual `workflow_dispatch`.
- **Maven Central**: artifact `ru.swdevsm:detekt-conventions` (see badge above for latest published version).

## Usage

```kotlin
plugins {
    id("ru.swdevsm.detekt-conventions") version "<version>"
}
```

Configures the detekt Gradle plugin (`1.23.8`) with swdevsm defaults.

## Build

- `./gradlew build` (from inside this repo)
- Kotlin 2.3.10, JVM 21 toolchain.
- No tests.
- Resolves `ru.swdevsm.publishing-conventions` at `0.0.1` — publish it to `mavenLocal` first if resolution fails.