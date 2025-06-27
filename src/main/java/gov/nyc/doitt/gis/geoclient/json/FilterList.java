package gov.nyc.doitt.gis.geoclient.json;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterList extends IdentifiableNode {

    private List<FilterNode> filterNodes;

    @JsonIgnore
    public List<Filter> asFilters() {
        return this.filterNodes.stream().map(n -> n.asFilter()).collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "filters";
    }

    @JsonProperty("filter")
    public List<FilterNode> getFilterNodes() {
        return filterNodes;
    }

    public void setFilterNodes(List<FilterNode> filterNodes) {
        this.filterNodes = filterNodes;
    }

    @Override
    public String toString() {
        return "FilterList [id=" + getId() + ", filters=" + filterNodes +  "]";
    }

}
