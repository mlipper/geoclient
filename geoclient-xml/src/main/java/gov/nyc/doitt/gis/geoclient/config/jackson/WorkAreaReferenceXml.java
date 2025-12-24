package gov.nyc.doitt.gis.geoclient.config.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class WorkAreaReferenceXml {

    @JacksonXmlProperty(isAttribute = true)
    private String reference;

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
