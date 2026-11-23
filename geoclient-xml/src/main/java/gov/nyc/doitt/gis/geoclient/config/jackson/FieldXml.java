/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.config.jackson;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Jackson binding for a {@code <field>} element describing a field in a Geosupport work area.
 */
public class FieldXml {

    /**
     * Creates a new instance.
     */
    public FieldXml() {
    }

    /**
     * Field type of composite fields, which are made up of other fields.
     */
    public static final String TYPE_COMPOSITE = "COMP";

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

    /**
     * Indicates whether this field is a composite field.
     *
     * @return {@code true} if the type is {@value #TYPE_COMPOSITE}
     */
    public boolean isComposite() {
        return TYPE_COMPOSITE.equals(type);
    }

    /**
     * Returns the identifier.
     *
     * @return the identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the identifier.
     *
     * @param id the identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the one-based start position of the field within the work area.
     *
     * @return the one-based start position of the field within the work area
     */
    public int getStart() {
        return start;
    }

    /**
     * Sets the one-based start position of the field within the work area.
     *
     * @param start the one-based start position of the field within the work area
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Returns the length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Sets the length.
     *
     * @param length the length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the field type.
     *
     * @return the field type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the field type.
     *
     * @param type the field type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Indicates whether the field is an input field.
     *
     * @return {@code true} if the field is an input field
     */
    public boolean isInput() {
        return input;
    }

    /**
     * Sets whether the field is an input field.
     *
     * @param input whether the field is an input field
     */
    public void setInput(boolean input) {
        this.input = input;
    }

    /**
     * Returns the field alias.
     *
     * @return the field alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the field alias.
     *
     * @param alias the field alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Returns the field output alias.
     *
     * @return the field output alias
     */
    public String getOutputAlias() {
        return outputAlias;
    }

    /**
     * Sets the field output alias.
     *
     * @param outputAlias the field output alias
     */
    public void setOutputAlias(String outputAlias) {
        this.outputAlias = outputAlias;
    }

    /**
     * Indicates whether the field value preserves whitespace.
     *
     * @return {@code true} if the field value preserves whitespace
     */
    public boolean isWhitespace() {
        return whitespace;
    }

    /**
     * Sets whether the field value preserves whitespace.
     *
     * @param whitespace whether the field value preserves whitespace
     */
    public void setWhitespace(boolean whitespace) {
        this.whitespace = whitespace;
    }
}
