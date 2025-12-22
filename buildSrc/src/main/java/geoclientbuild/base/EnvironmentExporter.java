package geoclientbuild.base;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.tasks.TaskAction;

public abstract class EnvironmentExporter extends DefaultTask {

    public abstract MapProperty<String, String> getEnvironmentVariables();

    @TaskAction
    public void exportEnvironment() {
        getEnvironmentVariables().get().forEach((k, v) -> {
            System.getenv().put(k, v);
        });
    }
}
