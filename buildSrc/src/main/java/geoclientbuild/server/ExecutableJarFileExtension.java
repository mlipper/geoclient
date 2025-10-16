package geoclientbuild.server;

import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public interface ExecutableJarFileExtension {
    Property<String> getConfigurationName();
    ListProperty<String> getConsumerProjectPaths();
    Property<String> getProducerProjectPath();
    Property<String> getProducerJarTaskName();
}
