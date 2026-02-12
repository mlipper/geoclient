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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "geoclient")
public class GeoclientXml {

    @JacksonXmlProperty(localName = "filters")
    private FiltersXml filters;

    @JacksonXmlProperty(localName = "workAreas")
    private WorkAreasXml workAreas;

    @JacksonXmlProperty(localName = "functions")
    private FunctionsXml functions;

    public Map<String, FunctionConfiguration> getFunctionConfigurations() {
        Map<String, FunctionConfiguration> result = new HashMap<>();
        for (FunctionXml function : functions.getFunctions()) {
            WorkAreaXml workAreaOne = findWorkAreaById(function.getWorkAreaOne().getReference());
            WorkAreaXml workAreaTwo = null;
            if (function.isTwoWorkAreas()) {
                workAreaTwo = findWorkAreaById(function.getWorkAreaTwo().getReference());
            }
            FunctionConfiguration functionConfiguration = new FunctionConfiguration(function.getId(),
                function.getConfiguration(), workAreaOne, workAreaTwo);
            result.put(function.getId(), functionConfiguration);
        }
        return result;
    }

    public List<FilterXml> getFiltersForReference(String reference) {
        FiltersXml filtersXml = getFilters();
        if (filtersXml.getId().equals(reference)) {
            return filtersXml.getFilters();
        }
        throw new IllegalArgumentException("No filters found for reference '" + reference + "'");
    }

    public FiltersXml getFilters() {
        return filters;
    }

    public void setFilters(FiltersXml filters) {
        this.filters = filters;
    }

    public WorkAreasXml getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(WorkAreasXml workAreas) {
        this.workAreas = workAreas;
    }

    public FunctionsXml getFunctions() {
        return functions;
    }

    public void setFunctions(FunctionsXml functions) {
        this.functions = functions;
    }

    FunctionXml findFunctionById(String id) {
        FunctionXml result = functions.getFunctions().stream().filter(f -> id.equals(f.getId())).findFirst().orElse(
            null);
        if (result == null) {
            throw new IllegalArgumentException("Function with id '" + id + "' not found");
        }
        return result;
    }

    WorkAreaXml findWorkAreaById(String id) {
        WorkAreaXml result = workAreas.getWorkAreas().stream().filter(wa -> id.equals(wa.getId())).findFirst().orElse(
            null);
        if (result == null) {
            throw new IllegalArgumentException("WorkArea with id '" + id + "' not found");
        }
        return result;
    }
}
