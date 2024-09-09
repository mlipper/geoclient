plugins {
    alias(libs.plugins.buildParameters)
}

// TODO: Needed?
// group = "geoclientbuild"

buildParameters {
    pluginId("geoclientbuild.build-parameters")
    bool("ci") {
        fromEnvironment()
        defaultValue.set(false)
    }
    bool("javaUseSystemProxy") {
        fromEnvironment("JAVA_USE_SYSTEM_PROXY")
        defaultValue.set(true)
    }
    group("documentation") {
        description.set("Parameters needed for building documentation samples.")
        string("key") {
            fromEnvironment("DOCUMENTATION_KEY")
        }
        string("endpoint") {
            fromEnvironment("DOCUMENTATION_ENDPOINT")
            defaultValue.set("http://localhost:8080/geoclient/v2")
        }
    }
    group("testing") {
        description.set("Parameters needed for unit, functional and integration tests.")
        string("key") {
            fromEnvironment("TESTING_KEY")
            defaultValue.set("UNSET")
        }
        string("endpoint") {
            fromEnvironment("TESTING_ENDPOINT")
            defaultValue.set("http://localhost:8080/geoclient/v2")
        }
    }
}