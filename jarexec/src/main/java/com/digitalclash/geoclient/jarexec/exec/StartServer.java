package com.digitalclash.geoclient.jarexec.exec;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.net.URI;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StartServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TASK_NAME = "startServer";

    abstract File getApiServerJar();

    abstract List<String> getArguments();

    abstract URI getUri();

    abstract Long getWaitSecondsAfterStart();
    
    abstract File getOutputFile();

    public StartServer() {
    }

    public void startApiServer() throws Exception {
        String jarPath = getApiServerJar().getAbsolutePath();
        logger.info("API server base endpoint: {}", getUri().toString());
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command().add("java");
        processBuilder.command().add("-jar");
        processBuilder.command().add(jarPath);
        processBuilder.command().addAll(getArguments());
        File outputFile = getOutputFile();
        processBuilder.redirectOutput(Redirect.appendTo(outputFile));
        processBuilder.redirectError(Redirect.appendTo(outputFile));
        File nullInput = new File(System.getProperty("os.name").startsWith("Windows") ? "NUL" : "/dev/null");
        processBuilder.redirectInput(Redirect.from(nullInput));
        long sleepTime = 1000 * getWaitSecondsAfterStart();
        LocalTime startTime = LocalTime.now();
        Process process = processBuilder.start();
        logger.info("API server PID {}", process.pid());
        Thread.sleep(sleepTime); // Wait for server to start
        LocalTime afterSleepTime = LocalTime.now();
        long duration = Duration.between(startTime, afterSleepTime).getSeconds();
        logger.info("{} task slept for {} seconds (local time) after API server start.", TASK_NAME, duration);
    }

}
