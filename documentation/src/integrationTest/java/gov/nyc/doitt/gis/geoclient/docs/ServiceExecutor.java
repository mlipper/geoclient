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
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                process.destroyForcibly();
            }
        }
    }
}
