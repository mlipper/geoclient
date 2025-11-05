package com.digitalclash.geoclient.jarexec.exec;

import java.io.File;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StopServer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String TASK_NAME = "stopServer";
    public static final String DEFAULT_ENDPOINT = "/actuator/shutdown";

    abstract URI getURI();

    abstract File getOutputFile();

    public StopServer() {
    }

    public void stopServer() throws Exception {
        URI uri = getURI();
        logger.info("Stopping API server with empty-body POST to {}", uri.toString());
        Poster poster = new Poster();
        String response = poster.post(uri);
        logger.info("API server shutdown response: {}", response);

        File outputFile = getOutputFile();
        try (java.io.FileWriter writer = new java.io.FileWriter(outputFile, true)) {
            writer.write("API server shutdown response: " + response + System.lineSeparator());
        }
    }
}
