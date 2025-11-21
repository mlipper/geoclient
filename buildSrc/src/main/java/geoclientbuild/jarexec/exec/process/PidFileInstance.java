package geoclientbuild.jarexec.exec.process;

import java.io.File;

import geoclientbuild.jarexec.exec.TargetProcess;

public class PidFileInstance<T extends File> implements TargetProcess<T> {

    private final T pidFile;

    public PidFileInstance (T pidFile) {
        this.pidFile = pidFile;
    }

    @Override
    public T getTarget() {
        return pidFile;
    }

}
