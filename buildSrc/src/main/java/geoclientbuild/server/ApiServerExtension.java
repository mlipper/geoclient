package geoclientbuild.server;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.file.RegularFileProperty;

public interface ApiServerExtension {

    public static final String EXTENSION_NAME = "apiserver";

    RegularFileProperty getServerJar();
    RegularFileProperty getPidFile();
    Property<String> getContextPath();
    Property<String> getJavaCommand();
    Property<String> getHost();
    Property<Integer> getPort();
    Property<String> getScheme();
    MapProperty<String, String> getEnvironment();
    ListProperty<String> getArguments();
    Property<Long> getSleepSecondsAfterStart();
}
