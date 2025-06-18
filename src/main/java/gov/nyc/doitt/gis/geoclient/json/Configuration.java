package gov.nyc.doitt.gis.geoclient.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.function.Filter;

public class Configuration {
    private static final Logger log = LoggerFactory.getLogger(Configuration.class);
    private static final String DEFAULT_CONFIG_FILE = "geoclient.json";
    
    private final JsonNode jsonNode;

    public Configuration(String configFile) {
        this.jsonNode = loadConfig(configFile);
    }

    public Configuration() {
        this(DEFAULT_CONFIG_FILE);
    } 

    public GeosupportConfig loadGeosupportConfig() {
        return null;
    }

    public List<Filter> loadFilters() {
        List<JsonNode> filters = this.jsonNode.findValues("filter");
        List<Filter> result = new ArrayList<>(filters.size());
        for (JsonNode filterNode : filters) {
            // filterNode is an ObjectNode
            filterNode.iterator().forEachRemaining(e -> 
                e.properties().forEach(kv -> {
                    if (kv.getKey().equals("pattern")) {
                        result.add(new Filter(kv.getValue().asText()));
                    }
                })
            );
        }
        return result;
    }

    protected ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    protected JsonNode loadConfig(String configFile) {
        ObjectMapper mapper = getObjectMapper();
        try (var is = getClass().getClassLoader().getResourceAsStream("geoclient.json")) {
            //Map<String, String> result = mapper.readValue(is, new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {});
            JsonNode result = mapper.readTree(is);
            log.info("Configuration loaded: {}", result);
            return result;
        } catch (java.io.IOException | NullPointerException e) {
            log.error("Failed to load JSON configuration", e);
            throw new JsonConfigurationException("Could not load Geoclient JSON configuration", e);
        }
    }
}
