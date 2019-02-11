package gov.nyc.doitt.gis.geoclient.gradle

import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.util.Map

class GeoclientExtension extends AbstractExtension {

    static final String GC_JNI_VERSION_GRADLE = 'gcJniVersion'
    static final String GC_JNI_VERSION_SYSTEM = 'gc.jni.version'
    static final String GC_JNI_VERSION_ENVVAR = 'GC_JNI_VERSION'

    // Default is dynamic so there is no constant 'DEFAULT_GC_JNI_VERSION'

    private final Property<String> jniVersion

    GeoclientExtension(Project project) {
        super(project)
        this.jniVersion = project.objects.property(String)
    }

    void resolveConventions() {
        resolveConvention(GC_JNI_VERSION_GRADLE, GC_JNI_VERSION_SYSTEM, GC_JNI_VERSION_ENVVAR, jniVersion, project.version)
    }

    Property<String> getJniVersion() {
        this.jniVersion
    }

    void setJniVersion(String value) {
        this.jniVersion.set(value)
    }

    Map<String, Object> environment() {
        [GC_JNI_VERSION_ENVVAR: this.jniVersion.getOrNull()]
    }

    Map<String, Object> systemProperties() {
        [GC_JNI_VERSION_SYSTEM: this.jniVersion.getOrNull()]
    }

}