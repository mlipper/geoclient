package geoclientbuild.jarexec.exec;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import geoclientbuild.client.Request;
import geoclientbuild.client.Response;
import geoclientbuild.client.RestClient;
import geoclientbuild.jarexec.settings.Settings;
import com.google.common.util.concurrent.AbstractExecutionThreadService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarExecutionService extends AbstractExecutionThreadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Process process;
    private final RestClient restClient;
    private final Settings settings;

    public JarExecutionService(RestClient restClient, Settings settings) {
        this.restClient = restClient;
        this.settings = settings;
    }

    @Override
    protected void run() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        configureProcess(processBuilder);
        logger.info("Service starting up. Launching external process...");
        this.process = processBuilder.start();
        logger.info("External process started with PID: {}", process.pid());
        TimeUnit.SECONDS.sleep(4); // Wait for process to be fully initialized 
        logger.info("Service running...");
        int exitCode = this.process.waitFor();
        logger.info("External process finished with exit code: {}", exitCode);
    }

    @Override
    protected void shutDown() throws Exception {
        logger.info("Service shutting down. Stopping external process...");
        if(isProcessAlive() && settings.supportsHttpShutdown()){
            httpShutdown();
            TimeUnit.SECONDS.sleep(5); // Wait a bit for graceful HTTP shutdown
        }
        destroyProcess(); // Does nothing if process is already terminated or null
        logger.info("Service shut down complete.");
    }
    
    void configureProcess(ProcessBuilder processBuilder) {
        processBuilder.inheritIO(); // Redirect the external process's streams to the current process's streams
        if(settings.environmentIsSet()) {
            processBuilder.environment().putAll(settings.getEnvironment());
        }
        processBuilder.command().addAll(settings.commandLineAsList());
        if(settings.workingDirectoryExists()) {
            processBuilder.directory(settings.getWorkingDirectory());
        }
    }

    void httpShutdown() {
        Request request = settings.createHttpShutdownRequest();
        logger.info("Sending HTTP shutdown request to " + request.toString());
        Response response = null;
        try {
            response = restClient.call(request);
            logger.info("HTTP shutdown request sent successfully.");
        } catch (Exception e) {
            logger.error("Failed to send HTTP shutdown request: {}", e.getMessage());
            if(response != null) {
                logger.error("Response: {}", response.toString());
            }
        }
    }

    private boolean isProcessAlive() {
        if (process != null && process.isAlive()) {
            return true;
        }
        return false;
    }

    private void destroyProcess() {
        if (isProcessAlive()) {
            logger.info("Sending SIGTERM to process: {}", process.pid());
            process.destroy(); // Non-blocking: attempts graceful shutdown (SIGTERM)
            try {
                // Wait for a few seconds for the process to terminate gracefully
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    logger.warn("Process did not terminate gracefully. Forcing shutdown.");
                    process.destroyForcibly(); // Forceful shutdown (SIGKILL)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                process.destroyForcibly();
            }
        }
    }
}
