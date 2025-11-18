package geoclientbuild.jarexec.settings;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON-based configuration file loader for HTTP shutdown settings.
 */
public class HttpShutdownSettings {

    /**
     * Loads HTTP shutdown settings from a JSON file.
     *
     * @param file the JSON file containing HTTP shutdown settings
     * @return the loaded HttpShutdown settings
     * @throws Exception
     */
    public HttpShutdown load(File file) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(file, HttpShutdown.class);
    }
}
