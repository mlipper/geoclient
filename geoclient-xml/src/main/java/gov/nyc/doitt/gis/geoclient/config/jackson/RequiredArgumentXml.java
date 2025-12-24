package gov.nyc.doitt.gis.geoclient.config.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RequiredArgumentXml {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
