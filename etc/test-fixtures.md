# Test Fixtures Refactoring

## Test Fixtures and Tools

| Project        | Source Folder | Package                              | Class                             |
| -------------- | ------------- | ------------------------------------ | --------------------------------- |
| geoclient-jni  | testFixtures  | gov.nyc.doitt.gis.geoclient.jni.test | GeoclientStub                     |
| geoclient-test | main          | gov.nyc.doitt.gis.geoclient.test     | GeosupportIntegrationTest         |
| geoclient-test | main          | gov.nyc.doitt.gis.geoclient.test     | IntegrationTestSupport            |
| geoclient-test | main          | gov.nyc.doitt.gis.geoclient.test     | LogLevelAdapter                   |
| geoclient-test | main          | gov.nyc.doitt.gis.geoclient.test     | NativeIntegrationTest             |
| geoclient-test | main          | gov.nyc.doitt.gis.geoclient.test     | RequiresRestServiceCustomCondtion |
| jni-test       | main          | gov.nyc.doitt.gis.geoclient.jni.test | ByteBufferUtils                   |
| jni-test       | main          | gov.nyc.doitt.gis.geoclient.jni.test | ConfigInitializer                 |
| jni-test       | main          | gov.nyc.doitt.gis.geoclient.jni.test | TestConfig                        |
| jni-test       | main          | gov.nyc.doitt.gis.geoclient.jni.test | TestConfigurationException        |
| jni-test       | main          | gov.nyc.doitt.gis.geoclient.jni.test | TestFileParser                    |

Dependents of `GeoclientStub`:

* geoclient-core - src/test
  * gov.nyc.doitt.gis.geoclient.config.FunctionConfigTest 
  * gov.nyc.doitt.gis.geoclient.config.FunctionToCsv

Dependents of `ByteBufferUtils`:

* geoclient-jni - src/integrationTest
  * gov.nyc.doitt.gis.geoclient.jni.GeoclientJniIntegrationTest
* cli - src/main
  * gov.nyc.doitt.gis.geoclient.cli.JniTest

Dependents of `ConfigInitializer`:

* geoclient-jni - src/integrationTest
  * gov.nyc.doitt.gis.geoclient.jni.GeoclientJniIntegrationTest

Dependents of `TestFileParser`:

* cli - src/main
  * gov.nyc.doitt.gis.geoclient.cli.JniTest
