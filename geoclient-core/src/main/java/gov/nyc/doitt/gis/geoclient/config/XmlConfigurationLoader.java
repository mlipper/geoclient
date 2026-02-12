/*
 * Copyright 2013-2026 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.config.jackson.FieldXml;
import gov.nyc.doitt.gis.geoclient.config.jackson.FilterXml;
import gov.nyc.doitt.gis.geoclient.config.jackson.FunctionConfiguration;
import gov.nyc.doitt.gis.geoclient.config.jackson.GeoclientXml;
import gov.nyc.doitt.gis.geoclient.config.jackson.GeoclientXmlReader;
import gov.nyc.doitt.gis.geoclient.config.jackson.OutputFiltersXml;
import gov.nyc.doitt.gis.geoclient.config.jackson.WorkAreaXml;
import gov.nyc.doitt.gis.geoclient.function.DefaultConfiguration;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.GeosupportFunction;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

/**
 * Loads the specified configuraton file to create and register the core
 * Geoclient runtime API objects.
 *
 * @author mlipper
 * @since 2.0.4
 */
public class XmlConfigurationLoader {

    private static final Logger log = LoggerFactory.getLogger(XmlConfigurationLoader.class);

    private Geoclient geoclient;
    private GeoclientXml geoclientXml;
    private Map<String, FunctionConfiguration> functionConfigurations;

    /**
     * Loads the specified configuraton file to create the core Geoclient
     * runtime objects. The instantiated functions and work areas are registered
     * in the Registry class as they created.
     *
     * @param geoclient Object that provides access to the geoclient native library.
     *                  This is used to call the Geosupport functions using the
     *                  instances created by this class.
     * @param file      the configuration file that defines the functions and work
     *                  areas to be created and registered.
     * @throws Exception if there is an error loading the configuration file or
     *                   creating the functions and work areas.
     */
    public void load(Geoclient geoclient, String file) throws Exception {
        this.geoclient = geoclient;
        GeoclientXmlReader reader = new GeoclientXmlReader(file);
        geoclientXml = reader.getGeoclientXml();
        this.functionConfigurations = geoclientXml.getFunctionConfigurations();
        registerFunctions();
    }

    /**
     * Registers the functions defined in the configuration file. This method
     * should only be called once and only by the load method. It is not defined
     * as private to allow for unit testing.
     *
     * @return a list of the registered functions (only used for testing purposes)
     * @throws Exception if the configuration is invalid or an unexpected error occurs.
     */
    List<Function> registerFunctions() throws Exception {
        List<Function> functions = new ArrayList<>();
        for (FunctionConfiguration functionConfiguration : functionConfigurations.values()) {
            WorkArea workAreaOne = registerWorkArea(functionConfiguration.getWorkAreaOne());
            String id = functionConfiguration.getId();
            DefaultConfiguration configuration = new DefaultConfiguration();
            configuration.setRequiredArguments(functionConfiguration.getConfiguration());
            WorkArea workAreaTwo = null;
            if (functionConfiguration.isTwoWorkAreas()) {
                workAreaTwo = registerWorkArea(functionConfiguration.getWorkAreaTwo());
                warnIfDuplicateFieldIds(workAreaOne, workAreaTwo);
            }
            Function function = new GeosupportFunction(id, workAreaOne, workAreaTwo, geoclient, configuration);
            Registry.addFunction(function);
            log.info("Registered function {}.", function.getId());
            functions.add(function);
        }
        return functions;
    }

    /**
     * Registers a work area defined in the configuration file. If the work area
     * has already been registered, the existing instance is returned.
     *
     * @param workAreaXml the work area definition from the configuration file
     * @return the registered work area
     * @throws Exception if the work area is invalid or an unexpected error occurs.
     */
    WorkArea registerWorkArea(WorkAreaXml workAreaXml) throws Exception {
        if (Registry.containsWorkArea(workAreaXml.getId())) {
            log.debug("Work area {} is already registered. Returning existing instance.", workAreaXml.getId());
            return Registry.getWorkArea(workAreaXml.getId());
        }
        log.info("Loading filters for work area {}.", workAreaXml.getId());
        List<Filter> filters = convert(workAreaXml.getOutputFilters());
        log.info("Loading fields for work area {}.", workAreaXml.getId());
        List<Field> configuredFields = convert(workAreaXml);
        SortedSet<Field> uniqueSet = new TreeSet<Field>();
        populateUniqueSet(configuredFields, uniqueSet, workAreaXml.getId());
        WorkArea workArea = new WorkArea(workAreaXml.getId(), uniqueSet, filters);
        // Currently, WorkArea uses the computed length based on the fields,
        // and validation happens externally using the configured expected length.
        // This maybe be worth rethinking.
        int expectedLength = workAreaXml.getLength();
        validate(workArea, expectedLength);
        Registry.addWorkArea(workArea);
        log.info("Registered work area {}.", workArea.getId());
        return workArea;
    }

    /**
     * Converts the WorkAreaXml configured FieldXml objects to a list of Fields.
     *
     * @param workAreaXml the WorkAreaXml object
     * @return list of Fields
     */
    List<Field> convert(WorkAreaXml workAreaXml) {
        List<Field> fields = new ArrayList<>();
        for (FieldXml fieldXml : workAreaXml.getFields()) {
            fields.add(convert(fieldXml));
        }
        return fields;
    }

