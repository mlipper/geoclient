package geoclientbuild.jarexec.exec;

import java.io.File;

/**
 * Silly class to encapsulate argument to different
 * {@link DestroyProcessStrategy} instances.
 * Replace with abstract generic ProcessInstance<T> if this proves too clumsy.
 */
public class ProcessHolder {

    private final Process process;
    private final ProcessHandle handle;
    private final File pidFile;

    public ProcessHolder(Process process) {
        this.process = process;
        this.handle = null;
        this.pidFile = null;
        if (process == null) {
            throw new IllegalArgumentException("Process argument cannot be null.");
        }
    }

    public ProcessHolder(ProcessHandle handle) {
        this.process = null;
        this.handle = handle;
        this.pidFile = null;
        if (handle == null) {
            throw new IllegalArgumentException("ProcessHandle argument cannot be null.");
        }
    }

    public ProcessHolder(File pidFile) {
        this.process = null;
        this.handle = null;
        this.pidFile = pidFile;
        if (pidFile == null) {
            throw new IllegalArgumentException("File argument cannot be null.");
        }
    }

    public Process getProcess() {
        return process;
    }

    public ProcessHandle getHandle() {
        return handle;
    }

    public File getPidFile() {
        return pidFile;
    }

    public boolean isProcess() {
        return process != null;
    }

    public boolean isPidFile() {
        return this.pidFile != null;
    }

    public boolean isHandle() {
        return this.handle != null;
    }

    public long getPid() {
        if (isPidFile()) {
            throw new IllegalStateException("PID is unknown for file " + pidFile.getAbsolutePath());
        }
        if (isHandle()) {
            return handle.pid();
        }
        return process.pid();
    }

    @Override
    public String toString() {
        if (isHandle()) {
            return "ProcessHolder [pid=" + handle.pid() + ", handle=" + handle + "]";
        }
        if (isPidFile()) {
            return "ProcessHolder [file=" + pidFile.getAbsolutePath() + "]";
        }
        return "ProcessHolder [pid=" + process.pid() + ", process=" + process + "]";
    }
}
