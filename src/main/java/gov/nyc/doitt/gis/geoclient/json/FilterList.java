package gov.nyc.doitt.gis.geoclient.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterList extends IdentifiableNode {

    private List<FilterNode> filters;

    @JsonIgnore
    @Override
    public String getName() {
        return "filters";
    }

    @JsonProperty("filter")
    public List<FilterNode> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterNode> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "FilterList [id=" + getId() + ", filters=" + filters +  "]";
    }

}
