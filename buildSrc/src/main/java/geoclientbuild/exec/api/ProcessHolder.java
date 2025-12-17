/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package geoclientbuild.exec.api;

import java.io.File;

/**
 * Silly class to encapsulate argument to different
 * {@link ProcessManager} instances.
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
