package gov.nyc.doitt.gis.geoclient.config.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FunctionXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "workAreaOne")
    private WorkAreaReferenceXml workAreaOne;

    @JacksonXmlProperty(localName = "workAreaTwo")
    private WorkAreaReferenceXml workAreaTwo;

    @JacksonXmlProperty(localName = "configuration")
    private ConfigurationXml configuration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkAreaReferenceXml getWorkAreaOne() {
        return workAreaOne;
    }

    public void setWorkAreaOne(WorkAreaReferenceXml workAreaOne) {
        this.workAreaOne = workAreaOne;
    }

    public WorkAreaReferenceXml getWorkAreaTwo() {
        return workAreaTwo;
    }

    public void setWorkAreaTwo(WorkAreaReferenceXml workAreaTwo) {
        this.workAreaTwo = workAreaTwo;
    }

    public ConfigurationXml getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationXml configuration) {
        this.configuration = configuration;
    }
}
