package gov.nyc.doitt.gis.geoclient.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterListTest {

    @Test
    void testFilterList_add() {
        FilterList flist = new FilterList();
        Filter filter1 = new Filter("foo");
        flist.add(filter1);
        Filter filter2 = new Filter("bar");
        flist.add(filter2);
        List<Filter> filters = flist.getFilters();
        assertEquals("Filter [pattern=foo]", filters.get(0).toString());
        assertEquals("Filter [pattern=bar]", filters.get(1).toString());
    }

    @Test
    void testFilterList_constructor() {
        assertEquals(0, new FilterList().getFilters().size());
    }
}
