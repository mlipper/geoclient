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

import java.io.File;
import java.net.URI;
import java.util.List;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.RegularFile;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.Provider;

public class ApiServerPlugin implements Plugin<Project> {
    private Logger logger = Logging.getLogger(ApiServerPlugin.class);

    public static final String APISERVER_INFO_TASK_NAME = "apiServerInfo";

    public static final String DEFAULT_CONTEXT_PATH = "geoclient/v2";
    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 8080;
    public static final String DEFAULT_SCHEME = "http";
    public static final String DEFAULT_OUTPUT_DIRECTORY = "api-server";
    public static final String DEFAULT_JAVA_COMMAND = "java";
    public static final String DEFAULT_PID_FILE = "api-server.pid";
    public static final String DEFAULT_PID_FILE_PATH = DEFAULT_OUTPUT_DIRECTORY + File.separator
            + DEFAULT_PID_FILE;
    public static final String DEFAULT_PROFILE = "docsamples";
    public static final Long DEFAULT_SLEEP_AFTER_START_SECONDS = 8L;
    public static final Long DEFAULT_SLEEP_AFTER_STOP_SECONDS = 4L;

    public void apply(Project project) {
        // Register the extension
        ApiServerExtension extension = registerExtension(project);

        URI baseUri = baseUri(extension);
        // Register tasks
        project.getTasks().register(StartServer.TASK_NAME, StartServer.class, task -> {
            task.getServerJar().set(extension.getServerJar());
            task.getJavaCommand().set(extension.getJavaCommand());
            task.getUri().set(baseUri);
            List<String> args = new ArgumentsBuilder.BootArgumentsBuilder().host(extension.getHost().get()).port(
                extension.getPort().get()).contextPath(extension.getContextPath().get()).profile(
                    DEFAULT_PROFILE).build();
            task.getArguments().set(args);
            task.getPidFile().set(extension.getPidFile());
            task.getSleepSecondsAfterStart().set(extension.getSleepSecondsAfterStart());
        });

        project.getTasks().register(StopServer.TASK_NAME, StopServer.class, task -> {
            task.getUri().set(baseUri(extension));
            task.getPidFile().set(extension.getPidFile());
        });

        project.getTasks().register(APISERVER_INFO_TASK_NAME, task -> {
            String url = String.format("%s://%s:%d/%s", extension.getScheme().get(), extension.getHost().get(),
                extension.getPort().get(), extension.getContextPath().get());
            Provider<File> jarfile = extension.getServerJar().getAsFile();
            task.doLast(s -> {
                logger.lifecycle("ApiServer build service URL: " + url);
                logger.lifecycle("ApiServer JAR: " + jarfile.get().getAbsolutePath());
            });
        });
    }

    private ApiServerExtension registerExtension(Project project) {
        ApiServerExtension extension = project.getExtensions().create(ApiServerExtension.EXTENSION_NAME,
            ApiServerExtension.class);
        // Configure the extension with some defaults

        // Configure default base URI
        extension.getScheme().convention(DEFAULT_SCHEME);
        extension.getHost().convention(DEFAULT_HOST);
        extension.getPort().convention(DEFAULT_PORT);
        extension.getContextPath().convention(DEFAULT_CONTEXT_PATH);

        // Configure process environment
        extension.getSleepSecondsAfterStart().convention(DEFAULT_SLEEP_AFTER_START_SECONDS);
        extension.getSleepSecondsAfterStop().convention(DEFAULT_SLEEP_AFTER_STOP_SECONDS);
        extension.getEnvironment().convention(System.getenv());
        extension.getArguments().convention(java.util.Collections.emptyList());
        extension.getJavaCommand().convention(DEFAULT_JAVA_COMMAND);
        // Change to tmpDir?
        Provider<RegularFile> pidFile = project.getLayout().getBuildDirectory().file(DEFAULT_PID_FILE_PATH);
        extension.getPidFile().convention(pidFile);
        return extension;
    }

    private URI baseUri(ApiServerExtension extension) {
        try {
            return new URI(String.format("%s://%s:%d/%s", extension.getScheme().get(), extension.getHost().get(),
                extension.getPort().get(), extension.getContextPath().get()));
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to build API server URI", e);
        }
    }
}
