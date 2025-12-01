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
package geoclientbuild.jarexec.exec;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import geoclientbuild.jarexec.settings.Settings;

public class JarExecutionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Settings settings;

    public JarExecutionService(Settings settings) {
        this.settings = settings;
    }

    public Process start() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        configureProcess(processBuilder);
        logger.info("Service starting up. Launching external process...");
        Process process = processBuilder.start();
        logger.info("External process started with PID: {}", process.pid());
        TimeUnit.SECONDS.sleep(10); // Wait for process to be fully initialized
        logger.info("Service running...");
        return process;
    }

    public void stop(Process process) throws Exception {
    }

    public void stop(ProcessHandle process) throws Exception {
        logger.info("Service shutting down. Stopping external process...");
        destroyProcess(process); // Does nothing if process is already terminated or null
        logger.info("Service shut down complete.");
    }

    void configureProcess(ProcessBuilder processBuilder) {
        processBuilder.inheritIO(); // Redirect the external process's streams to the current process's streams
        if (settings.environmentIsSet()) {
            processBuilder.environment().putAll(settings.getEnvironment());
        }
        processBuilder.command().addAll(settings.commandLineAsList());
        if (settings.workingDirectoryExists()) {
            processBuilder.directory(settings.getWorkingDirectory());
        }
    }

    private boolean isProcessAlive(Process process) {
        if (process != null && process.isAlive()) {
            return true;
        }
        return false;
    }

    private boolean isProcessAlive(ProcessHandle process) {
        if (process != null && process.isAlive()) {
            return true;
        }
        return false;
    }

    private void destroyProcess(ProcessHandle process) {
    }

    private void destroyProcess(Process process) {
        if (isProcessAlive(process)) {
            logger.info("Sending SIGTERM to process: {}", process.pid());
            process.destroy(); // Non-blocking: attempts graceful shutdown (SIGTERM)
            try {
                // Wait for a few seconds for the process to terminate gracefully
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    logger.warn("Process did not terminate gracefully. Forcing shutdown.");
                    process.destroyForcibly(); // Forceful shutdown (SIGKILL)
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                process.destroyForcibly();
            }
        }
    }
}
