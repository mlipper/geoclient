package geoclientbuild.server;

import java.util.Optional;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

public abstract class StopServer extends AbstractServerProcess {

    public static final String TASK_NAME = "stopServer";
    public static final String DEFAULT_ENDPOINT = "/actuator/shutdown";

    @InputFile
    abstract RegularFileProperty getPidFile();

    public StopServer() {
        setGroup("API Server");
        setDescription("Stops the Geoclient API server.");
    }

    @TaskAction
    public void stopServer() throws ApiServerException {
        long pid = -1L;
        try {
            pid = readPidFile(getPidFile());
            Optional<ProcessHandle> processHandle = ProcessHandle.of(pid);
            if(processHandle.isPresent()) {
                getLogger().lifecycle("Stopping server process {}.", pid);
                stopProcess(processHandle.get(), pid);
                getLogger().lifecycle("Server process {} has been stopped.", pid);
            }
            deletePidFile(getPidFile());
        } catch (Exception e) {
            getLogger().error("Error stopping server process.", e);
            ApiServerException apiServerException = deletePidFileAndBuildException(getPidFile(), "Failed to stop API server.", e);
            throw apiServerException;
        }
    }

    private void stopProcess(ProcessHandle processHandle, long pid) {
        if(processHandle != null && processHandle.isAlive()) {
            ProcessHandle.Info phInfo = processHandle.info();
            getLogger().debug("Attempting to destroy process {} with ProcessHandle: {}", pid, phInfo.toString());
            if(!processHandle.destroy()) {
                getLogger().debug("Failed to destroy process {}. Attempting to destroy it forcibly.", pid);
                if(processHandle != null && processHandle.isAlive()) {
                    if(!processHandle.destroyForcibly()) {
                        throw new IllegalStateException(processHandle.info().toString());
                    }
                }
            }
        }
    }
}
