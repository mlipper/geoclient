package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class WorkAreasXml {

    @JacksonXmlProperty(localName = "workArea")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<WorkAreaXml> workAreas;

    public List<WorkAreaXml> getWorkAreas() {
        return workAreas;
    }

    public void setWorkAreas(List<WorkAreaXml> workAreas) {
        this.workAreas = workAreas;
    }
}
