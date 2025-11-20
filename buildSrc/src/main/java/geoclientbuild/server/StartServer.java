package geoclientbuild.server;

import java.net.URI;
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
            getLogger().info("Wrote PID file {} for API server process {}.", pidFileAsFile(getPidFile()).getAbsolutePath(), pid);
            getLogger().lifecycle("API server running with PID {}.", pid);
        } catch (Exception e) {
            getLogger().error("Error starting server process.", e);
            ApiServerException apiServerException = deletePidFileAndBuildException(getPidFile(), "Failed to start API server.", e);
            throw apiServerException;
        }
    }

    private Settings createSettings() {
        Settings settings = new Settings.Builder()
            .withJavaCommand(getJavaCommand().get())
            .withJarFile(getServerJar().get().getAsFile())
            .withArguments(getArguments().get())
            .withSleepSecondsAfterStart(getSleepSecondsAfterStart().get())
            .build();
        Map<String, String> env = getEnvironment().getOrNull();
        if(env != null && !env.isEmpty()) {
            settings.setEnvironment(env);
        }
        return settings;
    }
}
