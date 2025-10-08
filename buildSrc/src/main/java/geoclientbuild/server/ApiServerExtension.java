package geoclientbuild.server;

import org.gradle.api.provider.Property;
import org.gradle.api.file.RegularFileProperty;

public interface ApiServerExtension {
    Property<String> getContextPath();
    Property<String> getHost();
    Property<Integer> getPort();
    Property<String> getScheme();
    RegularFileProperty getApiServerJar();
}