    /**
     * Converts a FieldXml object to a Field object.
     *
     * @param fieldXml the FieldXml object to convert
     * @return the converted Field object
     */
    Field convert(FieldXml fieldXml) {
        return new Field(fieldXml.getId(), fieldXml.getStart(), fieldXml.getLength(), fieldXml.isComposite(),
            fieldXml.isInput(), fieldXml.getAlias(), fieldXml.isWhitespace(), fieldXml.getOutputAlias());
    }

    /**
     * Converts the OutputFiltersXml configured FilterXml objects to a list of Filters.
     *
     * @param outputFiltersXml the OutputFiltersXml object
     * @return list of Filters
     */
    List<Filter> convert(OutputFiltersXml outputFiltersXml) {
        List<Filter> filters = new java.util.ArrayList<>();
        if (outputFiltersXml == null || outputFiltersXml.getReference() == null) {
            log.warn(" OutputFiltersXML or OutputFiltersXML.getReference() returned null.");
            return filters;
        }
        String reference = outputFiltersXml.getReference();
        log.info("Loading filters for reference {}.", reference);
        List<FilterXml> filterXmls = this.geoclientXml.getFiltersForReference(reference);
        for (FilterXml filterXml : filterXmls) {
            filters.add(new Filter(filterXml.getPattern()));
        }
        return filters;
    }

    /**
     * Populates the unique set with the configured fields. If a field is a
     * duplicate (i.e. has the same start and length as another field), it will not
     * be added to the unique set and will be added to the list of duplicates. The
     * list of duplicates is returned at the end.
     *
     * @param configuredFields the list of fields defined for the work area in the
     *                         configuration
     * @param uniqueSet        the set that will be populated with the unique
     *                         fields. This set is used to determine if a field is a
     *                         duplicate based on the start and length of the field.
     * @param functionId       the id of the function that the work area belongs to.
     *                         This is used for logging purposes to provide context
     *                         about where the duplicate fields are defined.
     * @return the list of duplicate fields that were not added to the unique set.
     *         This value is used by the code but made available for testing
     *         purposes.
     */
    List<Field> populateUniqueSet(List<Field> configuredFields, SortedSet<Field> uniqueSet, String functionId) {
        List<Field> duplicates = new ArrayList<Field>();
        for (Field field : configuredFields) {
            if (!uniqueSet.add(field)) {
                Field fieldAlreadyInTheSet = findDuplicate(field, uniqueSet);
                log.debug(
                    "Field [id={}] has a duplicate start and length of Field [id={}] and will NOT be added to WorkArea [id={}]",
                    field.getId(), fieldAlreadyInTheSet.getId(), functionId);
                duplicates.add(field);
            }
        }
        return duplicates;
    }

    /**
     * Validates that the length of the work area matches the expected length. The
     * length of the work area is computed based on the fields defined for the work
     * area. If the length does not match, an exception is thrown.
     *
     * @param workArea the work area to validate
     * @param expectedLength the expected length of the work area as defined in the configuration.
     */
    void validate(WorkArea workArea, int expectedLength) {
        if (workArea.length() != expectedLength) {
            InvalidWorkAreaLengthException e = new InvalidWorkAreaLengthException(workArea, expectedLength);
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * Warns if there are duplicate field ids between the two work areas. A
     * duplicate field id is a field id that is defined in both work areas. If there
     * are duplicate field ids, a warning is logged with the duplicate field ids and
     * the work areas they are defined in.
     *
     * This is not considered an error because some fields are returned by both work
     * areas (e.g. the input fields from the first work area are often returned in
     * the second work area). However, it is worth logging a warning because it can
     * be by misconfiguration.
     *
     * @param workAreaOne work area one (part of all functions)
     * @param workAreaTwo work area two
     */
    void warnIfDuplicateFieldIds(WorkArea workAreaOne, WorkArea workAreaTwo) {
        List<String> duplicateIds = workAreaOne.getFieldIds();
        // Find the duplicates
        duplicateIds.retainAll(workAreaTwo.getFieldIds());
        if (duplicateIds.size() > 0) {
            String wa1Id = workAreaOne.getId();
            String wa2Id = workAreaTwo.getId();
            log.debug("=====================================================");
            log.debug("== The following field id's are defined in both WorkArea [id={}] and WorkArea [id={}].", wa1Id,
                wa2Id);
            for (String id : duplicateIds) {
                log.debug("== Field [id={}]", id);
            }
            log.debug("== Only the value from WorkArea [id={}] will be returned.", wa2Id);
            log.debug("=====================================================");
        }
    }

    /**
     * Finds a field in the unique set that has the same start and length as
     * the given field.
     *
     * @param field the field to find a duplicate for
     * @param uniqueSet the set of unique fields to search for a duplicate
     * @return the duplicate field if found, otherwise null
     */
    Field findDuplicate(Field field, SortedSet<Field> uniqueSet) {
        for (Field possibleMatch : uniqueSet) {
            if (field.compareTo(possibleMatch) == 0 || field.equals(possibleMatch)) {
                return possibleMatch;
            }
        }
        return null;
    }
}
