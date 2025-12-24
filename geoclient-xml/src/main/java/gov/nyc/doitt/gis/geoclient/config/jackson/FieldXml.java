package gov.nyc.doitt.gis.geoclient.config.jackson;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FieldXml {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(isAttribute = true)
    private int start;

    @JacksonXmlProperty(isAttribute = true)
    private int length;

    @JacksonXmlProperty(isAttribute = true)
    private String type; // "COMP" etc.

    @JacksonXmlProperty(isAttribute = true)
    private boolean input;

    @JacksonXmlProperty(isAttribute = true)
    private String alias;

    @JacksonXmlProperty(isAttribute = true)
    private String outputAlias;

    @JacksonXmlProperty(isAttribute = true)
    private boolean whitespace;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInput() {
        return input;
    }

    public void setInput(boolean input) {
        this.input = input;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getOutputAlias() {
        return outputAlias;
    }

    public void setOutputAlias(String outputAlias) {
        this.outputAlias = outputAlias;
    }

    public boolean isWhitespace() {
        return whitespace;
    }

    public void setWhitespace(boolean whitespace) {
        this.whitespace = whitespace;
    }
}
