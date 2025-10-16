package geoclientbuild.server;

import java.net.URI;

import org.gradle.api.DefaultTask;
import org.gradle.api.services.ServiceReference;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.TaskAction;

import geoclientbuild.client.Poster;

import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;

public abstract class StopServer extends DefaultTask {

    public static final String TASK_NAME = "stopServer";
    public static final String DEFAULT_ENDPOINT = "/actuator/shutdown";

    @ServiceReference(ApiServer.SERVICE_NAME)
    abstract Property<ApiServer> getApiServer();

    @Input
    abstract Property<String> getEndpoint();

    @Internal
    public Provider<URI> getUri() {
        return getApiServer().map(
            server -> server.getUri().resolve(server.getUri().toString() + getEndpoint().getOrElse(""))
        );
    }

    public StopServer() {
        setGroup("API Server");
        setDescription("Stops the Geoclient API server.");
        getEndpoint().convention(DEFAULT_ENDPOINT);
    }

    @TaskAction
    public void stopServer() throws Exception {
        String uri = getUri().get().toString();
        getLogger().lifecycle("Stopping API server at {}", uri);
        Poster poster = new Poster();
        String response = poster.post(uri, Poster.DEFAULT_PARAMETERS, Poster.DEFAULT_HEADERS);
        getLogger().lifecycle("API server shutdown response: {}", response);
    }

}
