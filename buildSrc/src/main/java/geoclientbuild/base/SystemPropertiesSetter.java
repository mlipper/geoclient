package geoclientbuild.base;

import org.gradle.api.DefaultTask;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.tasks.TaskAction;

public abstract class SystemPropertiesSetter extends DefaultTask {

    public abstract MapProperty<String, String> getSystemProperties();

    @TaskAction
    public void setSystemProperties() {
       getSystemProperties().get().forEach((k, v) -> System.setProperty(k, v));
    }
}
