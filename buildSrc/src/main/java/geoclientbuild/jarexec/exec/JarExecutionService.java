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

import java.time.Duration;
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
        TimeUnit.SECONDS.sleep(settings.getSleepSecondsAfterStart()); // Wait for process to be fully initialized
        logger.info("Service running...");
        return process;
    }

    public void stop(Process process) throws Exception {
        if(process == null) {
            throw new IllegalArgumentException("ProcessHandle argument cannot be null.");
        }
        Long pid = process.pid();
        logger.info("Destroying process {}...", pid);
        destroyProcess(process); // Does nothing if process is already terminated or null
        logger.info("Process {} has been destroyed.", pid);
    }

    public void stop(ProcessHandle handle) throws Exception {
        if(handle == null) {
            throw new IllegalArgumentException("ProcessHandle argument cannot be null.");
        }
        Long pid = handle.pid();
        logger.info("Destroying process {}...", pid);
        destroyProcess(handle); // Does nothing if process is already terminated or null
        logger.info("Process {} has been destroyed.", pid);
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

    private boolean isProcessAlive(ProcessHandle handle) {
        if (handle != null && handle.isAlive()) {
            return true;
        }
        return false;
    }

    private void destroyProcess(ProcessHandle handle) {
        if(isProcessAlive(handle)) {
            logger.info("Sending SIGTERM to process: {}", handle.pid());
            if(!handle.destroy()) {
                try {
                    Thread.sleep(Duration.ofSeconds(10));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    String msg = String.format("Thread interrupted waiting for PID %d to stop: %s", handle.pid(), e.getMessage());
                    logger.warn(msg);
                }
                if(!handle.destroyForcibly()) {
                    String msg = String.format("Failed to forcibly destroy process %d.", handle.pid());
                    logger.error(msg);
                    throw new IllegalStateException(msg);
                }
            }
        }
    }

    private void destroyProcess(Process process) {
        if (isProcessAlive(process)) {
            Long pid = process.pid();
            logger.info("Sending SIGTERM to process: {}", pid);
            process.destroy(); // Non-blocking: attempts graceful shutdown (SIGTERM)
            try {
                // Wait for a few seconds for the process to terminate gracefully
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    logger.warn("Process {} did not terminate gracefully. Forcing shutdown.", pid);
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
