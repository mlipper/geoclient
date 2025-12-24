package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OutputFiltersXml {

    @JacksonXmlProperty(isAttribute = true)
    private String reference;

    @JacksonXmlProperty(localName = "filter")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FilterXml> filters;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<FilterXml> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterXml> filters) {
        this.filters = filters;
    }
}
