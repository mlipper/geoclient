package geoclientbuild.server;

import java.util.List;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.ServiceReference;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.gradle.process.ExecResult;

public abstract class StartServer extends DefaultTask {
    public static final String TASK_NAME = "startApiServer";

    @ServiceReference(ApiServer.SERVICE_NAME)
    abstract Property<ApiServer> getApiServer();

    @Input
    abstract ListProperty<String> getArguments();

    @javax.inject.Inject
    public abstract ExecOperations getExecOperations();

    public StartServer() {
        setGroup("API Server");
        setDescription("Starts the Geoclient API server.");
    }

    @TaskAction
    public void startApiServer() {
        ApiServer server = getApiServer().get();
        String jarPath = server.getApiServerJarFile().getAbsolutePath();
        getLogger().lifecycle("Starting API server at {}", server.getUri());
        List<String> arguments = getArguments().get();
        ExecResult result = getExecOperations().exec(spec -> {
            spec.commandLine("java", "-jar", jarPath, arguments.toArray(new String[0]));
            spec.setStandardInput(System.in);
            spec.setStandardOutput(System.out);
            spec.setErrorOutput(System.err);
        });
        int exitCode = result.getExitValue();
        getLogger().lifecycle("API server process exited with code {}", exitCode);
    }
}
