package gov.nyc.doitt.gis.geoclient.json;


import static gov.nyc.doitt.gis.geoclient.json.fixtures.FilterFixtures.createFilterList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.config.WorkAreaConfig;
import gov.nyc.doitt.gis.geoclient.function.Filter;

class JsonConfigurationTest {

    private static final Logger log = LoggerFactory.getLogger(JsonConfigurationTest.class);

    @Test
    void test_loadFilters() throws IOException {
        JsonConfiguration config = new JsonConfiguration();
        List<FilterList> listOfFilterLists = config.loadFilters();
        assertEquals(1, listOfFilterLists.size());
        FilterList filterList = listOfFilterLists.get(0);
        log.info("filters: {}", filterList);
        assertNotNull(filterList);
        assertNotNull(filterList.getId());
        assertNotNull(filterList.getFilters());
        assumeFalse(filterList.getFilters().isEmpty());
        for (Filter filter : filterList.getFilters()) {
            log.info("filter: {}", filter);
            assertTrue(filter.toString().startsWith("Filter [pattern="));
            assertTrue(filter.toString().endsWith("]"));
        }
    }

    @Test
    void test_loadWorkAreas() throws IOException {
        JsonConfiguration config = new JsonConfiguration();
        FilterList filterList = createFilterList(2);
        List<FilterList> listOfFilterLists = new ArrayList<>();
        listOfFilterLists.add(filterList);
        List<WorkAreaConfig> waConfigList = config.loadWorkAreas(listOfFilterLists);
        log.info("work areas: {}", waConfigList);
        assertNotNull(waConfigList);
        for (WorkAreaConfig waConfig : waConfigList) {
            assertNotNull(waConfig.getId());
        }
    }
}
