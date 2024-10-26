package geoclientbuild.conventions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.plugins.quality.Checkstyle;
import org.gradle.api.plugins.quality.CheckstyleExtension;
import org.gradle.api.plugins.quality.CheckstylePlugin;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;
import org.gradle.external.javadoc.CoreJavadocOptions;

class JavaConventions {
    private static final String SOURCE_AND_TARGET_COMPATIBILITY = "17";

/*
	private void configureJarManifestConventions(Project project) {
		ExtractResources extractLegalResources = project.getTasks()
			.create("extractLegalResources", ExtractResources.class);
		extractLegalResources.getDestinationDirectory().set(project.getLayout().getBuildDirectory().dir("legal"));
		extractLegalResources.getResourceNames().set(Arrays.asList("LICENSE.txt", "NOTICE.txt"));
		extractLegalResources.getProperties().put("version", project.getVersion().toString());
		SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
		Set<String> sourceJarTaskNames = sourceSets.stream()
			.map(SourceSet::getSourcesJarTaskName)
			.collect(Collectors.toSet());
		Set<String> javadocJarTaskNames = sourceSets.stream()
			.map(SourceSet::getJavadocJarTaskName)
			.collect(Collectors.toSet());
		project.getTasks().withType(Jar.class, (jar) -> project.afterEvaluate((evaluated) -> {
			jar.metaInf((metaInf) -> metaInf.from(extractLegalResources));
			jar.manifest((manifest) -> {
				Map<String, Object> attributes = new TreeMap<>();
				attributes.put("Automatic-Module-Name", project.getName().replace("-", "."));
				attributes.put("Build-Jdk-Spec", SOURCE_AND_TARGET_COMPATIBILITY);
				attributes.put("Built-By", "Mattew Lipper");
				attributes.put("Implementation-Title",
						determineImplementationTitle(project, sourceJarTaskNames, javadocJarTaskNames, jar));
				attributes.put("Implementation-Version", project.getVersion());
				manifest.attributes(attributes);
			});
		}));
	}
*/
	private String determineImplementationTitle(Project project, Set<String> sourceJarTaskNames,
			Set<String> javadocJarTaskNames, Jar jar) {
		if (sourceJarTaskNames.contains(jar.getName())) {
			return "Source for " + project.getName();
		}
		if (javadocJarTaskNames.contains(jar.getName())) {
			return "Javadoc for " + project.getName();
		}
		return project.getDescription();
	}
}