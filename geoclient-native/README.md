# geoclient-native

This module builds the host-native JNI shared library used by `geoclient-jni`.

## Scope

- Owns native `C` sources and headers formerly in `geoclient-jni`.
- Builds host-native artifacts for Linux and Windows x86_64.
- Publishes native artifacts through Gradle variant-aware configurations and Maven publication.

## Linkage Modes

Linkage is controlled by Gradle property `nativeLinkage`:

- `shared` (default): build/publish shared JNI library.
- `static`: build/publish static library only.
- `both`: build/publish both shared and static.

Commands:

```sh
# Default shared mode
./gradlew :geoclient-native:buildNative

# Static only
./gradlew :geoclient-native:buildNative -PnativeLinkage=static

# Shared + static
./gradlew :geoclient-native:buildNative -PnativeLinkage=both
```

If an invalid `nativeLinkage` value is provided, the build fails fast.

## Variant Attributes

Published Gradle variants expose these attributes:

- `org.gradle.usage`
  - shared: `native-runtime`
  - static: `native-link`
- `org.gradle.category`: `library`
- `org.gradle.libraryelements`
  - shared: `shared-library`
  - static: `static-library`
- `org.gradle.native.operatingSystem`: host OS (`linux` or `windows`)
- `org.gradle.native.architecture`: `x86-64`
- `gov.nyc.geoclient.native.linkage`
  - shared: `shared`
  - static: `static`

Outgoing configurations:

- shared: `nativeBinaryElements`
- static: `nativeStaticElements`

## Key Tasks

```sh
# Build/export host-native binary in build/native/<variant>/
./gradlew :geoclient-native:buildNative

# Publish host-native classified artifact to local Maven repository
./gradlew :geoclient-native:publishToMavenLocal
```

## Artifact Coordinates

Maven publication:

- `group`: inherited from root project
- `artifactId`: `geoclient-native`
- `version`: inherited from root project
- `classifier`:
  - shared Linux: `linux-x64`
  - shared Windows: `windows-x64`
  - static Linux: `linux-x64-static`
  - static Windows: `windows-x64-static`

Example local dependency coordinate shape:

- shared: `group:geoclient-native:version:linux-x64`
- shared: `group:geoclient-native:version:windows-x64`
- static: `group:geoclient-native:version:linux-x64-static`
- static: `group:geoclient-native:version:windows-x64-static`

## Integration with geoclient-jni

`geoclient-jni` resolves the shared variant (`nativeBinaryElements`) from this module and syncs the binary into:

`build/generated-resources/main/gov/nyc/doitt/gis/geoclient/jni/<variant>/`

That preserves the existing runtime loading contract used by the JNI loader.
