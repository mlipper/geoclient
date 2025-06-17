plugins {
    `java-library`
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
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}
