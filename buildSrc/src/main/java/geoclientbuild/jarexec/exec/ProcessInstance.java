package geoclientbuild.jarexec.exec;

public class ProcessInstance<T extends Process> implements TargetProcess<T>{

    private final T process;

    public ProcessInstance(T process) {
        if(process == null) {
            throw new IllegalArgumentException("Process argument cannot be null.");
        }
        this.process = process;
    }

    @Override
    public T getTarget() {
        return this.process;
    }
}
