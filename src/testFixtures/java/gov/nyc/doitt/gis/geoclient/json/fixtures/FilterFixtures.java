package gov.nyc.doitt.gis.geoclient.json.fixtures;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.json.FilterList;

public class FilterFixtures {

    public static final String FLIST_DEFAULT_ID = "allFilters";
    public static final String FNODE_ID = "id";
    public static final String FNODE_PATTERN = "match";

    public static List<Filter> createFilters(int count) {
        List<Filter> filters = new java.util.ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int j = i + 1;
            filters.add(new Filter(FNODE_PATTERN + j));
        }
        return filters;
    }

    public static FilterList createFilterList(int count) {
        FilterList filterList = new FilterList();
        filterList.setId(FLIST_DEFAULT_ID);
        filterList.setFilters(createFilters(count));
        return filterList;
    }
}
