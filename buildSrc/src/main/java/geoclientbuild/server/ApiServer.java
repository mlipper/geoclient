package geoclientbuild.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;

public abstract class ApiServer implements BuildService<ApiServer.Params>, AutoCloseable {
    interface Params extends BuildServiceParameters {
        Property<Integer> getPort();
        Property<String> getContextPath();
        Property<String> getHost();
        Property<String> getScheme();
        RegularFileProperty getApiServerJar();
    }

    private final Logger logger = Logging.getLogger(ApiServer.class);

    //public static final String DEFAULT_CONTEXT_PATH = "geoclient/v2";
    //public static final String DEFAULT_HOST = "localhost";
    //public static final Integer DEFAULT_PORT = 8080;
    //public static final String DEFAULT_SCHEME = "http";
    //public static final String SERVICE_NAME = "geoclientbuild.apiserver";

    private final URI uri;

    public ApiServer() throws URISyntaxException{
        //configureApiServerConventions();
        String scheme = getParameters().getScheme().getOrElse("http");
        String host = getParameters().getHost().get();
        int port = getParameters().getPort().get();
        String contextPath = getParameters().getContextPath().get();
        uri = new URI(String.format("%s://%s:%d/%s", scheme, host, port, contextPath));
        logger.lifecycle("API server URI: " + uri.toString());
    }

    public File getApiServerJarFile() {
        return getParameters().getApiServerJar().getAsFile().get();
    }

    public URI getUri() throws URISyntaxException {
        logger.lifecycle("API server URI: " + uri.toString());
        return uri;
    }

    @Override
    public void close() {
        // Implement any necessary cleanup logic here
    }

//    private void configureApiServerConventions() {
//        // Set default values for parameters if not provided
//        getParameters().getPort().convention(DEFAULT_PORT);
//        getParameters().getHost().convention(DEFAULT_HOST);
//        getParameters().getScheme().convention(DEFAULT_SCHEME);
//        getParameters().getContextPath().convention(DEFAULT_CONTEXT_PATH);
//    }
}
