package geoclientbuild.server;

import java.net.URI;
import java.util.Optional;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public abstract class StopServer extends DefaultTask {

    public static final String TASK_NAME = "stopServer";
    public static final String DEFAULT_ENDPOINT = "/actuator/shutdown";

    @Input
    abstract Property<URI> getURI();

    @Input
    abstract RegularFileProperty getPidFile();

    public StopServer() {
        setGroup("API Server");
        setDescription("Stops the Geoclient API server.");
    }

    @TaskAction
    public void stopServer() throws Exception {
        // HTTP shutdown
        //URI uri = getURI().get();
        //getLogger().lifecycle("Stopping API server with empty-body POST to {}", uri.toString());
        //Request postRequest = new Request("stop-server", getURI().get().toString(), Request.HTTP_POST_METHOD, null);
        //RestClient restClient = new HttpClient();
        //String response = restClient.call(postRequest).getBody();
        //getLogger().lifecycle("API server shutdown response: {}", response);
        String pid = FileUtils.readTextFile(getPidFile());
        Optional<ProcessHandle> processHandle = ProcessHandle.of(Long.valueOf(pid));
        if(processHandle.isPresent()) {
            stopProcess(processHandle.get());
        }
    }

    private void stopProcess(ProcessHandle processHandle) {
        if(processHandle != null && processHandle.isAlive()) {
            if(!processHandle.destroy()) {
                if(processHandle != null && processHandle.isAlive()) {
                    if(!processHandle.destroyForcibly()) {
                        throw new IllegalStateException(processHandle.info().toString());
                    }
                }
            }
        }
    }
}
