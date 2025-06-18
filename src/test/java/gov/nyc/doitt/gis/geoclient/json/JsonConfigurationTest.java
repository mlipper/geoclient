package gov.nyc.doitt.gis.geoclient.json;


import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.IOException;
import java.util.List;
//import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Filter;

class JsonConfigurationTest {

    private static final Logger log = LoggerFactory.getLogger(JsonConfigurationTest.class);

    @Test
    void test_loadFilters() throws IOException {
        JsonConfiguration config = new JsonConfiguration();
        List<Filter> filters = config.loadFilters();
        log.info("filters: {}", filters);
        assertNotNull(filters);
        assumeFalse(filters.isEmpty());
        for (Filter filter : filters) {
            assertNotEquals("Filter [pattern=missing]", filter.toString());
        }
    }
}
