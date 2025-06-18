package gov.nyc.doitt.gis.geoclient.json;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;

public class JsonConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JsonConfiguration.class);
    private static final String DEFAULT_CONFIG_FILE = "geoclient.json";

    private final JsonNode rootNode;
    private final ObjectMapper mapper;

    public JsonConfiguration(String configFile) {
        // Must create ObjectMapper first because it is used by
        // #loadConfig(String file)
        this.mapper = getObjectMapper();
        this.rootNode = loadConfig(configFile);
    }

    public JsonConfiguration() {
        this(DEFAULT_CONFIG_FILE);
    }

    public GeosupportConfig loadGeosupportConfig() {
        return null;
    }

    public void load() {
        FilterList filterList = loadFilters();
        log.info("Loaded filters {}", filterList);
    }

    protected FilterList loadFilters() {
        JsonNode allFilters = this.rootNode.get("geoclient").get("filters");
        String compositeFiltersId = allFilters.get("id").asText();
        log.info("Found filters.id: {}", compositeFiltersId);
        List<JsonNode> filters = allFilters.findValues("filter");
        List<FilterNode> filterNodes = new ArrayList<>();
        for (JsonNode filterNode : filters) {
            // filterNode is an ObjectNode
            filterNode.iterator().forEachRemaining(e -> {
                String filterId = e.get("id").asText();
                String pattern = e.get("pattern").asText();
                filterNodes.add(new FilterNode(filterId, pattern));
            });
        }
        FilterList flist = new FilterList();
        flist.setId(compositeFiltersId);
        flist.setFilters(filterNodes);
        return flist;
    }

    protected ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // to prevent exception when encountering unknown property:
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // to allow coercion of JSON empty String ("") to null Object value:
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return objectMapper;
    }

    protected JsonNode loadConfig(String configFile) {
        try (var is = getClass().getClassLoader().getResourceAsStream("geoclient.json")) {
            JsonNode result = mapper.readTree(is);
            log.info("Configuration loaded: {}", result);
            return result;
        } catch (java.io.IOException | NullPointerException e) {
            log.error("Failed to load JSON configuration", e);
            throw new JsonConfigurationException("Could not load Geoclient JSON configuration", e);
        }
    }
}
