plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
	implementation(project(":build-parameters"))
	implementation(kotlin("gradle-plugin"))
}