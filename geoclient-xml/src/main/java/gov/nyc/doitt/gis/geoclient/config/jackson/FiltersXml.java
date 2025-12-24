package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FiltersXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "filter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FilterXml> filters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FilterXml> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterXml> filters) {
        this.filters = filters;
    }
}
