plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.jacksonDatabind)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.12.1")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
