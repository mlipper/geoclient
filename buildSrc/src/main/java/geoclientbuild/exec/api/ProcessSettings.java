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
package geoclientbuild.exec.api;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProcessSettings {

    private List<String> commandLine;
    private Optional<File> pidFile;
    private Optional<File> workingDirectory;
    private Optional<Long> sleepSecondsAfterStart;
    private Optional<Map<String, String>> environment;

    public static class Builder {
        private final ProcessSettings settings;

        public Builder() {
            settings = new ProcessSettings();
        }

        public Builder withCommandLine(List<String> commandLine) {
            settings.setCommandLine(commandLine);
            return this;
        }

        public Builder withCommandLine(String... commandLine) {
            return withCommandLine(List.of(commandLine));
        }

        public Builder withPidFile(File pidFile) {
            settings.setPidFile(Optional.of(pidFile));
            return this;
        }

        public Builder withWorkingDirectory(File workingDirectory) {
            settings.setWorkingDirectory(Optional.of(workingDirectory));
            return this;
        }

        public Builder withEnvironment(Map<String, String> env) {
            settings.setEnvironment(Optional.of(env));
            return this;
        }

        public Builder withSleepSecondsAfterStart(Long sleepSeconds) {
            settings.setSleepSecondsAfterStart(Optional.of(sleepSeconds));
            return this;
        }

        public ProcessSettings build() {
            return this.settings;
        }
    }

    public List<String> getCommandLine() {
        return commandLine;
    }

    void setCommandLine(List<String> commandLine) {
        this.commandLine = commandLine;
    }

    public Optional<File> getPidFile() {
        return pidFile;
    }

    void setPidFile(Optional<File> pidFile) {
        this.pidFile = pidFile;
    }

    public Optional<File> getWorkingDirectory() {
        return workingDirectory;
    }

    void setWorkingDirectory(Optional<File> workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public Optional<Long> getSleepSecondsAfterStart() {
        return sleepSecondsAfterStart;
    }

    void setSleepSecondsAfterStart(Optional<Long> sleepSecondsAfterStart) {
        this.sleepSecondsAfterStart = sleepSecondsAfterStart;
    }

    public Optional<Map<String, String>> getEnvironment() {
        return environment;
    }

    void setEnvironment(Optional<Map<String, String>> environment) {
        this.environment = environment;
    }

}
