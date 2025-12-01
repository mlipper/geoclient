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
