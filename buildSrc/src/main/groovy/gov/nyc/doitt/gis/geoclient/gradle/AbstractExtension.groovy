package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.io.File
import java.util.Map

abstract class AbstractExtension {

    protected final Project project

    AbstractExtension(Project project) {
        this.project = project
    }

    abstract void resolveConventions()
    abstract Map<String, Object> environment()
    abstract Map<String, Object> systemProperties()

    void resolveConvention(String gradleName, String systemName, String envName, Property<String> property, String defaultValue) {
        Object value = gradleProperty(gradleName)
        if(value) {
            property.convention(value.toString())
            return
        }
        String stringValue = systemProperty(systemName)
        if(stringValue) {
            property.convention(stringValue)
            project.property(gradleName, stringValue)
            return
        }
        stringValue = environmentVariable(envName)
        if(stringValue) {
            property.convention(stringValue)
            project.property(gradleName, stringValue)
            return
        }
        property.convention(defaultValue)
        project.property(gradleName, defaultValue)
    }

    Object gradleProperty(String name) {
        project.findProperty(name)
    }

    String systemProperty(String name) {
        System.getProperty(name)
    }

    String environmentVariable(String name) {
        System.getenv(name)
    }
}