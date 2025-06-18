package gov.nyc.doitt.gis.geoclient.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterNode extends IdentifiableNode {

    private String pattern;

    public FilterNode() {
        super();
    }

    public FilterNode(String id) {
        super(id);
    }

    public FilterNode(String id, String pattern) {
        super(id);
        this.pattern = pattern;
    }

    @JsonIgnore
    public Filter asFilter() {
        return new Filter(pattern);
    }

    @JsonIgnore
    @Override
    public String getName() {
        return "filter";
    }

    @JsonProperty("pattern")
    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((pattern == null) ? 0 : pattern.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        FilterNode other = (FilterNode) obj;
        if (pattern == null) {
            if (other.pattern != null)
                return false;
        } else if (!pattern.equals(other.pattern))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FilterNode [id=" + getId() + ", pattern=" + pattern + "]";
    }

}
