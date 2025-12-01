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
package geoclientbuild.runner;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.gradle.api.Action;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.workers.ProcessWorkerSpec;
import org.gradle.workers.WorkAction;
import org.gradle.workers.WorkParameters;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceRunner {
    private final Logger logger = LoggerFactory.getLogger(ServiceRunner.class);

    private final WorkerExecutor workerExecutor;

    interface ServiceRunnerWorkParameters extends WorkParameters {
        ListProperty<String> getActiveProfiles();

        RegularFileProperty getJarFile();

        ListProperty<String> getJvmArguments();
    }

    public abstract static class ServiceRunnerExecutor implements WorkAction<ServiceRunnerWorkParameters> {

        @Override
        public void execute() {

        }
    }

    public ServiceRunner(WorkerExecutor workerExecutor) {
        this.workerExecutor = Objects.requireNonNull(workerExecutor);
    }

    public void run(ServiceRunnerTask task) {
        Objects.requireNonNull(task);
        WorkQueue workerQueue = workerExecutor.processIsolation(configureWorkerSpec(task));
        workerQueue.submit(ServiceRunnerExecutor.class, configureWorkParameters(task));
    }

    private Action<ProcessWorkerSpec> configureWorkerSpec(ServiceRunnerTask task) {
        return spec -> {
            //spec.getClasspath().setFrom(task.getClasspath());
            spec.forkOptions(option -> {
                option.jvmArgs(buildJvmArguments(task));
            });
        };
    }

    private Action<ServiceRunnerWorkParameters> configureWorkParameters(ServiceRunnerTask task) {
        return params -> {
            params.getActiveProfiles().addAll(buildActiveProfiles(task));
        };
    }

    private List<String> buildActiveProfiles(ServiceRunnerTask task) {
        List<String> args = task.getActiveProfiles().getOrElse(Collections.emptyList());
        logger.info("Using active profiles: {}", args);
        return args;
    }

    private List<String> buildJvmArguments(ServiceRunnerTask task) {
        List<String> args = task.getJvmArguments().getOrElse(Collections.emptyList());
        logger.info("Using JVM arguments: {}", args);
        return args;
    }
}
