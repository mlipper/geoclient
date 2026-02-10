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
package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;
import java.util.Map;

/**
 * Helper for configuring domain objects for testing. Not intended to be used outside of this test package.
 */
public class Builders {

    public static class ConfigurationXmlBuilder {
        private List<RequiredArgumentXml> requiredArguments;

        public static ConfigurationXmlBuilder builder() {
            return new ConfigurationXmlBuilder();
        }

        public ConfigurationXmlBuilder requiredArguments(List<RequiredArgumentXml> requiredArguments) {
            this.requiredArguments = requiredArguments;
            return this;
        }

        public ConfigurationXml build() {
            ConfigurationXml configurationXml = new ConfigurationXml();
            configurationXml.setRequiredArguments(requiredArguments);
            return configurationXml;
        }
    }

    public static class RequiredArgmunentsXmlBuilder {
        private List<RequiredArgumentXml> requiredArguments;
        public static RequiredArgmunentsXmlBuilder builder() {
            return new RequiredArgmunentsXmlBuilder();
        }

        public RequiredArgmunentsXmlBuilder requiredArguments(Map<String, String> namesAndValues) {
            requiredArguments = namesAndValues.entrySet().stream().map(entry -> {
                RequiredArgumentXml requiredArgument = new RequiredArgumentXml();
                requiredArgument.setName(entry.getKey());
                requiredArgument.setValue(entry.getValue());
                return requiredArgument;
            }).toList();
            return this;
        }

        public List<RequiredArgumentXml> build() {
            return requiredArguments;
        }
    }

    public static class FiltersXmlBuilder {
        private String id;
        private List<FilterXml> filters;

        public static FiltersXmlBuilder builder() {
            return new FiltersXmlBuilder();
        }

        public FiltersXmlBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FiltersXmlBuilder filters(Map<String, String> idsAndPatterns) {
            filters = idsAndPatterns.entrySet().stream().map(entry -> {
                FilterXml filter = new FilterXml();
                filter.setId(entry.getKey());
                filter.setPattern(entry.getValue());
                return filter;
            }).toList();
            FiltersXml filtersXml = new FiltersXml();
            filtersXml.setFilters(filters);
            return this;
        }

        public FiltersXml build() {
            FiltersXml filtersXml = new FiltersXml();
            filtersXml.setId(id);
            filtersXml.setFilters(filters);
            return filtersXml;
        }
    }

    public static class OutputFiltersXmlBuilder {
        private String reference;
        private List<FilterXml> filters;

        public static OutputFiltersXmlBuilder builder() {
            return new OutputFiltersXmlBuilder();
        }

        public OutputFiltersXmlBuilder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public OutputFiltersXmlBuilder filters(List<FilterXml> filters) {
            this.filters = filters;
            return this;
        }

        public OutputFiltersXml build() {
            OutputFiltersXml outputFiltersXml = new OutputFiltersXml();
            outputFiltersXml.setReference(reference);
            outputFiltersXml.setFilters(filters);
            return outputFiltersXml;
        }
    }

    public static class FieldXmlBuilder {
        private String id;
        private int start;
        private int length;
        // Nullable fields
        private String type = null;
        private boolean input = false;
        private String alias = null;
        private String outputAlias = null;
        private boolean whitespace;

        public static FieldXmlBuilder builder() {
            return new FieldXmlBuilder();
        }

        public FieldXmlBuilder id(String id) {
            this.id = id;
            return this;
        }

        public FieldXmlBuilder start(int start) {
            this.start = start;
            return this;
        }

        public FieldXmlBuilder length(int length) {
            this.length = length;
            return this;
        }

        public FieldXmlBuilder type(String type) {
            this.type = type;
            return this;
        }

        public FieldXmlBuilder input(boolean input) {
            this.input = input;
            return this;
        }

        public FieldXmlBuilder alias(String alias) {
            this.alias = alias;
            return this;
        }

        public FieldXmlBuilder outputAlias(String outputAlias) {
            this.outputAlias = outputAlias;
            return this;
        }

        public FieldXmlBuilder whitespace(boolean whitespace) {
            this.whitespace = whitespace;
            return this;
        }

        public FieldXml build() {
            FieldXml fieldXml = new FieldXml();
            fieldXml.setId(id);
            fieldXml.setStart(start);
            fieldXml.setLength(length);
            fieldXml.setType(type);
            fieldXml.setInput(input);
            fieldXml.setAlias(alias);
            fieldXml.setOutputAlias(outputAlias);
            fieldXml.setWhitespace(whitespace);
            return fieldXml;
        }
    }

    public static class WorkAreaXmlBuilder {
        private String id;
        private boolean isWA1;
        private int length;
        private OutputFiltersXml outputFilters;
        private List<FieldXml> fields;

        public static WorkAreaXmlBuilder builder() {
            return new WorkAreaXmlBuilder();
        }

        public WorkAreaXmlBuilder id(String id) {
            this.id = id;
            return this;
        }

        public WorkAreaXmlBuilder isWA1(boolean isWA1) {
            this.isWA1 = isWA1;
            return this;
        }

        public WorkAreaXmlBuilder length(int length) {
            this.length = length;
            return this;
        }

        public WorkAreaXmlBuilder outputFilters(OutputFiltersXml outputFilters) {
            this.outputFilters = outputFilters;
            return this;
        }

        public WorkAreaXmlBuilder fields(List<FieldXml> fields) {
            this.fields = fields;
            return this;
        }

        public WorkAreaXml build() {
            WorkAreaXml workAreaXml = new WorkAreaXml();
            workAreaXml.setId(id);
            workAreaXml.setIsWA1(isWA1);
            workAreaXml.setLength(length);
            workAreaXml.setOutputFilters(outputFilters);
            workAreaXml.setFields(fields);
            return workAreaXml;
        }
    }
}
