package geoclientbuild.jarexec;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileUtils {
    private FileUtils() {}

    /**
     * 
     * Write a text file creating any missing intermediate directories if necessary.
     * 
     * This method is copied from the <a href="https://github.com/gradle/native-platform/blob/master/buildSrc/src/main/java/gradlebuild/WriteNativeVersionSources.java">native-platform</a> project.
     * 
     * @param contents
     * @param targetLocation
     * @throws IOException
     */
    public static void writeTextFile(String contents, File targetLocation) throws IOException {
        Path path = targetLocation.toPath();
        Files.createDirectories(path.getParent());
        Files.write(path, contents.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 
     * Read a small text file (< 1G) into a String.
     *
     * @param contents
     * @param targetLocation
     * @throws IOException
     */
    public static String readTextFile(File targetLocation) throws IOException {
        Path path = targetLocation.toPath();
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    /**
     * 
     * Read an InputStream into String.
     *
     * This method is copied from the <a href="https://github.com/gradle/gradle-org-conventions-plugin/blob/master/src/main/java/io/github/gradle/conventions/customvalueprovider/DevelocityConventions.java">gradle-org-conventions-plugin</a> project.
     * @param is
     * @return
     */
    public static String toString(InputStream is) {
        return new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
    }
}
