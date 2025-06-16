package gov.nyc.doitt.gis.geoclient.json;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

class ConfigurationTest {

    private static final Logger log = LoggerFactory.getLogger(ConfigurationTest.class);

    @SuppressWarnings("unchecked")
    @Test
    void jacksonBasic() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (var is = getClass().getClassLoader().getResourceAsStream("geoclient.json")) {
            assertNotNull(is, "config.json not found in classpath");
            Map<String, String> result = mapper.readValue(is, Map.class);
            log.info("Configuration loaded: {}", result);
            assertNotNull(result);
        }
    }
}
