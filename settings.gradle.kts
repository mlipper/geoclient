pluginManagement {
    includeBuild("gradle/plugins")
    repositories.gradlePluginPortal()
}

rootProject.name = "geoclient"

include("gorp")
//include("documentation")
//include("geoclient-jni")
//include("geoclient-core")
//include("geoclient-parser")
//include("geoclient-service")
//include("geoclient-test")
//include("geoclient-utils:cli")
//include("geoclient-utils:jni-test")
