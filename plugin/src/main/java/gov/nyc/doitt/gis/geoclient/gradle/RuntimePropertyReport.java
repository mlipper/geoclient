package gov.nyc.doitt.gis.geoclient.gradle;

import static gov.nyc.doitt.gis.geoclient.gradle.GeoclientPlugin.DEFAULT_REPORT_FILE_NAME_FORMAT;
import static gov.nyc.doitt.gis.geoclient.gradle.StringUtils.fill;
import static gov.nyc.doitt.gis.geoclient.gradle.StringUtils.nullSafeString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

public class RuntimePropertyReport extends DefaultTask {

    public static final String OUT_REPORT_FILE_FORMAT = "report file --> %s\n";
    public static final String OUT_REPORT_TITLE_FORMAT = "\n\n'%s' runtime\n";
    private final Logger logger;
    @Input
    private final RuntimePropertyExtension extension;
    private final Property<String> fileName;
    private final DirectoryProperty outputDir;

    @javax.inject.Inject
    public RuntimePropertyReport(RuntimePropertyExtension extension, Project project) {
        super();
        Objects.requireNonNull(extension, "Extension argument cannot be null");
        this.logger = project.getLogger();
        this.extension = extension;
        ObjectFactory objectFactory = project.getObjects();
        this.fileName = objectFactory.property(String.class);
        this.fileName.convention(String.format(DEFAULT_REPORT_FILE_NAME_FORMAT, extension.getName()));
        this.outputDir = project.getLayout().getBuildDirectory();
    }

    @Input
    public String getExtensionName() {
        return this.extension.getName();
    }

    @Input
    public DirectoryProperty getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String path) {
        this.outputDir.dir(path);
    }

    @Input
    public Property<String> getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName.set(name);
    }

    @TaskAction
    public void generateReport() throws IOException {
        File destination = getReportFile(this.outputDir.get().getAsFile(), this.fileName.get());
        String content = buildContent(destination);
        logger.quiet(content);
        logger.debug("Writing report to file {}", destination.getCanonicalFile());
        try (BufferedWriter output = new BufferedWriter(new FileWriter(destination));) {
            output.write(content);
        }
        logger.debug("Runtime property report written to '" + destination + "'");
    }

    protected String buildContent(File destination) throws IOException {

        // @formatter:off
        StringBuffer buffer = new StringBuffer();

        formatHeader(buffer, getExtensionName(), destination);

        extension.getRuntimeProperties().forEach((runtimeProperty) -> {
            appendSectionHeader(buffer, runtimeProperty.getName());
            runtimeProperty.getSources().get().forEach(p -> {
                  appendRow(buffer, "Name", p.getName())
                 .appendRow(buffer, "Value", nullSafeString(p.getValue()))
                 .appendRow(buffer, "Type", nullSafeString(p.getType()).toUpperCase())
                 .appendRow(buffer, "Resolution", nullSafeString(p.getResolution()).toUpperCase());
            });
        });

        return buffer.toString();
        // @formatter:on
    }

    protected void formatHeader(StringBuffer buffer, String containerName, File report) {
        String title = String.format(OUT_REPORT_TITLE_FORMAT, containerName);
        // @formatter:off
        buffer.append(title)
                .append(fill(title.length(), '-'))
                .append('\n')
                .append('\n')
                .append(String.format(OUT_REPORT_FILE_FORMAT, report));
        // @formatter:on
    }

    protected RuntimePropertyReport appendSectionHeader(StringBuffer buffer, String runtimePropertyName) {
        int fill = 16 - runtimePropertyName.length() - 2;
        String dashes = fill(fill, '-');
        String line = String.format("\n%s%s %s\n", "+-", dashes, runtimePropertyName);
        buffer.append(line);
        return this;
    }

    protected RuntimePropertyReport appendRow(StringBuffer buffer, String titleColumn, Object value) {
        buffer.append(String.format("%16s | %s\n", titleColumn, StringUtils.nullSafeString(value)));
        return this;
    }

    @OutputFile
    public File getReportFile(File dir, String fileName) throws IOException {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File destination = new File(dir, fileName);
        if (!destination.exists()) {
            destination.createNewFile();
        }
        return destination;
    }
}