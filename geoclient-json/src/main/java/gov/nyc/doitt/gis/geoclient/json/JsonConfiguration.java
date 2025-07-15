package gov.nyc.doitt.gis.geoclient.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;
import gov.nyc.doitt.gis.geoclient.config.WorkAreaConfig;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;

public class JsonConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JsonConfiguration.class);
    private static final String DEFAULT_CONFIG_FILE = "geoclient.json";

    static class FieldBuilder {
        private String id;
        private Integer start;
        private Integer length;
        private boolean composite;
        private boolean input;
        private String alias;
        private boolean whitespaceSignificant;
        private String outputAlias;

        public FieldBuilder(String id, Integer start, Integer length) {
            this.id = id;
            this.start = start;
            this.length = length;
            this.composite = false;
            this.input = false;
            this.alias = null;
            this.whitespaceSignificant = false;
            this.outputAlias = null;
        }

        public FieldBuilder composite(ObjectNode node) {
            if (node.has("composite")) {
                this.composite = node.get("composite").asBoolean();
            }
            return this;
        }

        public FieldBuilder input(ObjectNode node) {
            if (node.has("input")) {
                this.input = node.get("input").asBoolean();
            }
            return this;
        }

        public FieldBuilder alias(ObjectNode node) {
            if (node.has("alias")) {
                this.alias = node.get("alias").asText();
            }
            return this;
        }

        public FieldBuilder whitespaceSignificant(ObjectNode node) {
            if (node.has("whitespaceSignificant")) {
                this.whitespaceSignificant = node.get("whitespaceSignificant").asBoolean();
            }
            return this;
        }

        public FieldBuilder outputAlias(ObjectNode node) {
            if (node.has("outputAlias")) {
                this.outputAlias = node.get("outputAlias").asText();
            }
            return this;
        }

        public Field build() {
            return new Field(id, start, length, composite, input, alias, whitespaceSignificant, outputAlias);
        }

    }

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
        GeosupportConfig geosupportConfig = loadGeosupportConfig();
        log.info("Loaded GeosupportConfig: {}", geosupportConfig);
        List<FilterList> filterList = loadFilters();
        log.info("Loaded filters {}", filterList);
        List<WorkAreaConfig> waConfigs = loadWorkAreas(filterList);
        log.info("Loaded work area configurations {}", waConfigs);
    }

    protected List<FilterList> loadFilters() {
        ArrayNode fListArray = (ArrayNode)this.rootNode.get("geoclient").get("filters");
        List<FilterList> listOfFilterLists = new ArrayList<>();
        for (JsonNode fListNode : fListArray) {
           String fListId = fListNode.get("id").asText(); 
           ArrayNode filtersNode = (ArrayNode)fListNode.get("filter");
           List<Filter> filters = new ArrayList<>();
           for (JsonNode filter : filtersNode) {
               ObjectNode o = (ObjectNode)filter;
               filters.add(new Filter(o.get("pattern").asText()));
           }
           FilterList list = new FilterList();
           list.setId(fListId);
           list.setFilters(filters);
           listOfFilterLists.add(list);
        }
        return listOfFilterLists;
    }

    protected List<WorkAreaConfig> loadWorkAreas(List<FilterList> filterList) {
        List<WorkAreaConfig> workAreaConfigs = new ArrayList<>();
        ArrayNode workAreas = (ArrayNode)this.rootNode.get("geoclient").get("workAreas");
        for (JsonNode workAreaNode : workAreas) {
            String id = workAreaNode.get("id").asText();
            Integer length = workAreaNode.get("length").asInt();
            boolean isWorkAreaOne = false;
            if (workAreaNode.has("isWA1")) {
                isWorkAreaOne = workAreaNode.get("isWA1").asBoolean();
            }
            List<Filter> filters = new ArrayList<>();
            if (workAreaNode.hasNonNull("filters")) {
                String filtersId = workAreaNode.get("filters").get("id").asText();
                Optional<FilterList> theList = filterList.stream().filter(fl -> filtersId.equals(fl.getId())).findFirst();
                if (theList.isPresent()) {
                    filters.addAll(theList.get().getFilters());
                }
            }
            List<Field> fields = new ArrayList<Field>();
            ArrayNode fieldsNode = (ArrayNode)workAreaNode.get("fields");
            for (JsonNode fieldNode : fieldsNode) {
                fields.add(getField((ObjectNode)fieldNode));
            }
            WorkAreaConfig workAreaConfig = new WorkAreaConfig(id, length, isWorkAreaOne, fields, filters);
            workAreaConfigs.add(workAreaConfig);

        }
        return workAreaConfigs;
    }

    protected Field getField(ObjectNode f) {
        String id = f.get("id").asText();
        int start = f.get("start").asInt();
        int length = f.get("length").asInt();
        FieldBuilder builder = new FieldBuilder(id, start, length);
        return builder.composite(f)
            .input(f)
            .alias(f)
            .whitespaceSignificant(f)
            .outputAlias(f).build();
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
