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
package gov.nyc.doitt.gis.geoclient.docs;

import java.util.concurrent.TimeUnit;

public class ServiceExecutor {
    public Process startServer() throws Exception {

        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "-jar",
            "/workspaces/geoclient/geoclient-service/build/libs/geoclient-service-2.0.4.jar",
            "--spring.profiles.active=docsamples");
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        Thread.sleep(10000);
        return process;
    }

    public void stopServer(Process process) {
        if (process.isAlive()) {
            process.destroy(); // Non-blocking: attempts graceful shutdown (SIGTERM)
            try {
                if (!process.waitFor(10, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                process.destroyForcibly();
            }
        }
    }
}
