package geoclientbuild.jarexec.exec.process;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import geoclientbuild.jarexec.exec.TargetProcess;
import geoclientbuild.jarexec.exec.process.PidFileInstance;
import geoclientbuild.jarexec.exec.process.ProcessHandleInstance;
import geoclientbuild.jarexec.exec.process.ProcessInstance;

/*
 * Place holder for real process fixtures, mocks, tests, etc...
 */
public class TargetProcessTest {

    @Test
    void testGetTarget_process() {
        Process process = processFixture();
        TargetProcess<Process> targetProcess = new ProcessInstance<Process>(process);
        assertSame(process, targetProcess.getTarget());
    }

    @Test
    void testGetTarget_handle() {
        ProcessHandle handle = processHandleFixture();
        TargetProcess<ProcessHandle> targetProcess = new ProcessHandleInstance<ProcessHandle>(handle);
        assertSame(handle, targetProcess.getTarget());
    }

    @Test
    void testGetTarget_pidFile() {
        File pidFile = pidFileFixture();
        TargetProcess<File> targetProcess = new PidFileInstance<File>(pidFile);
        assertSame(pidFile, targetProcess.getTarget());
    }

    private File pidFileFixture() {
        return new File("foo.txt");
    }

    private ProcessHandle processHandleFixture() {
        return new ProcessHandle() {

            @Override
            public Stream<ProcessHandle> children() {
                return null;
            }

            @Override
            public int compareTo(ProcessHandle other) {
                return 0;
            }

            @Override
            public Stream<ProcessHandle> descendants() {
                return null;
            }

            @Override
            public boolean destroy() {
                return false;
            }

            @Override
            public boolean destroyForcibly() {
                return false;
            }

            @Override
            public Info info() {
                return null;
            }

            @Override
            public boolean isAlive() {
                return false;
            }

            @Override
            public CompletableFuture<ProcessHandle> onExit() {
                return null;
            }

            @Override
            public Optional<ProcessHandle> parent() {
                return Optional.empty();
            }

            @Override
            public long pid() {
                return 0;
            }

            @Override
            public boolean supportsNormalTermination() {
                return false;
            }

        };
    }

    private Process processFixture() {
        return new Process() {

            @Override
            public void destroy() {
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public InputStream getErrorStream() {
                return null;
            }

            @Override
            public InputStream getInputStream() {
                return null;
            }

            @Override
            public OutputStream getOutputStream() {
                return null;
            }

            @Override
            public int waitFor() throws InterruptedException {
                return 0;
            }
        };
    }
}
