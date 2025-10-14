package geoclientbuild.server;

import org.gradle.testfixtures.ProjectBuilder;
import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A simple unit test for the 'geoclientbuild.apiserver' plugin.
 */
class GeoclientBuildServicePluginTest {
    @Test void pluginRegistersATask() {
        Project project = ProjectBuilder.builder().build();
        project.getPlugins().apply("geoclientbuild.apiserver");
        assertNotNull(project.getTasks().findByName(GeoclientBuildServicePlugin.APISERVER_INFO_TASK_NAME));
    }
}
