/*
 * Copyright 2013-2025 the original author or authors.
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
package geoclientbuild.docs;

import static org.gradle.api.plugins.JavaBasePlugin.DOCUMENTATION_GROUP;

import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskProvider;

public class DocumentationPlugin implements Plugin<Project> {

    public static final String GENERATE_SAMPLES_TASK_NAME = "generateSamples";
    public static final String SAMPLES_EXTENSION_NAME = "samples";
    public static final String SYNC_SAMPLES_TASK_NAME = "syncSamples";
    public static final String DEFAULT_DOCUMENTATION_TASK_NAME = "asciidoctor";
    public static final String DEFAULT_SAMPLE_REQUESTS_FILE = "src/main/resources/requests.json";
    public static final String DEFAULT_SAMPLES_RESPONSE_DIR = "generated/samples";
    public static final String DEFAULT_SAMPLES_SOURCE_DIR = "src/docs/asciidoc/samples";

    private Logger logger = Logging.getLogger(DocumentationPlugin.class);

    public void apply(Project project) {
        logger.info("Applying DocumentationPlugin...");
        SamplesExtension extension = registerExtension(project);
        TaskProvider<GenerateSamplesTask> generateSamplesTaskProvider = project.getTasks().register(GENERATE_SAMPLES_TASK_NAME, GenerateSamplesTask.class, t -> {
            t.setGroup(DOCUMENTATION_GROUP);
            t.setDescription("Write JSON responses to geoclient REST calls as files in an output folder.");
            t.getRequestsFile().convention(extension.getSampleRequestsFile());
            t.getDestinationDirectory().convention(extension.getSamplesResponseDirectory());
        });
        project.getTasks().register(SYNC_SAMPLES_TASK_NAME, Sync.class, t -> {
            t.setGroup(DOCUMENTATION_GROUP);
            t.setDescription("Copy generated samples to the main source set.");
            // Implies dependsOn relationship with GenerateSamplesTask.
            t.from(generateSamplesTaskProvider.get());
            t.into(extension.getSamplesSourceDirectory().get());
        });
        project.getPluginManager().withPlugin("applyAsciiDoctorPlugin", (plugin) -> {
            project.getTasks().withType(AbstractAsciidoctorTask.class, task -> {
                if(extension.getSyncSamples().getOrElse(false)) {
                    task.dependsOn(SYNC_SAMPLES_TASK_NAME);
                }
            });
        });
    }

    private SamplesExtension registerExtension(Project project) {
        SamplesExtension extension = project.getExtensions().create(SAMPLES_EXTENSION_NAME, SamplesExtension.class);
        extension.getDocumentationTaskName().set(DEFAULT_DOCUMENTATION_TASK_NAME);
        extension.getSampleRequestsFile().convention(project.getLayout().getProjectDirectory().file(DEFAULT_SAMPLE_REQUESTS_FILE));
        extension.getSamplesResponseDirectory().convention(project.getLayout().getBuildDirectory().dir(DEFAULT_SAMPLES_RESPONSE_DIR));
        extension.getSamplesSourceDirectory().convention(project.getLayout().getProjectDirectory().dir(DEFAULT_SAMPLES_SOURCE_DIR));
        extension.getSyncSamples().convention(true);
        return extension;
    }
}
