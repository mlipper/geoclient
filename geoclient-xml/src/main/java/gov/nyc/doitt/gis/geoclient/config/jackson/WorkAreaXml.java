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
package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class WorkAreaXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private boolean isWA1;

    @JacksonXmlProperty(localName = "length")
    private int length;

    // <outputFilters reference="allFilters" />
    @JacksonXmlProperty(localName = "outputFilters")
    private OutputFiltersXml outputFilters;

    @JacksonXmlElementWrapper(localName = "fields")
    @JacksonXmlProperty(localName = "field")
    private List<FieldXml> fields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIsWA1() {
        return isWA1;
    }

    public void setIsWA1(boolean isWA1) {
        this.isWA1 = isWA1;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public OutputFiltersXml getOutputFilters() {
        return outputFilters;
    }

    public void setOutputFilters(OutputFiltersXml outputFilters) {
        this.outputFilters = outputFilters;
    }

    public List<FieldXml> getFields() {
        return fields;
    }

    public void setFields(List<FieldXml> fields) {
        this.fields = fields;
    }
}
