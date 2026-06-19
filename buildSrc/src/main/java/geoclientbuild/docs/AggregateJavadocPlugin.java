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
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.JavadocMemberLevel;
import org.gradle.external.javadoc.StandardJavadocDocletOptions;

/**
 * Plugin that contributes a root-level aggregate Javadoc task and wires sources from Java subprojects.
 */
public class AggregateJavadocPlugin implements Plugin<Project> {

    public static final String PLUGIN_NAME = "geoclientbuild.aggregate-javadoc";
    public static final String AGGREGATE_JAVADOC_TASK_NAME = "aggregateJavadoc";
    public static final String CLEAN_AGGREGATE_JAVADOC_TASK_NAME = "cleanAggregateJavadoc";
    public static final String AGGREGATE_JAVADOC_EXTENSION_NAME = "aggregateJavadocOptions";
    public static final String AGGREGATE_JAVADOC_OUTPUT_DIR = "docs/javadoc-aggregate";

    @Override
    public void apply(Project project) {
        AggregateJavadocExtension extension = project.getExtensions().create(AGGREGATE_JAVADOC_EXTENSION_NAME,
            AggregateJavadocExtension.class);
        extension.getOutputDirectory().convention(
            project.getLayout().getBuildDirectory().dir(AGGREGATE_JAVADOC_OUTPUT_DIR));

        TaskProvider<Javadoc> aggregateJavadocTaskProvider = project.getTasks().register(AGGREGATE_JAVADOC_TASK_NAME,
            Javadoc.class, task -> {
                task.setGroup(DOCUMENTATION_GROUP);
                task.setDescription("Generates unified Javadoc for all Java subprojects.");
                task.setDestinationDir(extension.getOutputDirectory().get().getAsFile());

                if (task.getOptions() instanceof StandardJavadocDocletOptions options) {
                    options.setEncoding("UTF-8");
                    options.setMemberLevel(JavadocMemberLevel.PROTECTED);
                    options.author(true);
                    options.version(true);
                }
            });

        project.getTasks().register(CLEAN_AGGREGATE_JAVADOC_TASK_NAME, task -> {
            task.setGroup(DOCUMENTATION_GROUP);
            task.setDescription("Deletes the aggregate Javadoc output directory.");
            task.doLast(t -> project.delete(extension.getOutputDirectory()));
        });

        if (project.getState().getExecuted()) {
            configureTaskOptions(project, extension);
        }
        else {
            project.afterEvaluate(p -> configureTaskOptions(project, extension));
        }

        project.getSubprojects().forEach(subproject -> subproject.getPluginManager().withPlugin("java", p -> {
            aggregateJavadocTaskProvider.configure(task -> {
                JavaPluginExtension javaExtension = subproject.getExtensions().getByType(JavaPluginExtension.class);
                SourceSet mainSourceSet = javaExtension.getSourceSets().getByName(SourceSet.MAIN_SOURCE_SET_NAME);

                task.source(mainSourceSet.getAllJava());
                task.setClasspath(task.getClasspath().plus(
                    subproject.files(mainSourceSet.getCompileClasspath(), mainSourceSet.getOutput())));
            });
        }));
    }

    private void configureTaskOptions(Project project, AggregateJavadocExtension extension) {
        project.getGradle().projectsEvaluated(gradle -> {
            project.getTasks().named(AGGREGATE_JAVADOC_TASK_NAME, Javadoc.class).configure(task -> {
                task.setDestinationDir(extension.getOutputDirectory().get().getAsFile());
                task.setFailOnError(extension.isFailOnError());

                if (task.getOptions() instanceof StandardJavadocDocletOptions options) {
                    if (extension.isQuiet()) {
                        options.addBooleanOption("quiet", true);
                    }
                    if (extension.isDisableDoclint()) {
                        options.addBooleanOption("Xdoclint:none", true);
                    }
                }
                if (!extension.getCopyOptionsFrom().isPresent()) {
                    throw new org.gradle.api.GradleException(
                        "The 'aggregateJavadocOptions.copyOptionsFrom' property is required. "
                                + "Please configure it to specify the source Javadoc task to copy options and configurations from.");
                }
                Javadoc subprojectJavadoc = extension.getCopyOptionsFrom().get();

                // Copy logging configuration from the source subproject's Javadoc task.
                // We copy the configurations individually to keep the tasks isolated and avoid
                // shared mutability side-effects (e.g. root overrides modifying the subproject task).
                task.getLogging().captureStandardError(subprojectJavadoc.getLogging().getStandardErrorCaptureLevel());
                task.getLogging().captureStandardOutput(subprojectJavadoc.getLogging().getStandardOutputCaptureLevel());

                // Copy Javadoc options from the source task to the aggregate task.
                // We perform a property-by-property copy to ensure task isolation and prevent
                // aggregate Javadoc-specific options (like 'quiet' or 'disableDoclint') from
                // leaking back to and modifying the source subproject's Javadoc task.
                if (subprojectJavadoc.getOptions() instanceof StandardJavadocDocletOptions subOptions
                        && task.getOptions() instanceof StandardJavadocDocletOptions aggOptions) {

                    aggOptions.setAuthor(subOptions.isAuthor());
                    aggOptions.setVersion(subOptions.isVersion());
                    aggOptions.setUse(subOptions.isUse());
                    aggOptions.setNoTimestamp(subOptions.isNoTimestamp());

                    if (subOptions.getEncoding() != null) {
                        aggOptions.setEncoding(subOptions.getEncoding());
                    }
                    if (subOptions.getMemberLevel() != null) {
                        aggOptions.setMemberLevel(subOptions.getMemberLevel());
                    }
                    if (subOptions.getHeader() != null) {
                        aggOptions.setHeader(subOptions.getHeader());
                    }
                    if (subOptions.getDocTitle() != null) {
                        aggOptions.setDocTitle(subOptions.getDocTitle());
                    }
                    if (subOptions.getFooter() != null) {
                        aggOptions.setFooter(subOptions.getFooter());
                    }
                    if (subOptions.getWindowTitle() != null) {
                        aggOptions.setWindowTitle(subOptions.getWindowTitle());
                    }
                    if (subOptions.getLocale() != null) {
                        aggOptions.setLocale(subOptions.getLocale());
                    }
                    if (subOptions.getCharSet() != null) {
                        aggOptions.setCharSet(subOptions.getCharSet());
                    }
                    if (subOptions.getLinks() != null) {
                        aggOptions.setLinks(subOptions.getLinks());
                    }
                    if (subOptions.getLinksOffline() != null) {
                        aggOptions.setLinksOffline(subOptions.getLinksOffline());
                    }
                    if (subOptions.getGroups() != null) {
                        aggOptions.setGroups(subOptions.getGroups());
                    }
                }
            });
        });
    }
}
