package geoclientbuild.jarexec.exec;

import java.io.File;

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
