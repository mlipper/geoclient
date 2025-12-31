package geoclientbuild.docs;

import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

/**
 * Plugin to apply the Asciidoctor plugin allowing DocumentationPlugin clients
 * to avoid having to apply the Asciidoctor plugin if they don't need it.
 */
public class ApplyAsciidoctorPlugin implements Plugin<Project> {

    private Logger logger = Logging.getLogger(ApplyAsciidoctorPlugin.class);

    @Override
    public void apply(Project project) {
        logger.info("Applying Asciidoctor plugins to project: " + project.getName());
        project.getPlugins().apply("org.asciidoctor.jvm.convert");
        project.getPlugins().apply("org.asciidoctor.jvm.pdf");
        logger.info("Asciidoctor plugins applied successfully.");
    }

}
