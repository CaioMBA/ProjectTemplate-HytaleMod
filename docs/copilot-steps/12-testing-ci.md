# 12 — Add Tests and GitHub Actions CI

Goal: make the template safer to reuse by adding unit tests and CI.

Add test dependencies in `build.gradle.kts`.

Recommended:

```kotlin
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.0")
    testImplementation("org.mockito:mockito-core:5.12.0")
}

tasks.test {
    useJUnitPlatform()
}
```

Use versions compatible with the current Gradle/Java setup.

Create:

```txt
src/test/java/dev/ofatech/hytale/template/
```

Add tests:

```txt
config/
  ConfigValidatorTest.java
  ConfigManagerTest.java

data/
  JsonDataStoreTest.java

events/
  SimpleEventBusTest.java

api/
  TemplateApiProviderTest.java

messages/
  MessageFormatterTest.java

util/
  IdentifiersTest.java
```

## Test requirements

### ConfigValidatorTest
Test:
- valid config passes
- enabled API with default token fails
- invalid port fails
- blank language fails

### JsonDataStoreTest
Test:
- save/load object
- missing object returns empty
- delete removes object
- path traversal key is rejected

Use temporary directories.

### SimpleEventBusTest
Test:
- subscriber receives event
- unsubscribe works
- failing subscriber does not block next subscriber

### TemplateApiProviderTest
Test:
- unavailable before registration
- get fails before registration
- register/get works
- double register fails
- unregister clears provider

### MessageFormatterTest
Test:
- replaces placeholders
- leaves missing placeholders unchanged
- handles empty placeholder map

## GitHub Actions

Create:

```txt
.github/workflows/build.yml
```

Suggested workflow:

```yaml
name: Build

on:
  push:
    branches:
      - main
      - master
  pull_request:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v6

      - name: Setup Java
        uses: actions/setup-java@v5
        with:
          distribution: temurin
          java-version: '25'

      - name: Make Gradle executable
        run: chmod +x ./gradlew

      - name: Build
        run: ./gradlew build
```

If `actions/checkout@v6` or `actions/setup-java@v5` are not accepted by the current environment, use the newest stable supported versions.

Implementation rules:
- Tests must not require a real Hytale server.
- Do not mock everything unnecessarily.
- Prioritize pure Java utilities.
- CI should run build and tests.
- Keep workflow simple.

Acceptance criteria:
- `./gradlew test` succeeds.
- `./gradlew build` succeeds.
- GitHub Actions workflow exists.
