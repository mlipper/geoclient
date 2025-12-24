package gov.nyc.doitt.gis.geoclient.config.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonXmlTest {

    @Test
    public void testDeserializeGeoclientXml() throws Exception {
        XmlMapper mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("geoclient.xml");
        assertNotNull(inputStream, "geoclient.xml not found in classpath");

        GeoclientXml geoclient = mapper.readValue(inputStream, GeoclientXml.class);
        assertNotNull(geoclient);

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

        // Check Fields in WA1
        List<FieldXml> fields = wa1.getFields();
        assertNotNull(fields);
        assertFalse(fields.isEmpty());

        // Check specific field in WA1
        FieldXml streetName1In = null;
        for (FieldXml f : fields) {
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

        // Verify outputAlias in WA2_F1
        WorkAreaXml wa2f1 = null;
        for (WorkAreaXml wa : workAreas) {
            if ("WA2_F1".equals(wa.getId())) {
                wa2f1 = wa;
                break;
            }
        }
        assertNotNull(wa2f1, "WA2_F1 work area not found");
        List<FieldXml> wa2f1Fields = wa2f1.getFields();
        assertNotNull(wa2f1Fields);

        FieldXml dynamicBlock = null;
        for (FieldXml f : wa2f1Fields) {
            if ("dynamicBlock".equals(f.getId())) {
                dynamicBlock = f;
                break;
            }
        }
        assertNotNull(dynamicBlock, "dynamicBlock field not found in WA2_F1");
        assertEquals("atomicPolygon", dynamicBlock.getOutputAlias());

        FieldXml censusTract1990 = null;
        for (FieldXml f : wa2f1Fields) {
            if ("censusTract1990".equals(f.getId())) {
                censusTract1990 = f;
                break;
            }
        }
        assertNotNull(censusTract1990, "censusTract1990 field not found in WA2_F1");
        assertTrue(censusTract1990.isWhitespace());

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
