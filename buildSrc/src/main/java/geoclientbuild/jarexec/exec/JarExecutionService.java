package geoclientbuild.jarexec.exec;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import geoclientbuild.jarexec.settings.Settings;

public class JarExecutionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Settings settings;

    public JarExecutionService(Settings settings) {
        this.settings = settings;
    }

    public Process start() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        configureProcess(processBuilder);
        logger.info("Service starting up. Launching external process...");
        Process process = processBuilder.start();
        logger.info("External process started with PID: {}", process.pid());
        TimeUnit.SECONDS.sleep(10); // Wait for process to be fully initialized 
        logger.info("Service running...");
        return process;
    }

    public void stop(Process process) throws Exception {
    }
    public void stop(ProcessHandle process) throws Exception {
        logger.info("Service shutting down. Stopping external process...");
        destroyProcess(process); // Does nothing if process is already terminated or null
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

    private boolean isProcessAlive(Process process) {
        if (process != null && process.isAlive()) {
            return true;
        }
        return false;
    }

    private boolean isProcessAlive(ProcessHandle process) {
        if (process != null && process.isAlive()) {
            return true;
        }
        return false;
    }

    private void destroyProcess(ProcessHandle process) {
    }
    private void destroyProcess(Process process) {
        if (isProcessAlive(process)) {
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
