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
package geoclientbuild.docs;

import static org.gradle.api.plugins.JavaBasePlugin.DOCUMENTATION_GROUP;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.JavadocMemberLevel;
import org.gradle.external.javadoc.StandardJavadocDocletOptions;

/**
 * Plugin that contributes a root-level aggregate Javadoc task and wires sources from Java subprojects.
 */
public class AggregateJavadocPlugin implements Plugin<Project> {

    public static final String PLUGIN_NAME = "geoclientbuild.aggregate-javadoc";
    public static final String AGGREGATE_JAVADOC_TASK_NAME = "aggregateJavadoc";
    public static final String AGGREGATE_JAVADOC_EXTENSION_NAME = "aggregateJavadocOptions";
    public static final String AGGREGATE_JAVADOC_OUTPUT_DIR = "docs/javadoc-aggregate";

    @Override
    public void apply(Project project) {
        AggregateJavadocExtension extension = project.getExtensions().create(AGGREGATE_JAVADOC_EXTENSION_NAME,
            AggregateJavadocExtension.class);

        project.getTasks().register(AGGREGATE_JAVADOC_TASK_NAME, Javadoc.class, task -> {
            task.setGroup(DOCUMENTATION_GROUP);
            task.setDescription("Generates unified Javadoc for all Java subprojects.");
            task.setDestinationDir(project.getLayout().getBuildDirectory().dir(AGGREGATE_JAVADOC_OUTPUT_DIR).get().getAsFile());

            if (task.getOptions() instanceof StandardJavadocDocletOptions options) {
                options.setEncoding("UTF-8");
                options.setMemberLevel(JavadocMemberLevel.PROTECTED);
                options.author(true);
                options.version(true);
            }
        });

        if (project.getState().getExecuted()) {
            configureTaskOptions(project, extension);
        } else {
            project.afterEvaluate(p -> configureTaskOptions(project, extension));
        }

        project.getSubprojects().forEach(subproject -> subproject.getPluginManager().withPlugin("java", p -> {
            project.getTasks().named(AGGREGATE_JAVADOC_TASK_NAME, Javadoc.class).configure(task -> {
                JavaPluginExtension javaExtension = subproject.getExtensions().getByType(JavaPluginExtension.class);
                SourceSet mainSourceSet = javaExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);

                task.source(mainSourceSet.getAllJava());
                task.setClasspath(task.getClasspath().plus(subproject.files(mainSourceSet.getCompileClasspath(), mainSourceSet.getOutput())));
            });
        }));
    }

    private void configureTaskOptions(Project project, AggregateJavadocExtension extension) {
        project.getTasks().named(AGGREGATE_JAVADOC_TASK_NAME, Javadoc.class).configure(task -> {
            task.setFailOnError(extension.isFailOnError());

            if (task.getOptions() instanceof StandardJavadocDocletOptions options) {
                if (extension.isQuiet()) {
                    options.addBooleanOption("quiet", true);
                }
                if (extension.isDisableDoclint()) {
                    options.addBooleanOption("Xdoclint:none", true);
                }
            }
        });
    }
}
