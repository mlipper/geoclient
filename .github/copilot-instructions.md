# Geoclient — Copilot Instructions

## What this project is

Geoclient is a Java/JNI wrapper around the NYC Department of City Planning's **Geosupport** geocoding C library, exposing it as a Spring Boot REST service. Java calls into C via JNI; the C layer calls Geosupport shared libraries that must be installed at `/opt/geosupport/current`. Geoclient provides a convenient way to access Geosupport functionality from Java applications without directly dealing with the native C library. It also adds single-field search capabilities and other utilities to simplify working with Geosupport data.

## Build, test, and lint

```sh
# Build everything
./gradlew build

# Unit tests only (no native library needed)
./gradlew test

# Integration tests (require Geosupport installed at /opt/geosupport/current)
./gradlew integrationTest

# Single test class (replace module and class name)
./gradlew :geoclient-service:test --tests "gov.nyc.doitt.gis.geoclient.service.web.RestControllerTest"

# Run the service locally (uses 'bootrun' Spring profile)
./gradlew :geoclient-service:bootRun

# Check and auto-fix formatting
./gradlew spotlessCheck
./gradlew spotlessApply

# Check style
./gradlew checkstyle
```

The `check` task runs both `test` and `integrationTest`. Use `test` alone when Geosupport is not available.

## Module layout

| Module | Role |
|---|---|
| `platform` | Spring Boot BOM — controls most dependency versions |
| `geoclient-jni` | JNI bridge: Java + C source (`src/main/c/`), builds `libgeolientjni.so` |
| `geoclient-xml` | Jackson-based reader for `geoclient.xml` (Geosupport field/function definitions) |
| `geoclient-core` | Core abstractions: `GeosupportFunction`, `WorkArea`, `Field`, `Filter` |
| `geoclient-parser` | Regex-based NLP for parsing free-form NYC location strings |
| `geoclient-service` | Spring Boot 4 REST service — the deployable artifact |
| `geoclient-cli` | Command-line tool for direct JNI testing |
| `geoclient-test` | Shared test infrastructure (`GeosupportIntegrationTest`, `NativeIntegrationTest`) |
| `buildSrc` | Convention plugins + build tooling (Groovy + Java) |

## Architecture

Request flow:

```
HTTP GET /geoclient/v2/address
  → RestController
  → GeosupportService (GeosupportServiceImpl)
  → GeosupportFunction.call(Map<String,Object>)
  → GeoclientJni.callgeo(ByteBuffer wa1, ByteBuffer wa2)
  → C: geoclient.c → Geosupport callgeo()
  → response Map populated from WorkArea fields
  → GeosupportResponse (JSON/XML)
```

**Geosupport work areas**: Geosupport communicates through two fixed-length byte buffers (`WA1`, `WA2`). The field layout (position, length, alias, input flag) for every function is declared in `geoclient-core/src/main/resources/geoclient.xml` and loaded by `GeoclientXmlReader` at startup into `WorkArea`/`Field` objects. Adding or changing Geosupport function support means editing this XML file.

**Spring profiles**: `bootrun` (local dev), `quiet`, `accesslog`, `docsamples`. The `bootrun` profile enables actuator endpoints and file logging to `build/bootrun.log`.

**Context path**: `/geoclient/v2` (configurable in `application.yml`).

## Key conventions

### Java
- **Java 21**, strict compilation: `-Werror -Xlint:unchecked -Xlint:deprecation -Xlint:rawtypes -Xlint:varargs`. Compiler warnings are errors.
- All Java and Groovy source files **must** have the Apache License 2.0 header (enforced by Spotless). See `gradle/config/spotless/apache-license-2.0.java`.
- Formatting is enforced by **Spotless** using the Eclipse formatter config at `gradle/config/eclipse/geoclient-eclipse-formatter-settings.xml`. Run `./gradlew spotlessApply` before committing.
- Checkstyle config lives in `gradle/config/checkstyle/`.

### Testing
- **Unit tests** live in `src/test/java`; **integration tests** in `src/integrationTest/java`; shared fixtures in `src/testFixtures/java`.
- Integration tests that call Geosupport natively require the env var `GEOFILES=/opt/geosupport/current/fls/` and the native libraries to be present.
- Mockito requires a special JVM agent configuration (see `geoclient.test-conventions.gradle`) due to JDK 21 instrumentation restrictions. Do not remove the `mockitoAgent` configuration.
- JaCoCo coverage merges `test.exec` and `integrationTest.exec`; coverage reports go to `build/reports/jacoco/`.

### Convention plugins (buildSrc)
Convention plugins are applied by name, not by class:
- `geoclient.java-conventions` — Java 21 toolchain, Spotless, Checkstyle, versions plugin
- `geoclient.library-conventions` — extends java-conventions, adds `java-library` + Maven publishing
- `geoclient.test-conventions` — adds `integrationTest` source set, JaCoCo, JUnit 5, Mockito agent
- `geoclient.jar-conventions` — shared JAR manifest

### Spring Boot version
Spring Boot version **must be kept in sync** across three places: `gradle/libs.versions.toml`, `buildSrc/build.gradle`, and `platform/build.gradle`. See comments in each file.

### JNI native library loading
The `geoclientjni` shared library is bundled inside the `geoclient-jni` jar and unpacked at runtime to `${java.io.tmpdir}/${gc.jni.version}/`. The property `gc.jni.version` defaults to `geoclient-jni-2` (set in `gradle.properties`). `NativeLibraryLoader` handles the unpacking; `JniContext` provides the paths.

### Service REST endpoints
Both `RestController` and `SingleFieldSearchController` are located in `gov.nyc.doitt.gis.geoclient.service.web` package. Endpoints are defined as constants in each controller (e.g., `ADDRESS_URI = "/address"` and `SEARCH_URI = "/search"`). Each endpoint in `RestController` maps to a `ServiceType` enum entry that also names the JSON wrapper element.

The `/search` endpoint in `SingleFieldSearchController` is used for single-field searches, while other endpoints in `RestController` handle location-related queries with discrete input parameters that map to specific Geosupport functions. Single-field search relies heavily on the `geoclient-parser` module to parse and validate input parameters before invoking the appropriate Geosupport function. Both controllers are organized in `geoclient-service/src/main/java/gov/nyc/doitt/gis/geoclient/service/web/`.

**Design Note:** `RestController` and `SingleFieldSearchController` are kept as separate classes despite being in the same package to maintain clear separation of concerns—they handle fundamentally different input patterns and processing strategies (discrete parameters vs. free-form string parsing).

## TODO
1. Refactor single-field search handling (except for web or Spring aware classes) into a separate standalone module that can be used in other types of applications. E.g., from `geoclient-cli`.
2. Begin using the `testcontainers` library for integration tests to allow for development without requiring DevContainers on macOS which is not currently supported by the native Geosupport libraries.
3. Create API documentation (Javadoc) for all public and protected classes and methods.
4. Create metadata to begin using GitHub Actions for CI/CD.
5. Update the `geoclient-parser` project to recognize apartment/suite/floor information in single-field search queries. This will improve the accuracy of single-field searches by discarding this information when not needed for calling Geosupport functions and ensuring that the correct Geosupport function is invoked based on the relevant address components.