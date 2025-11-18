package geoclientbuild.jarexec.settings;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

public abstract class AbstractSettingsTest {

    protected Map<String, String> environment = Map.of(
        "KEY1", "VALUE1",
        "KEY2", "VALUE2"
    );

    protected List<String> arguments = List.of(
        "arg1",
        "arg2",
        "arg3"
    );

    protected File jarFile = new File("/app/example.jar");

    protected File httpShutdownFile;

    protected String shutDownHttpMethod = "POST";

    protected String shutDownUrl = "http://localhost:8080/shutdown";

    protected AbstractSettingsTest() {
        // Should be on the test classpath
        URL fileUrl = this.getClass().getResource("/http-shutdown.json");
        httpShutdownFile = new File(fileUrl.getFile());
        if (!httpShutdownFile.exists()) {
            throw new IllegalStateException("Test httpShutdownFile does not exist: " + httpShutdownFile.getAbsolutePath());
        }
    }

    protected Settings settingsFixture() {
        Settings settings = new Settings();
        settings.setArguments(arguments);
        settings.setEnvironment(environment);
        settings.setJarFile(jarFile);
        settings.setHttpShutdownFile(httpShutdownFile);
        settings.setWorkingDirectory(jarFile);
        return settings;
    }
}
