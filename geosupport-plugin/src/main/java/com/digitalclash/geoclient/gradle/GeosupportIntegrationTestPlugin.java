/*
 * Copyright 2013-2023 the original author or authors.
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
package com.digitalclash.geoclient.gradle;

import com.digitalclash.geoclient.gradle.tasks.GeosupportIntegrationTest;
import com.digitalclash.geoclient.gradle.tasks.IntegrationTestOptionsAware;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.TaskProvider;

public class GeosupportIntegrationTestPlugin implements Plugin<Project> {

    public static final String INTEGRATION_TEST_OPTIONS_EXTENSION_NAME = "integrationTestOptions";

    private Logger logger = Logging.getLogger(GeosupportIntegrationTestPlugin.class);

    @Override
    public void apply(final Project project) {
        project.getPlugins().apply(GeosupportPlugin.class);
        final GeosupportApplication geosupportApplication = project.getExtensions()
                .getByType(GeosupportApplication.class);
        final IntegrationTestOptions integrationTestOptions = ((ExtensionAware) geosupportApplication).getExtensions()
                .create(INTEGRATION_TEST_OPTIONS_EXTENSION_NAME, IntegrationTestOptions.class, project.getObjects());
        logger.info("[ITEST] Created new IntegrationTestOptions instance with test name {} and sourceSet name {}.",
                integrationTestOptions.getTestName().get(), integrationTestOptions.getSourceSetName().get());
        configureIntegrationTestOptionsAwareTasks(project, integrationTestOptions, logger);
        logger.info(
                "[ITEST] Configured tasks using IntegrationTestOptions instance with test name {} and sourceSet name {}.",
                integrationTestOptions.getTestName().get(), integrationTestOptions.getSourceSetName().get());

        project.getPlugins().withType(JavaPlugin.class).configureEach(javaPlugin -> {
            // TODO This is happening before configureIntegrationTestOptionsAwareTasks() is
            // called.
            // TODO See
            // https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/DockerConventionJvmApplicationPlugin.java
            // See
            // https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests
            SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
            SourceSet testSourceSet = sourceSets.getByName(SourceSet.TEST_SOURCE_SET_NAME);
            // Create new integration test SourceSet.
            logger.quiet("[ITEST] Creating new SourceSet using IntegrationTestOptions instance with test name {} and sourceSet name {}.", integrationTestOptions.getTestName().get(), integrationTestOptions.getSourceSetName().get());
            SourceSet sourceSet = sourceSets.create(integrationTestOptions.getSourceSetName().get());
            sourceSet.getJava().srcDir(integrationTestOptions.getJavaSourceDir());
            sourceSet.getResources().srcDir(integrationTestOptions.getResourcesSourceDir());
            sourceSet.getCompileClasspath().plus(mainSourceSet.getOutput()).plus(mainSourceSet.getCompileClasspath());
            sourceSet.getRuntimeClasspath().plus(mainSourceSet.getOutput()).plus(mainSourceSet.getRuntimeClasspath());
            // New SourceSet's implementation and runtimeOnly configurations extend from the
            // corresponding configurations in the
            // "main" SourceSet. Add dependencies from the testImplementation configuration
            // for JUnit and other test dependencies.
            Configuration javaImplementation = project.getConfigurations().getByName(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME);
            Configuration javaTestImplementation = project.getConfigurations().getByName(JavaPlugin.TEST_IMPLEMENTATION_CONFIGURATION_NAME);
            Configuration implementation = project.getConfigurations().getByName(sourceSet.getImplementationConfigurationName());
            implementation.extendsFrom(javaImplementation, javaTestImplementation);
            Configuration javaRuntimeOnly = project.getConfigurations().getByName(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME);
            Configuration javaTestRuntimeOnly = project.getConfigurations().getByName(JavaPlugin.TEST_RUNTIME_ONLY_CONFIGURATION_NAME);
            Configuration runtimeOnly = project.getConfigurations().getByName(sourceSet.getRuntimeOnlyConfigurationName());
            runtimeOnly.extendsFrom(javaRuntimeOnly, javaTestRuntimeOnly);

            TaskProvider<GeosupportIntegrationTest> test = project.getTasks().register(integrationTestOptions.getTestName().get(), GeosupportIntegrationTest.class, new Action<GeosupportIntegrationTest>() {
                        public void execute(GeosupportIntegrationTest test) {
                            test.setDescription("Runs tests which call Geosupport native code using JNI.");
                            test.setGroup(GeosupportPlugin.DEFAULT_TASK_GROUP);
                            logger.info("Setting testClassesDirs({})", sourceSet.getOutput().getClassesDirs().getAsPath());
                            test.setTestClassesDirs(sourceSet.getOutput().getClassesDirs());
                            logger.info("Setting classpath({})", sourceSet.getRuntimeClasspath().getAsPath());
                            test.setClasspath(sourceSet.getRuntimeClasspath());
                            test.shouldRunAfter(project.getTasks().named("test"));
                            // Set required GEOFILES environment variable using the value from the
                            // extension.
                            test.environment("GEOFILES", geosupportApplication.getGeosupport().getGeofiles().get());
                        }
                    });
            TaskProvider<Task> checkTask = project.getTasks().named("check");
            checkTask.get().dependsOn(test);
        });
    }

    //
    // Based on
    // https://github.com/bmuschko/gradle-docker-plugin/blob/master/src/main/java/com/bmuschko/gradle/docker/DockerRemoteApiPlugin.java
    //
    private void configureIntegrationTestOptionsAwareTasks(Project project,
            final IntegrationTestOptions integrationTestOptions, final Logger logger) {
        project.getTasks().withType(IntegrationTestOptionsAware.class)
                .configureEach(new Action<IntegrationTestOptionsAware>() {
                    @Override
                    public void execute(IntegrationTestOptionsAware task) {
                        logger.quiet("[ITEST] Configuring task {}'s integrationTestOptions conventions using {}.", task.getName(), integrationTestOptions);
                        task.getIntegrationTestOptions().getTestName().convention(integrationTestOptions.getTestName());
                        task.getIntegrationTestOptions().getSourceSetName() .convention(integrationTestOptions.getSourceSetName());
                        task.getIntegrationTestOptions().getValidate().convention(integrationTestOptions.getValidate());
                        task.getIntegrationTestOptions().getUseJavaLibraryPath().convention(integrationTestOptions.getUseJavaLibraryPath());
                        task.getIntegrationTestOptions().getExportLdLibraryPath().convention(integrationTestOptions.getExportLdLibraryPath());
                        // ConfigurationContainer configurationContainer =
                        // task.getProject().getConfigurations();
                        // configurationContainer.getNames().forEach(name ->
                        // logConfiguration(configurationContainer.getByName(name)));
                    }
                });
    }

    private void logConfiguration(Configuration configuration) {
        logger.quiet("Configuration: {}", configuration.getName());
        logger.quiet("              {} dependencies", configuration.getAllDependencies().size());
        logger.quiet("              {} artifacts", configuration.getAllArtifacts().size());
        configuration.getAllDependencies().forEach(
                dependency -> logger.quiet(" - {}:{}:{}",
                        dependency.getGroup(),
                        dependency.getName(),
                        dependency.getVersion()));
    }
}
