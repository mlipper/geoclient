/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.GeosupportFunction;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;
import gov.nyc.doitt.gis.geoclient.jni.GeoclientJni;

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

    static class JsonRegistry {

        private final ConcurrentMap<String, FilterList> filterListRegistry;
        private final ConcurrentMap<String, Function> functionRegistry;
        private final ConcurrentMap<String, WorkArea> workAreaRegistry;

        public JsonRegistry() {
            this.filterListRegistry = new ConcurrentHashMap<>();
            this.functionRegistry = new ConcurrentHashMap<>();
            this.workAreaRegistry = new ConcurrentHashMap<>();
        }

        public boolean hasFilterList(String id) {
            return filterListRegistry.containsKey(id);
        }

        public boolean hasFunction(String id) {
            return functionRegistry.containsKey(id);
        }

        public boolean hasWorkArea(String id) {
            return workAreaRegistry.containsKey(id);
        }

        public FilterList getFilterList(String id) {
            return filterListRegistry.get(id);
        }

        public Function getFunction(String id) {
            return functionRegistry.get(id);
        }

        public WorkArea getWorkArea(String id) {
            return workAreaRegistry.get(id);
        }

        public void register(FilterList filterList) {
            log.debug("add({})", filterList);
            filterListRegistry.putIfAbsent(filterList.getId(), filterList);
        }

        public void register(Function function) {
            log.debug("add({})", function);
            functionRegistry.putIfAbsent(function.getId(), function);
        }

        public void register(WorkArea workArea) {
            log.debug("add({})", workArea);
            workAreaRegistry.putIfAbsent(workArea.getId(), workArea);
        }
    }

    static class WorkAreaBuilder {
        private String id;
        private Integer length;
        private List<Field> fields;
        private JsonRegistry jsonRegistry;
        private String outputFilterListId;

        public WorkAreaBuilder(JsonRegistry jsonRegistry) {
            this.jsonRegistry = jsonRegistry;
        }

        public WorkArea build() {
            SortedSet<Field> uniqueSet = new TreeSet<Field>();
            removeDuplicates(this.fields, uniqueSet);
            List<Filter> outputFilters = this.jsonRegistry.getFilterList(this.outputFilterListId).getFilters();
            WorkArea workArea = new WorkArea(this.id, uniqueSet, outputFilters);
            validate(workArea);
            return workArea;
        }

        public WorkAreaBuilder id(String id) {
            this.id = id;
            return this;
        }

        public WorkAreaBuilder length(Integer length) {
            this.length = length;
            return this;
        }

        public WorkAreaBuilder field(Field field) {
            if (this.fields == null) {
                this.fields = new ArrayList<>();
            }
            this.fields.add(field);
            return this;
        }

        public WorkAreaBuilder fields(List<Field> fields) {
            this.fields = fields;
            return this;
        }

        public WorkAreaBuilder outputFilterListId(String outputFilterListId) {
            this.outputFilterListId = outputFilterListId;
            return this;
        }

        private Field findDuplicate(Field field, SortedSet<Field> uniqueSet) {
            for (Field possibleMatch : uniqueSet) {
                if (field.compareTo(possibleMatch) == 0 || field.equals(possibleMatch)) {
                    return possibleMatch;
                }
            }
            return null;
        }

        protected List<Field> removeDuplicates(List<Field> configuredFields, SortedSet<Field> uniqueSet) {
            List<Field> duplicates = new ArrayList<Field>();
            for (Field field : configuredFields) {
                if (!uniqueSet.add(field)) {
                    Field fieldAlreadyInTheSet = findDuplicate(field, uniqueSet);
                    log.debug(
                        "Field [id={}] has a duplicate start and length of Field [id={}] and will NOT be added to WorkArea[id={}]",
                        field.getId(), fieldAlreadyInTheSet.getId(), this.id);
                    duplicates.add(field);
                }
            }
            return duplicates;
        }

        private void validate(WorkArea workArea) {
            if (workArea.length() != this.length) {
                JsonConfigurationException e = new JsonConfigurationException(
                    String.format("Error creating WorkArea %s: expected length %d but was %d.", workArea.getId(),
                        this.length, workArea.length()));
                log.error(e.getMessage());
                throw e;
            }
        }

    }

    private final Geoclient geoclient;
    private final JsonNode rootNode;
    private final JsonRegistry jsonRegistry;
    private final ObjectMapper mapper;

    public JsonConfiguration(String configFile, Geoclient geoclient) {
        // Must create ObjectMapper first because it is used by
        // #loadConfig(String file)
        this.mapper = getObjectMapper();
        this.rootNode = loadConfig(configFile);
        this.geoclient = geoclient;
        this.jsonRegistry = new JsonRegistry();
    }

    public JsonConfiguration(Geoclient geoclient) {
        this(DEFAULT_CONFIG_FILE, geoclient);
    }

    public JsonConfiguration() {
        this(DEFAULT_CONFIG_FILE, new GeoclientJni());
    }

    public void load() {
        loadFilters();
        loadWorkAreas();
        loadFunctions();
    }

    public JsonRegistry getJsonRegistry() {
        return this.jsonRegistry;
    }

    public Geoclient getGeoclient() {
        return this.geoclient;
    }

    protected Field getField(ObjectNode f) {
        String id = f.get("id").asText();
        int start = f.get("start").asInt();
        int length = f.get("length").asInt();
        FieldBuilder builder = new FieldBuilder(id, start, length);
        return builder.composite(f).input(f).alias(f).whitespaceSignificant(f).outputAlias(f).build();
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
        }
        catch (java.io.IOException | NullPointerException e) {
            log.error("Failed to load JSON configuration", e);
            throw new JsonConfigurationException("Could not load Geoclient JSON configuration", e);
        }
    }

    protected void loadFilters() {
        ArrayNode filterListArrayNode = (ArrayNode) this.rootNode.get("geoclient").get("filters");
        for (JsonNode filterListNode : filterListArrayNode) {
            String filterListId = filterListNode.get("id").asText();
            ArrayNode filtersNode = (ArrayNode) filterListNode.get("filter");
            List<Filter> filters = new ArrayList<>();
            for (JsonNode filter : filtersNode) {
                ObjectNode o = (ObjectNode) filter;
                filters.add(new Filter(o.get("pattern").asText()));
            }
            FilterList list = new FilterList();
            list.setId(filterListId);
            list.setFilters(filters);
            this.jsonRegistry.register(list);
            log.debug("Registered filterList: {}", list);
        }
    }

    protected void loadFunctions() {
        ArrayNode functionArrayNode = (ArrayNode) this.rootNode.get("geoclient").get("functions");
        for (JsonNode functionNode : functionArrayNode) {
            String id = functionNode.get("id").asText();
            String workAreaOneId = functionNode.get("workAreaOne").asText();
            if (!this.jsonRegistry.hasWorkArea(workAreaOneId)) {
                throw new JsonConfigurationException(
                    "Function " + id + " references a non-existent workAreaOne: " + workAreaOneId);
            }
            WorkArea workAreaOne = this.jsonRegistry.getWorkArea(workAreaOneId);
            WorkArea workAreaTwo = null;
            if (functionNode.has("workAreaTwo")) {
                String workAreaTwoId = functionNode.get("workAreaTwo").asText();
                if (!this.jsonRegistry.hasWorkArea(workAreaTwoId)) {
                    throw new JsonConfigurationException(
                        "Function " + id + " references a non-existent workAreaTwo: " + workAreaTwoId);
                }
                workAreaTwo = this.jsonRegistry.getWorkArea(workAreaTwoId);
            }
            if (!functionNode.has("configuration")) {
                throw new JsonConfigurationException("Function " + id + " is missing Configuration.");
            }
            DefaultConfiguration configuration = new DefaultConfiguration();
            ObjectNode configNode = (ObjectNode) functionNode.get("configuration");
            if (!configNode.has("requiredArguments")) {
                throw new JsonConfigurationException(
                    "Function " + id + " is missing requiredArguments in Configuration.");
            }
            ArrayNode requiredArgsArrayNode = (ArrayNode) configNode.get("requiredArguments");
            Map<String, Object> requiredArguments = new java.util.HashMap<>();
            for (JsonNode requiredArgumentNode : requiredArgsArrayNode) {
                String name = requiredArgumentNode.get("name").asText();
                String value = requiredArgumentNode.get("value").asText();
                requiredArguments.put(name, value);
            }
            configuration.setRequiredArguments(requiredArguments);
            Function function = new GeosupportFunction(id, workAreaOne, workAreaTwo, this.geoclient, configuration);
            this.jsonRegistry.register(function);
            log.debug("Registered function: {}", function);
        }
    }

    protected void loadWorkAreas() {
        ArrayNode workAreaArrayNode = (ArrayNode) this.rootNode.get("geoclient").get("workAreas");
        for (JsonNode workAreaNode : workAreaArrayNode) {
            WorkAreaBuilder builder = new WorkAreaBuilder(this.jsonRegistry);
            builder.id(workAreaNode.get("id").asText()).length(workAreaNode.get("length").asInt());
            // TODO FIXME: outputFilters is an array, not a single value
            ArrayNode outputFiltersArrayNode = (ArrayNode)workAreaNode.get("outputFilters");
            List<String> outputFilterListIds = new ArrayList<>();
            for (JsonNode textNode : outputFiltersArrayNode) {
               outputFilterListIds.add(textNode.asText());
            }
            builder.outputFilterListId(outputFilterListIds.get(0));
            //builder.id(workAreaNode.get("id").asText()).length(workAreaNode.get("length").asInt()).outputFilterListId(
            //    workAreaNode.get("outputFilters").asText());
            ArrayNode fieldArrayNode = (ArrayNode) workAreaNode.get("fields");
            for (JsonNode fieldNode : fieldArrayNode) {
                Field field = getField((ObjectNode) fieldNode);
                builder.field(field);
                log.debug("Adding field: {}", field);
            }
            WorkArea workArea = builder.build();
            this.jsonRegistry.register(workArea);
            log.debug("Registered work area: {}", workArea);
        }
    }

}
