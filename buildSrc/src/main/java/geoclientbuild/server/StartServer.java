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
package geoclientbuild.server;

import java.util.Map;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import geoclientbuild.jarexec.exec.JarExecutionService;
import geoclientbuild.jarexec.settings.Settings;

public abstract class StartServer extends AbstractServerProcess {
    public static final String TASK_NAME = "startServer";

    @InputFile
    abstract RegularFileProperty getServerJar();

    @Input
    abstract ListProperty<String> getArguments();

    @Input
    abstract MapProperty<String, String> getEnvironment();

    @Optional
    @Input
    abstract Property<String> getJavaCommand();

    @Optional
    @Input
    abstract Property<Long> getSleepSecondsAfterStart();

    @OutputFile
    abstract RegularFileProperty getPidFile();

    public StartServer() {
        setGroup("API Server");
        setDescription("Starts the Geoclient API server.");
    }

    @TaskAction
    public void startServer() throws ApiServerException {
        Settings settings = createSettings();
        JarExecutionService jarExec = new JarExecutionService(settings);
        long pid = -1L;
        try {
            Process process = jarExec.start();
            pid = process.pid();
            writePidFile(getPidFile(), pid);
            getLogger().info("Wrote PID file {} for API server process {}.",
                pidFileAsFile(getPidFile()).getAbsolutePath(), pid);
            getLogger().lifecycle("API server running with PID {}.", pid);
            getLogger().lifecycle("API server started with settings: {}", settings.getSettingsInfo().info());
        }
        catch (Exception e) {
            getLogger().error("Error starting server process.", e);
            ApiServerException apiServerException = deletePidFileAndBuildException(getPidFile(),
                "Failed to start API server.", e);
            throw apiServerException;
        }
    }

    private Settings createSettings() {
        Settings settings = new Settings.Builder().withJavaCommand(getJavaCommand().get()).withJarFile(
            getServerJar().get().getAsFile()).withArguments(getArguments().get()).withSleepSecondsAfterStart(
                getSleepSecondsAfterStart().get()).build();
        Map<String, String> env = getEnvironment().getOrNull();
        if (env != null && !env.isEmpty()) {
            settings.setEnvironment(env);
        }
        return settings;
    }
}
