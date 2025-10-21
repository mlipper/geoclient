package geoclientbuild.server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Stream;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.services.ServiceReference;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecOperations;
import org.gradle.process.ExecResult;

public abstract class StartServer extends DefaultTask {
    public static final String TASK_NAME = "startServer";

    @ServiceReference(ApiServer.SERVICE_NAME)
    abstract Property<ApiServer> getApiServer();

    @Input
    abstract ListProperty<String> getArguments();

    @OutputFile
    abstract RegularFileProperty getOutputFile();

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
         File outputFile = getOutputFile().getAsFile().get();
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
            writer.println("API Server started with the following parameters:");
            writer.println("JAR Path: " + jarPath);
            writer.println("Arguments: " + String.join(" ", arguments));
        } catch (IOException e) {
            getLogger().error("Failed to write output file: " + outputFile.getAbsolutePath(), e);
        }
        ExecResult result = getExecOperations().javaexec(spec -> {
            //spec.commandLine("java", "-jar", jarPath, arguments.toArray(new String[0]));
            spec.classpath(jarPath);
            spec.args(arguments);
            spec.setStandardInput(System.in);
            spec.setStandardOutput(System.out);
            spec.setErrorOutput(System.err);
        });
        int exitCode = result.getExitValue();
        getLogger().lifecycle("API server process exited with code {}", exitCode);
        try (Stream<String> stream = Files.lines(outputFile.toPath())) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            getLogger().error("Failed to read output file: " + outputFile.getAbsolutePath(), e);
        }
    }
}
