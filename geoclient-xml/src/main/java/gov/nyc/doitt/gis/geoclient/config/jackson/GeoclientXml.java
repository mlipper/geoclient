package gov.nyc.doitt.gis.geoclient.config.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "geoclient")
public class GeoclientXml {

    @JacksonXmlProperty(localName = "filters")
    private FiltersXml filters;

    @JacksonXmlProperty(localName = "workAreas")
    private WorkAreasXml workAreas;

    @JacksonXmlProperty(localName = "functions")
    private FunctionsXml functions;

    public FiltersXml getFilters() {
        return filters;
    }

    public void setFilters(FiltersXml filters) {
        this.filters = filters;
    }

    public WorkAreasXml getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(WorkAreasXml workAreas) {
        this.workAreas = workAreas;
    }

    public FunctionsXml getFunctions() {
        return functions;
    }

    public void setFunctions(FunctionsXml functions) {
        this.functions = functions;
    }
}
