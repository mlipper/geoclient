package geoclientbuild.server;

import org.gradle.api.provider.Property;

public interface ApiServerExtension {
    Property<String> getContextPath();
    Property<String> getHost();
    Property<Integer> getPort();
    Property<String> getScheme();
}
