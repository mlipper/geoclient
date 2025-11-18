package geoclientbuild.server;

import java.io.File;
import java.net.URI;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Console;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import geoclientbuild.client.HttpClient;
import geoclientbuild.client.Request;
import geoclientbuild.client.RestClient;

public abstract class StopServer extends DefaultTask {

    public static final String TASK_NAME = "stopServer";
    public static final String DEFAULT_ENDPOINT = "/actuator/shutdown";

    @Input
    abstract Property<URI> getURI();

    @Console
    abstract RegularFileProperty getOutputFile();

    public StopServer() {
        setGroup("API Server");
        setDescription("Stops the Geoclient API server.");
    }

    @TaskAction
    public void stopServer() throws Exception {
        URI uri = getURI().get();
        getLogger().lifecycle("Stopping API server with empty-body POST to {}", uri.toString());
        Request postRequest = new Request("stop-server", getURI().get().toString(), Request.HTTP_POST_METHOD, null);
        RestClient restClient = new HttpClient();
        String response = restClient.call(postRequest).getBody();
        getLogger().lifecycle("API server shutdown response: {}", response);
        File outputFile = getOutputFile().getAsFile().get();
        try (java.io.FileWriter writer = new java.io.FileWriter(outputFile, true)) {
            writer.write("API server shutdown response: " + response + System.lineSeparator());
        }
    }
}
