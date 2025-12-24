package gov.nyc.doitt.gis.geoclient.config.jackson;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FunctionsXml {

    @JacksonXmlProperty(localName = "function")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<FunctionXml> functions;

    public List<FunctionXml> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionXml> functions) {
        this.functions = functions;
    }
}
