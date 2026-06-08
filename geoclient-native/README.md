# geoclient-native

This module builds the host-native JNI shared library used by `geoclient-jni`.

## Scope

- Owns native `C` sources and headers formerly in `geoclient-jni`.
- Builds exactly one host-native binary:
  - Linux host: `libgeoclientjni.so` (`linux-x64` classifier)
  - Windows host: `geoclientjni.dll` (`windows-x64` classifier)
- Publishes the native artifact through `nativeBinaryElements` and Maven publication.

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
- `classifier`: host variant (`linux-x64` or `windows-x64`)

Example local dependency coordinate shape:

- `group:geoclient-native:version:linux-x64`
- `group:geoclient-native:version:windows-x64`

## Integration with geoclient-jni

`geoclient-jni` resolves the `nativeBinaryElements` configuration from this module and syncs the resulting binary into:

`build/generated-resources/main/gov/nyc/doitt/gis/geoclient/jni/<variant>/`

That preserves the existing runtime loading contract used by the JNI loader.
