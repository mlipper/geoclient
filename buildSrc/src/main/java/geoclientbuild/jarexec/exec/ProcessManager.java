package geoclientbuild.jarexec.exec;

/**
 * Abstraction over various ways to destroy a process.
 *
 * @since 2.0
 * @author mlipper
 */
public interface ProcessManager<T> {
    /**
     * Destroys a process by using the strategy-appropriate instance from the given
     * {@link TargetProcess}.
     *
     * @param targetProcess
     * @return true if the process was successfully destroyed. Otherwise, returns
     *         false.
     */
    boolean destroy(TargetProcess<T> targetProcess);

    /**
     * Creates and starts a new process. Once running, returns a {@link ProcessHandle}.
     * @param settings for configuring the process before starting it.
     * @return {@link ProcessHandle} created from the running process.
     */
    ProcessHandle start(ProcessSettings settings);
}
