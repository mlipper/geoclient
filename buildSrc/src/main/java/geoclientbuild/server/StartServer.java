package geoclientbuild.server;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.time.Duration;
import java.time.LocalTime;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

public abstract class StartServer extends DefaultTask {
    public static final String TASK_NAME = "startServer";

    @InputFile
    abstract RegularFileProperty getApiServerJar();

    @Input
    abstract ListProperty<String> getArguments();

    @Input
    abstract Property<URI> getUri();

    @Optional
    @Input
    abstract Property<Long> getWaitSecondsAfterStart();
    
    @OutputFile
    abstract RegularFileProperty getOutputFile();

    public StartServer() {
        setGroup("API Server");
        setDescription("Starts the Geoclient API server.");
    }

    @TaskAction
    public void startApiServer() throws Exception {
        String jarPath = getApiServerJar().getAsFile().get().getAbsolutePath();
        getLogger().lifecycle("API server base endpoint: {}", getUri().get().toString());
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command().add("java");
        processBuilder.command().add("-jar");
        processBuilder.command().add(jarPath);
        processBuilder.command().addAll(getArguments().get());
        File outputFile = getOutputFile().getAsFile().get();
        processBuilder.redirectOutput(Redirect.appendTo(outputFile));
        processBuilder.redirectError(Redirect.appendTo(outputFile));
        File nullInput = new File(System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null");
        processBuilder.redirectInput(Redirect.from(nullInput));
        long sleepTime = 1000 * getWaitSecondsAfterStart().get();
        LocalTime startTime = LocalTime.now();
        Process process = processBuilder.start();
        getLogger().lifecycle("API server PID {}", process.pid());
        Thread.sleep(sleepTime); // Wait for server to start
        LocalTime afterSleepTime = LocalTime.now();
        long duration = Duration.between(startTime, afterSleepTime).getSeconds();
        getLogger().lifecycle("{} task slept for {} seconds (local time) after API server start.", TASK_NAME, duration);
    }

}
