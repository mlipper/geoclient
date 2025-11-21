package geoclientbuild.jarexec.exec;

/**
 * Abstraction over various ways to destroy a process.
 *
 * @since 2.0
 * @author mlipper
 */
public interface DestroyProcessStrategy<T> {
    /**
     * Destroys a process by using the strategy-appropriate instance from the given
     * {@link TargetProcess}.
     * The caller is responsible for instantiating the desired strategy
     * implementation and providing the required instance type.
     *
     * @param targetProcess
     * @return true if the process was successfully destroyed. Otherwise, returns
     *         false.
     */
    boolean destroy(TargetProcess<T> targetProcess);
}
