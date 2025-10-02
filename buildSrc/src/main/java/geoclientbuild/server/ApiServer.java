package geoclientbuild.server;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class ApiServer implements BuildService<ApiServer.Params>, AutoCloseable {
    interface Params extends BuildServiceParameters {
        Property<String> getContextPath();
        Property<String> getHost();
        Property<Integer> getPort();
        DirectoryProperty getResources();
        Property<String> getScheme();
    }

    public static final String DEFAULT_CONTEXT_PATH = "geoclient/v2";
    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 8084;
    public static final String DEFAULT_SCHEME = "http";
    public static final String SERVICE_NAME = "geoclientbuild.apiserver";

    private Logger logger = LoggerFactory.getLogger(ApiServer.class);
    private final URI uri;

    public ApiServer() throws URISyntaxException{
        String scheme = getScheme();
        String host = getHost();
        int port = getPort();
        String contextPath = getContextPath();
        uri = new URI(String.format("%s://%s:%d/%s", scheme, host, port, contextPath)); 
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public void close() {
        // Clean up resources if needed
        logger.info(String.format("Stopping server %s", uri));
    }

    protected void startServer() {
        // Logic to start the API server
        logger.info(String.format("Server is running at %s", uri));
    }

    private int getPort() {
        return getParameters().getPort().getOrElse(DEFAULT_PORT);
    }

    private String getScheme() {
        return getParameters().getScheme().getOrElse(DEFAULT_SCHEME);
    }

    private String getHost() {
        return getParameters().getHost().getOrElse(DEFAULT_HOST);
    }

    private String getContextPath() {
        return getParameters().getContextPath().getOrElse(DEFAULT_CONTEXT_PATH);
    }
}
