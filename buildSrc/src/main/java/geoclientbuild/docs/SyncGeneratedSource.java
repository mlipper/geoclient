package geoclientbuild.docs;

import javax.inject.Inject;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileSystemOperations;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * Sync sample response data to the specified source directory.
 * Inspired by <a href=
 * "https://github.com/spring-projects/spring-boot/blob/main/buildSrc/src/main/java/org/springframework/boot/build/SyncAppSource.java">this
 * example</a> from the Spring Boot project.
 * 
 * @author Matthew Lipper
 */
public abstract class SyncGeneratedSource extends DefaultTask {

	private final FileSystemOperations fileSystemOperations;

    @Inject
    public SyncGeneratedSource(FileSystemOperations fileSystemOperations) {
        this.fileSystemOperations = fileSystemOperations;
    }

    @InputDirectory
    public abstract DirectoryProperty getSourceDirectory();

    @OutputDirectory
    public abstract DirectoryProperty getDestinationDirectory();

    @TaskAction
    void syncGeneratedSources() {
        this.fileSystemOperations.sync((copySpec) -> {
            copySpec.from(getSourceDirectory());
            copySpec.into(getDestinationDirectory());
        });
    }
}
