plugins {
    `java-library`
    `java-test-fixtures`
}

repositories {
    mavenCentral()
    mavenLocal()
    //flatDir {
    //    dirs(rootProject.file("libs"))
    //}
}

dependencies {
    implementation(libs.geoclientCore)
    implementation(libs.geoclientJni)
    implementation(libs.jacksonDatabind)
    implementation(libs.slf4J)
    runtimeOnly(libs.logbackClassic)
    runtimeOnly(libs.logbackCore)
    testImplementation(libs.junitJupiter)
    testRuntimeOnly(libs.junitPlatformLauncher)
    testFixturesImplementation(libs.geoclientCore)
    testFixturesImplementation(libs.geoclientJni)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        //showStandardStreams = true
    }
}
