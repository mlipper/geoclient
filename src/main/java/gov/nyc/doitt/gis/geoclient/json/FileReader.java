package gov.nyc.doitt.gis.geoclient.json;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {


    public static Path getPath(String filePath) {
        return Paths.get(filePath);
    }
}
