package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ConfigurationXml {

    @JacksonXmlProperty(localName = "requiredArgument")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RequiredArgumentXml> requiredArguments;

    public List<RequiredArgumentXml> getRequiredArguments() {
        return requiredArguments;
    }

    public void setRequiredArguments(List<RequiredArgumentXml> requiredArguments) {
        this.requiredArguments = requiredArguments;
    }
}
