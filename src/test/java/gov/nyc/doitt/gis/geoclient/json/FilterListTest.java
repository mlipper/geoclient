package gov.nyc.doitt.gis.geoclient.json;

import static gov.nyc.doitt.gis.geoclient.json.fixtures.FilterFixtures.FNODE_PATTERN;
import static gov.nyc.doitt.gis.geoclient.json.fixtures.FilterFixtures.createFilterList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterListTest {

    @Test
    void testAsFilters() {
        FilterList list = createFilterList(2);
        List<Filter> filters = list.asFilters();
        assertEquals(2, filters.size());
        Filter filter1 = filters.get(0);
        assertEquals("Filter [pattern=" + FNODE_PATTERN + "1]", filter1.toString());
        Filter filter2 = filters.get(1);
        assertEquals("Filter [pattern=" + FNODE_PATTERN + "2]", filter2.toString());
    }

    @Test
    void testAsFilters_noFilters() {
        FilterList list = createFilterList(0);
        List<Filter> filters = list.asFilters();
        assertEquals(0, filters.size());
    }

    FilterNode createFilterNode(String id, String pattern) {
        return new FilterNode(id, pattern);
    }
}
