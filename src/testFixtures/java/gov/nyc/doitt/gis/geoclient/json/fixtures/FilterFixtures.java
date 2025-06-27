package gov.nyc.doitt.gis.geoclient.json.fixtures;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.json.FilterList;
import gov.nyc.doitt.gis.geoclient.json.FilterNode;

public class FilterFixtures {

    public static final String FLIST_DEFAULT_ID = "allFilters";
    public static final String FNODE_ID = "id";
    public static final String FNODE_PATTERN = "match";

    public static List<FilterNode> createFilterNodes(int count) {
        List<FilterNode> nodes = new java.util.ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int j = i + 1;
            nodes.add(new FilterNode(FNODE_ID + j, FNODE_PATTERN + j));
        }
        return nodes;
    }

    public static FilterList createFilterList(int count) {
        FilterList filterList = new FilterList();
        filterList.setId(FLIST_DEFAULT_ID);
        filterList.setFilterNodes(createFilterNodes(count));
        return filterList;
    }
}
