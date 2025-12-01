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

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DocumentationPlugin implements Plugin<Project> {

    public static final String GENERATE_SAMPLES_TASK_NAME = "generateSamples";
    private Logger logger = LoggerFactory.getLogger(DocumentationPlugin.class);

    public void apply(Project project) {
        logger.info("Applying DocumentationPlugin...");
        project.getTasks().register(GENERATE_SAMPLES_TASK_NAME, GenerateSamplesTask.class, t -> {
            t.setGroup(DOCUMENTATION_GROUP);
            t.setDescription("Write JSON responses to geoclient REST calls files in an output folder.");
            t.getRequestsFile().convention(
                project.getLayout().getBuildDirectory().file("resources/main/requests.json"));
            t.getDestinationDirectory().convention(project.getLayout().getBuildDirectory().dir("generated/samples"));
        });
    }
}
