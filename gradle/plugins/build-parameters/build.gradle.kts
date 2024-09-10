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
    // This section was copied from the JUnit 5 project. See:
    // Copied from https://github.com/junit-team/junit5/blob/main/gradle/plugins/build-parameters/build.gradle.kts
	group("manifest") {
		string("buildTimestamp") {
			description = "Overrides the value of the 'Build-Date' and 'Build-Time' jar manifest entries. Can be set as a String (e.g. '2023-11-05 17:49:13.996+0100') or as seconds since the epoch."
			fromEnvironment("SOURCE_DATE_EPOCH") // see https://reproducible-builds.org/docs/source-date-epoch/
		}
		string("builtBy") {
			description = "Overrides the value of the 'Built-By' jar manifest entry"
		}
		string("createdBy") {
			description = "Overrides the value of the 'Created-By' jar manifest entry"
		}
	}
}