package gov.nyc.doitt.gis.geoclient.json;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterList extends IdentifiableNode {

    private List<Filter> filters;

    public FilterList() {
        super();
        this.filters = new ArrayList<>();
    }

    @JsonIgnore
    public void add(Filter filter) {
        this.filters.add(filter);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "filters";
    }

    @JsonProperty("filter")
    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "FilterList [id=" + getId() + ", filters=" + filters + "]";
    }

}
