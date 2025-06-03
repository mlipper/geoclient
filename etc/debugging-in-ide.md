# Debugging in an IDE

Last update: 2025-06-03

## VS Code

This used to be straightforward but on 2025/06/03, multiple strategies failed
when trying to debug `SingleFieldSearchHandlerTest`.

* Project is running in a Linux Dev Container.
* Using standard "Extension Pack for Java" which includes the Gradle extension.

Debugging integration test `SingleFieldSearchHandlerTest` kept failing because `version.properties` could not be found.
Finally, the following steps worked:

1. Change `AppConfig` by adding an annotation option to ignore the missing file.

   ```java
   @PropertySource(value = "classpath:version.properties", ignoreResourceNotFound = true)
   ```

   Note that this is a terrible strategy because the /version endpoint will be broken.

2. Stop the gradle daemon which seems to confuse the IDE. From the shell, run:

   ```sh
   ./gradlew --stop
   ```

3. Go to the "Testing" view and navigate to the test suite or test you want to debug and click the debug icon.

   `geoclient-service` -> `SingleFieldSearchHandlerTest` -> `testFindLocationDefaultPolicy_placeWithBorough`

Hopefully, this is a transient issue and some IDE component or extension starts behaving again.
