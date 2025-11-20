package geoclientbuild.jarexec.settings;

import java.io.File;
import java.util.List;
import java.util.Map;

import geoclientbuild.client.Request;
import geoclientbuild.jarexec.exec.ConfigurationException;

public class Settings {

    public static final String DEFAULT_JAVA_COMMAND = "java";
    public static final long DEFAULT_SLEEP_SECONDS_AFTER_START = 10L;
    public static final String JAR_ARGUMENT = "-jar";

    private List<String> arguments;
    private Map<String, String> environment;
    private File jarFile;
    private String javaCommand;
    private SettingsInfo settingsInfo;
    private File httpShutdownFile;
    private File workingDirectory;
    private long sleepSecondsAfterStart = DEFAULT_SLEEP_SECONDS_AFTER_START;

    public static class Builder {
        private final Settings settings;

        public Builder() {
            settings = new Settings();
        }

        public Builder withHttpShutdownFile(String httpShutdownFile) {
            return this.withHttpShutdownFile(new File(httpShutdownFile));
        }

        public Builder withHttpShutdownFile(File httpShutdownFile) {
            settings.httpShutdownFile = httpShutdownFile;
            return this;
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

    public Request createHttpShutdownRequest() throws ConfigurationException {
        HttpShutdown httpShutdown = httpShutdown();
        return httpShutdown.createShutdownRequest();
    }

    public HttpShutdown httpShutdown() throws ConfigurationException {
        File httpShutdownFile = getHttpShutdownFile();
        if (httpShutdownFile == null || !httpShutdownFile.exists()) {
            throw new ConfigurationException("HTTP shutdown file not found: " +
                    (httpShutdownFile != null ? httpShutdownFile.getAbsolutePath() : "<null>"));
        }
        HttpShutdownSettings httpShutdownSettings = new HttpShutdownSettings();
        try {
            return httpShutdownSettings.load(httpShutdownFile);
        } catch (Exception e) {
            throw new ConfigurationException("Failed to load HTTP shutdown settings from file: ", e);
        }
    }

    public String settings() {
        return settingsInfo.info();
    }

    public boolean supportsHttpShutdown() {
        return httpShutdownFile != null && httpShutdownFile.exists();
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

    public File getHttpShutdownFile() {
        return httpShutdownFile;
    }

    public void setHttpShutdownFile(String httpShutdownFile) {
        this.setHttpShutdownFile(new File(httpShutdownFile));
    }

    public void setHttpShutdownFile(File httpShutdownFile) {
        this.httpShutdownFile = httpShutdownFile;
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

    public File getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.setWorkingDirectory(new File(workingDirectory));
    }

    public void setWorkingDirectory(File workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    private String resolveJavaCommand() {
        return javaCommand != null ? javaCommand : DEFAULT_JAVA_COMMAND;
    }
}
