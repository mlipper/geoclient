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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GeoclientXmlReaderTest {

    private static GeoclientXml geoclient;

    @BeforeAll
    public static void setup() throws Exception {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        InputStream inputStream = GeoclientXmlReaderTest.class.getClassLoader().getResourceAsStream("geoclient.xml");
        assertNotNull(inputStream, "geoclient.xml not found in classpath");

        geoclient = mapper.readValue(inputStream, GeoclientXml.class);
        assertNotNull(geoclient);
    }

    @Test
    public void testFilters() throws Exception {
        // Verify Filters
        FiltersXml filtersXml = geoclient.getFilters();
        assertNotNull(filtersXml);
        assertEquals("allFilters", filtersXml.getId());
        List<FilterXml> filters = filtersXml.getFilters();
        assertNotNull(filters);
        assertFalse(filters.isEmpty());

        // Check a specific filter
        boolean foundAngleFilter = false;
        for (FilterXml filter : filters) {
            if ("angleToFilter".equals(filter.getId())
                    && "angleTo(From|To)Node(Alpha|Beta)Value".equals(filter.getPattern())) {
                foundAngleFilter = true;
                break;
            }
        }
        assertTrue(foundAngleFilter, "angleToFilter not found or incorrect pattern");
    }

    @Test
    public void testWorkAreasXml() throws Exception {
        // Verify WorkAreas
        WorkAreasXml workAreasXml = geoclient.getWorkAreas();
        assertNotNull(workAreasXml);
        List<WorkAreaXml> workAreas = workAreasXml.getWorkAreas();
        assertNotNull(workAreas);
        assertFalse(workAreas.isEmpty());

        // Check WA1
        WorkAreaXml wa1 = null;
        for (WorkAreaXml wa : workAreas) {
            if ("WA1".equals(wa.getId())) {
                wa1 = wa;
                break;
            }
        }
        assertNotNull(wa1, "WA1 work area not found");
        assertTrue(wa1.isIsWA1());
        assertEquals(1200, wa1.getLength());

        // Check outputFilters for WA1
        OutputFiltersXml outputFilters = wa1.getOutputFilters();
        assertNotNull(outputFilters, "outputFilters for WA1 should not be null");
        assertEquals("allFilters", outputFilters.getReference());
    }

    @Test
    public void testFieldsXml_wa1() throws Exception {
        // Verify Fields
        List<WorkAreaXml> workAreasXml = geoclient.getWorkAreas().getWorkAreas();

        List<FieldXml> fieldsXml_wa1 = null;
        for (WorkAreaXml wa : workAreasXml) {
            if ("WA1".equals(wa.getId())) {
                fieldsXml_wa1 = wa.getFields();
                break;
            }
        }
        // Check specific field in WA1
        FieldXml streetName1In = null;
        for (FieldXml f : fieldsXml_wa1) {
            if ("streetName1In".equals(f.getId())) {
                streetName1In = f;
                break;
            }
        }
        assertNotNull(streetName1In, "streetName1In field not found in WA1");
        assertEquals(68, streetName1In.getStart());
        assertEquals(32, streetName1In.getLength());
        assertTrue(streetName1In.isInput());
        assertEquals("streetName", streetName1In.getAlias());
    }

    @Test
    public void testFieldsXml_wa2f1() throws Exception {
        // Verify Fields
        List<WorkAreaXml> workAreasXml = geoclient.getWorkAreas().getWorkAreas();

        List<FieldXml> fieldsXml_wa2_f1 = null;
        for (WorkAreaXml wa : workAreasXml) {
            if ("WA2_F1".equals(wa.getId())) {
                fieldsXml_wa2_f1 = wa.getFields();
                break;
            }
        }

        FieldXml dynamicBlock = null;
        for (FieldXml f : fieldsXml_wa2_f1) {
            if ("dynamicBlock".equals(f.getId())) {
                dynamicBlock = f;
                break;
            }
        }
        assertNotNull(dynamicBlock, "dynamicBlock field not found in WA2_F1");
        assertEquals("atomicPolygon", dynamicBlock.getOutputAlias());

        FieldXml censusTract1990 = null;
        for (FieldXml f : fieldsXml_wa2_f1) {
            if ("censusTract1990".equals(f.getId())) {
                censusTract1990 = f;
                break;
            }
        }
        assertNotNull(censusTract1990, "censusTract1990 field not found in WA2_F1");
        assertTrue(censusTract1990.isWhitespace());
    }

    @Test
    public void testFunctionsXml() throws Exception {

        // Verify Functions
        FunctionsXml functionsXml = geoclient.getFunctions();
        assertNotNull(functionsXml);
        List<FunctionXml> functions = functionsXml.getFunctions();
        assertNotNull(functions);
        assertFalse(functions.isEmpty());

        // Check Function "1B"
        FunctionXml function1B = null;
        for (FunctionXml func : functions) {
            if ("1B".equals(func.getId())) {
                function1B = func;
                break;
            }
        }
        assertNotNull(function1B, "Function 1B not found");
        assertNotNull(function1B.getWorkAreaOne());
        assertEquals("WA1", function1B.getWorkAreaOne().getReference());
        assertNotNull(function1B.getWorkAreaTwo());
        assertEquals("WA2_F1B", function1B.getWorkAreaTwo().getReference());

        // Check Configuration for 1B
        ConfigurationXml config = function1B.getConfiguration();
        assertNotNull(config);
        List<RequiredArgumentXml> args = config.getRequiredArguments();
        assertNotNull(args);

        boolean foundFunctionCode = false;
        for (RequiredArgumentXml arg : args) {
            if ("geosupportFunctionCode".equals(arg.getName()) && "1B".equals(arg.getValue())) {
                foundFunctionCode = true;
                break;
            }
        }
        assertTrue(foundFunctionCode, "geosupportFunctionCode argument not found or incorrect for function 1B");
    }
}
