package geoclientbuild.server;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.BuildService;
import org.gradle.api.services.BuildServiceParameters;
//import org.gradle.process.ExecOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ApiServer implements BuildService<ApiServer.Params>, AutoCloseable {
    interface Params extends BuildServiceParameters {
        Property<Integer> getPort();
        Property<String> getContextPath();
        Property<String> getHost();
        Property<String> getScheme();
        RegularFileProperty getApiServerJar();
    }

    public static final String DEFAULT_CONTEXT_PATH = "geoclient/v2";
    public static final String DEFAULT_HOST = "localhost";
    public static final Integer DEFAULT_PORT = 8084;
    public static final String DEFAULT_SCHEME = "http";
    public static final String SERVICE_NAME = "geoclientbuild.apiserver";

    private Logger logger = LoggerFactory.getLogger(ApiServer.class);
    private final URI uri;
    private final RegularFileProperty apiServerJar;
    //private final Process serverProcess;

    public ApiServer() throws URISyntaxException{
        String scheme = getScheme();
        String host = getHost();
        int port = getPort();
        String contextPath = getContextPath();
        uri = new URI(String.format("%s://%s:%d/%s", scheme, host, port, contextPath)); 
        apiServerJar = getApiServerJar();
        //serverProcess = startServer();
    }

    public File getApiServerJarFile() {
        return apiServerJar.getAsFile().get();
    }

    public URI getUri() {
        return uri;
    }

    @Override
    public void close() {
        // Clean up resources if needed
        logger.info(String.format("Stopping server %s", uri));
        //serverProcess.destroy();
    }

    //protected Process startServer() {
    //    // Logic to start the API server
    //    //getExecOperations().exec(spec -> {
    //    //    spec.commandLine("java", "-jar", apiServerJar.getAsFile().get().getAbsolutePath());
    //    //});
    //    ProcessBuilder processBuilder = new ProcessBuilder("java",
    //        "-jar", apiServerJar.getAsFile().get().getAbsolutePath(),
    //        "--server.address=" + getHost(),
    //        "--server.port=" + getPort(),
    //        "--server.servlet.context-path=/" + getContextPath(),
    //        "--spring.profiles.active=docsamples");
    //    processBuilder.environment().put("GEOSUPPORT_HOME", "/opt/geosupport/current");
    //    processBuilder.environment().put("GEOFILES", "/opt/geosupport/current/fls/");
    //    processBuilder.environment().put("GEOCLIENT_SERVICE_URL", uri.toString());
    //    processBuilder.inheritIO();
    //    Process process = null;
    //    try {
    //        process = processBuilder.start();
    //    } catch (Exception e) {
    //        logger.error("Failed to start server", e);
    //        throw new ApiServerException("Failed to start server",e);
    //    }
    //    if (!process.isAlive()) {
    //        throw new ApiServerException("Failed to start server");
    //    }
    //    logger.info(String.format("Server is running at %s", uri));
    //    return process;
    //}

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

    private RegularFileProperty getApiServerJar() {
        return getParameters().getApiServerJar();
    }
}
