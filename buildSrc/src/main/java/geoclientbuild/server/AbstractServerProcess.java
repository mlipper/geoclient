package geoclientbuild.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public abstract class AbstractServerProcess extends DefaultTask {

    @Input
    abstract Property<URI> getUri();

    protected boolean deletePidFile(RegularFileProperty pidFile) throws IOException {
        Path pidFilePath = pidFileAsFile(pidFile).toPath();
        if(Files.deleteIfExists(pidFilePath)) {
            getLogger().lifecycle("Deleted PID file {}.", pidFilePath.toAbsolutePath());
            return true;
        }
        getLogger().warn("Failed to delete PID file {} because it does not exist.", pidFilePath.toAbsolutePath());
        return false;
    }

    protected ApiServerException deletePidFileAndBuildException(RegularFileProperty pidFile, String message, Throwable cause) {
        RegularFile regularFile = pidFile.getOrNull();
        String fileName = regularFile != null ? regularFile.getAsFile().getAbsolutePath() : "null";
        try {
            if(deletePidFile(pidFile)) {
                throw new ApiServerException(message, cause);
            }
        } catch (Exception ignored) {
            getLogger().error("Error deleting PID file {}.", fileName);
        }
        String msg = String.format("%s WARNING: PID file %s could not be deleted.", message, fileName);
        throw new ApiServerException(msg, cause);
    }

    protected File pidFileAsFile(RegularFileProperty pidFile) {
        return pidFile.get().getAsFile();
    }

    protected long readPidFile(RegularFileProperty pidFile) throws IOException {
        Path pidFilePath = pidFileAsFile(pidFile).toPath();
        String pid = Files.readString(pidFilePath);
        getLogger().lifecycle("Read PID {} from file {}.", pid, pidFilePath.toAbsolutePath());
        return Long.valueOf(pid);
    }

    protected void writePidFile(RegularFileProperty pidFile, long pid) throws IOException {
        File pidFilePath = pidFileAsFile(pidFile);
        try (FileWriter writer = new FileWriter(pidFilePath)) {
            String pidString = String.valueOf(pid);
            writer.write(pidString);
            getLogger().lifecycle("PID file created {} with value {}.", pidFilePath.getAbsolutePath(), pidString);
        }
    }
}
