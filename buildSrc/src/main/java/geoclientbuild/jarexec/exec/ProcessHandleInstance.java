package geoclientbuild.jarexec.exec;

public class ProcessHandleInstance<T extends ProcessHandle> implements TargetProcess<T> {

    private final T handle;

    public ProcessHandleInstance(T handle) {
        if (handle == null) {
            throw new IllegalArgumentException("ProcessHandle argument cannot be null.");
        }
        this.handle = handle;
    }

    @Override
    public T getTarget() {
        return handle;
    }

}
