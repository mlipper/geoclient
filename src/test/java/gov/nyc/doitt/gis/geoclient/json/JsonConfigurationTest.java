package gov.nyc.doitt.gis.geoclient.json;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JsonConfigurationTest {

    private static final Logger log = LoggerFactory.getLogger(JsonConfigurationTest.class);

    @Test
    void test_loadFilters() throws IOException {
        JsonConfiguration config = new JsonConfiguration();
        FilterList filterList = config.loadFilters();
        log.info("filters: {}", filterList);
        assertNotNull(filterList);
        assertNotNull(filterList.getId());
        assertNotNull(filterList.getFilters());
        assumeFalse(filterList.getFilters().isEmpty());
        for (FilterNode filterNode : filterList.getFilters()) {
            log.info("filter: {}", filterNode);
            assertNotNull(filterNode.getId());
            assertNotNull(filterNode.getPattern());
        }
    }
}
