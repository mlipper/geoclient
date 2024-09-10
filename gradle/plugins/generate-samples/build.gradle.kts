plugins {
    id("groovy")
    id("java-gradle-plugin")
    //id "checkstyle"
    //id "project-report"
    //alias(libs.plugins.spotless)
    //alias(libs.plugins.versions)
    // Do not include the build-parameters plugin.
    // Instead, declare a project dependency.
}

repositories {
    gradlePluginPortal()
}

//tasks.withType(JavaCompile) {
//    options.encoding = "UTF-8"
//    options.compilerArgs.addAll("-Werror", "-Xlint:unchecked", "-Xlint:deprecation", "-Xlint:rawtypes","-Xlint:varargs")
//}

dependencies {
    implementation(project(":build-parameters"))
    implementation(kotlin("gradle-plugin"))
    //implementation libs.spotless.plugin
    //implementation libs.gradle.versions.plugin
    // Platforms
    implementation(platform(libs.spring.boot.dependencies))
    testImplementation(platform(libs.junit.bom))
    // Dependencies
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation(libs.httpclient)
    implementation(libs.jackson.databind)
    testImplementation(gradleTestKit())
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
    }
    environment("TESTING_KEY"          , System.getenv("TESTING_KEY"))
    environment("TESTING_ENDPOINT"     , System.getenv("TESTING_ENDPOINT"))
    environment("JAVA_USE_SYSTEM_PROXY", System.getenv("JAVA_USE_SYSTEM_PROXY"))
}

gradlePlugin {
    plugins {
        create("documentationPlugin") {
            id = "geoclientbuild-documentation"
            implementationClass = "geoclientbuild.docs.DocumentationPlugin"
        }
    }
}