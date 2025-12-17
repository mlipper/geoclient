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

import java.util.Optional;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

public abstract class StopServer extends AbstractServerProcess {

    public static final String TASK_NAME = "stopServer";

    @InputFile
    abstract RegularFileProperty getPidFile();

    public StopServer() {
        setGroup("API Server");
        setDescription("Stops the Geoclient API server.");
    }

    @TaskAction
    public void stopServer() throws ApiServerException {
        long pid = -1L;
        try {
            pid = readPidFile(getPidFile());
            Optional<ProcessHandle> processHandle = ProcessHandle.of(pid);
            if (processHandle.isPresent()) {
                getLogger().lifecycle("Stopping server process {}.", pid);
                stopProcess(processHandle.get(), pid);
                getLogger().lifecycle("Server process {} has been stopped.", pid);
            }
            deletePidFile(getPidFile());
        }
        catch (Exception e) {
            getLogger().error("Error stopping server process.", e);
            ApiServerException apiServerException = deletePidFileAndBuildException(getPidFile(),
                "Failed to stop API server.", e);
            throw apiServerException;
        }
    }

    private void stopProcess(ProcessHandle processHandle, long pid) {
        if (processHandle != null && processHandle.isAlive()) {
            ProcessHandle.Info phInfo = processHandle.info();
            getLogger().debug("Attempting to destroy process {} with ProcessHandle: {}", pid, phInfo.toString());
            if (!processHandle.destroy()) {
                getLogger().debug("Failed to destroy process {}. Attempting to destroy it forcibly.", pid);
                if (processHandle != null && processHandle.isAlive()) {
                    if (!processHandle.destroyForcibly()) {
                        throw new IllegalStateException(processHandle.info().toString());
                    }
                }
            }
        }
    }
}
