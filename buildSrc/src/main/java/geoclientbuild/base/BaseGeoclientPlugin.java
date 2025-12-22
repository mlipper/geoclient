package geoclientbuild.base;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class BaseGeoclientPlugin implements Plugin<Project> {
    private static final Logger logger = Logging.getLogger(BaseGeoclientPlugin.class);

    @Override
    public void apply(Project project) {
        // Apply the base plugin configuration
        project.getPlugins().apply("java");

        project.getTasks().register("exportEnvironment", EnvironmentExporter.class, task -> {
        });

        project.getTasks().register("setSystemProperties", SystemPropertiesSetter.class, task -> {
        });

        // Log the application of the base plugin
        logger.lifecycle("BaseGeoclientPlugin applied to project: {}", project.getName());
    }

}
