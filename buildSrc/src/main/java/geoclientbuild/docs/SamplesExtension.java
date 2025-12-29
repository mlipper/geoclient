package geoclientbuild.docs;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;

/**
 * Gradle plugin extension to configure generated documentation source as
 * input for the AsciiDoctor plugin.
 */
public interface SamplesExtension {
    Property<String> getDocumentationTaskName();
    RegularFileProperty getSampleRequestsFile();
    DirectoryProperty getSamplesResponseDirectory();
    DirectoryProperty getSamplesSourceDirectory();
    Property<Boolean> getSyncSamples();
}
