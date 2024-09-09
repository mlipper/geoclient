dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../libs.versions.toml"))
        }
    }
    //repositories.gradlePluginPortal()
}

include("build-parameters")
include("common")
include("generate-samples")