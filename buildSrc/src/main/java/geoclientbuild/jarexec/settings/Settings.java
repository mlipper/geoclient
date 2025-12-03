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
package geoclientbuild.jarexec.settings;

import java.io.File;
import java.util.List;
import java.util.Map;

import geoclientbuild.jarexec.exec.ProcessSettings;

public class Settings {

    public static final String DEFAULT_JAVA_COMMAND = "java";
    public static final long DEFAULT_SLEEP_SECONDS_AFTER_START = 8L;
    public static final long DEFAULT_SLEEP_SECONDS_AFTER_STOP = 4L;
    public static final String JAR_ARGUMENT = "-jar";

    private List<String> arguments;
    private Map<String, String> environment;
    private File jarFile;
    private String javaCommand;
    private SettingsInfo settingsInfo;
    private File workingDirectory;
    private long sleepSecondsAfterStart = DEFAULT_SLEEP_SECONDS_AFTER_START;
    private long sleepSecondsAfterStop = DEFAULT_SLEEP_SECONDS_AFTER_STOP;

    public static class Builder {
        private final Settings settings;

        public Builder() {
            settings = new Settings();
        }

        public Builder withJarFile(String jarFile) {
            return this.withJarFile(new File(jarFile));
        }

        public Builder withJarFile(File jarFile) {
            settings.setJarFile(jarFile);
            return this;
        }

        public Builder withJavaCommand(String javaCommand) {
            settings.setJavaCommand(javaCommand);
            return this;
        }

        public Builder withArguments(List<String> arguments) {
            settings.setArguments(arguments);
            return this;
        }

        public Builder withEnvironment(Map<String, String> environment) {
            settings.setEnvironment(environment);
            return this;
        }

        public Builder withSleepSecondsAfterStart(long seconds) {
            settings.setSleepSecondsAfterStart(seconds);
            return this;
        }

        public Builder withSleepSecondsAfterStop(long seconds) {
            settings.setSleepSecondsAfterStop(seconds);
            return this;
        }

        public Settings build() {
            return settings;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public Settings() {
        this.settingsInfo = new SettingsInfo(this);
    }

    public String commandLine() {
        StringBuilder cmd = new StringBuilder();
        cmd.append(resolveJavaCommand());
        cmd.append(" ").append(JAR_ARGUMENT).append(" ");
        cmd.append(jarFile.getAbsolutePath());
        if (arguments != null && !arguments.isEmpty()) {
            for (String arg : arguments) {
                cmd.append(" ").append(arg);
            }
        }
        return cmd.toString();
    }

    public List<String> commandLineAsList() {
        List<String> cmdList = new java.util.ArrayList<>();
        cmdList.add(resolveJavaCommand());
        cmdList.add(JAR_ARGUMENT);
        cmdList.add(jarFile.getAbsolutePath());
        if (arguments != null && !arguments.isEmpty()) {
            cmdList.addAll(arguments);
        }
        return cmdList;
    }

    // @TODO: test me!
    public boolean workingDirectoryExists() {
        return workingDirectory != null && workingDirectory.exists() && workingDirectory.isDirectory();
    }

    // @TODO: test me!
    public boolean environmentIsSet() {
        return environment != null && !environment.isEmpty();
    }

    public String settings() {
        return settingsInfo.info();
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public File getJarFile() {
        return jarFile;
    }

    public void setJarFile(String jarFile) {
        this.setJarFile(new File(jarFile));
    }

    public void setJarFile(File jarFile) {
        this.jarFile = jarFile;
    }

    public String getJavaCommand() {
        return javaCommand;
    }

    public void setJavaCommand(String javaCommand) {
        this.javaCommand = javaCommand;
    }

    public SettingsInfo getSettingsInfo() {
        return settingsInfo;
    }

    public long getSleepSecondsAfterStart() {
        return sleepSecondsAfterStart;
    }

    public void setSleepSecondsAfterStart(long sleepSecondsAfterStart) {
        this.sleepSecondsAfterStart = sleepSecondsAfterStart;
    }

    public long getSleepSecondsAfterStop() {
        return sleepSecondsAfterStop;
    }

    public void setSleepSecondsAfterStop(long sleepSecondsAfterStop) {
        this.sleepSecondsAfterStop = sleepSecondsAfterStop;
    }

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.setWorkingDirectory(new File(workingDirectory));
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public ProcessSettings toProcessSettings() {
        ProcessSettings.Builder psBuilder = new ProcessSettings.Builder();
        return psBuilder.withCommandLine(commandLineAsList())
            .withEnvironment(environment)
            .withWorkingDirectory(workingDirectory)
            .withSleepSecondsAfterStart(sleepSecondsAfterStart)
            .build();
    }
    private String resolveJavaCommand() {
        return javaCommand != null ? javaCommand : DEFAULT_JAVA_COMMAND;
    }
}
