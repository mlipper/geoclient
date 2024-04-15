package com.digitalclash.geoclient.gradle;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hc.client5.http.fluent.Form;
import org.apache.hc.client5.http.fluent.Request;

import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
import org.gradle.process.ExecOperations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RestService implements BuildService<RestService.Params>, AutoCloseable {

    interface Params extends BuildServiceParameters {
        Property<String> getUri();
    }

    static final String SHUTDOWN_ENDPOINT = "/actuator/shutdown";

    private final Property<String> uri;
    private Logger logger = LoggerFactory.getLogger(RestService.class);

    public RestService() {
        this.uri = getParameters().getUri();
    }

    public Property<String> getUri() {
        return this.uri;
    }

    @Override
    public void close() {
        // Stop the service ...
        try {
            Request.post(this.uri.get() + SHUTDOWN_ENDPOINT)
                    .bodyForm(Form.form().build()).execute().returnContent();
        } catch (IOException e) {
            logger.error("POST to shutdown endpoint {}{} failed:", this.uri.get(), SHUTDOWN_ENDPOINT);
            logger.error("Exception: ", e);
            throw new RuntimeException("Method RestService#close threw exception: " + e.getMessage());
        }
    }
}
