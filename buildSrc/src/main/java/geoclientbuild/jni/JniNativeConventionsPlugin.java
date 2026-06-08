/*
 * Copyright 2013-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package geoclientbuild.jni;

import java.util.Map;
import java.util.Set;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.attributes.Category;
import org.gradle.api.attributes.LibraryElements;
import org.gradle.api.attributes.Usage;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.testing.Test;
import org.gradle.api.tasks.testing.logging.TestLogEvent;
import org.gradle.nativeplatform.MachineArchitecture;
import org.gradle.nativeplatform.OperatingSystemFamily;

public class JniNativeConventionsPlugin implements Plugin<Project> {

    private static final String NATIVE_BINARY_CLASSPATH_CONFIGURATION_NAME = "nativeBinaryClasspath";
    private static final String SYNC_JNI_LIB_TASK_NAME = "syncJniLib";
    private static final String INTEGRATION_TEST_TASK_NAME = "integrationTest";
    private static final String CLASSES_TASK_NAME = "classes";

    @Override
    public void apply(Project project) {
        final Attribute<String> linkageAttr = Attribute.of("gov.nyc.geoclient.native.linkage", String.class);
        final boolean hostIsWindows = System.getProperty("os.name").toLowerCase().contains("windows");
        final String hostOperatingSystem = hostIsWindows ? OperatingSystemFamily.WINDOWS : OperatingSystemFamily.LINUX;
        final String nativeVariantName = hostIsWindows ? "windows-x64" : "linux-x64";

        final Provider<org.gradle.api.file.Directory> generatedResourcesDirectory =
            project.getLayout().getBuildDirectory().dir("generated-resources/main");
        final Provider<org.gradle.api.file.Directory> nativeResourceOutputDir = project.getLayout().getBuildDirectory().dir(
            "generated-resources/main/gov/nyc/doitt/gis/geoclient/jni/" + nativeVariantName);
        final Provider<org.gradle.api.file.Directory> integrationTestExtractDir =
            project.getLayout().getBuildDirectory().dir("jni-extract/integrationTest");

        final Configuration nativeBinaryClasspath = project.getConfigurations().create(
            NATIVE_BINARY_CLASSPATH_CONFIGURATION_NAME, c -> {
                c.setCanBeConsumed(false);
                c.setCanBeResolved(true);
                c.getAttributes().attribute(Usage.USAGE_ATTRIBUTE, project.getObjects().named(Usage.class, "native-runtime"));
                c.getAttributes().attribute(Category.CATEGORY_ATTRIBUTE,
                    project.getObjects().named(Category.class, Category.LIBRARY));
                c.getAttributes().attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE,
                    project.getObjects().named(LibraryElements.class, "shared-library"));
                c.getAttributes().attribute(OperatingSystemFamily.OPERATING_SYSTEM_ATTRIBUTE,
                    project.getObjects().named(OperatingSystemFamily.class, hostOperatingSystem));
                c.getAttributes().attribute(MachineArchitecture.ARCHITECTURE_ATTRIBUTE,
                    project.getObjects().named(MachineArchitecture.class, MachineArchitecture.X86_64));
                c.getAttributes().attribute(linkageAttr, "shared");
            });

        project.getDependencies().add(NATIVE_BINARY_CLASSPATH_CONFIGURATION_NAME,
            project.getDependencies().project(Map.of("path", ":geoclient-native", "configuration", "nativeBinaryElements")));

        final var syncJniLibTaskProvider = project.getTasks().register(SYNC_JNI_LIB_TASK_NAME, Sync.class, t -> {
            t.dependsOn(nativeBinaryClasspath);
            t.from(nativeBinaryClasspath);
            t.into(nativeResourceOutputDir.get().getAsFile());
        });

        project.getExtensions().getByType(JavaPluginExtension.class)
            .getSourceSets()
            .named(SourceSet.MAIN_SOURCE_SET_NAME, sourceSet -> sourceSet.getOutput().dir(generatedResourcesDirectory.get().getAsFile()));

        project.getTasks().named(CLASSES_TASK_NAME).configure(t -> t.dependsOn(syncJniLibTaskProvider));

        project.getTasks().named(INTEGRATION_TEST_TASK_NAME, Test.class).configure(t -> {
            t.systemProperty("gc.jni.extract.dir", integrationTestExtractDir.get().getAsFile().getAbsolutePath());
            t.doFirst(task -> project.delete(integrationTestExtractDir.get().getAsFile()));
            t.setMaxParallelForks(1);
            t.getTestLogging().setEvents(Set.of(TestLogEvent.FAILED, TestLogEvent.SKIPPED));
            t.getTestLogging().getDebug().setEvents(Set.of(TestLogEvent.FAILED, TestLogEvent.SKIPPED));
            t.getTestLogging().getInfo().setEvents(Set.of(TestLogEvent.FAILED, TestLogEvent.SKIPPED));
        });
    }
}