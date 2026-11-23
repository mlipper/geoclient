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
 * Jackson binding for a {@code <function>} element.
 */
public class FunctionXml {

    /**
     * Creates a new instance.
     */
    public FunctionXml() {
    }

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "workAreaOne")
    private WorkAreaReferenceXml workAreaOne;

    @JacksonXmlProperty(localName = "workAreaTwo")
    private WorkAreaReferenceXml workAreaTwo;

    @JacksonXmlProperty(localName = "configuration")
    private ConfigurationXml configuration;

    /**
     * Indicates whether the function uses two work areas.
     *
     * @return {@code true} if both work area references are present
     */
    public boolean isTwoWorkAreas() {
        return workAreaOne != null && workAreaTwo != null;
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
     * Returns the first work area.
     *
     * @return the first work area
     */
    public WorkAreaReferenceXml getWorkAreaOne() {
        return workAreaOne;
    }

    /**
     * Sets the first work area.
     *
     * @param workAreaOne the first work area
     */
    public void setWorkAreaOne(WorkAreaReferenceXml workAreaOne) {
        this.workAreaOne = workAreaOne;
    }

    /**
     * Returns the second work area.
     *
     * @return the second work area
     */
    public WorkAreaReferenceXml getWorkAreaTwo() {
        return workAreaTwo;
    }

    /**
     * Sets the second work area.
     *
     * @param workAreaTwo the second work area
     */
    public void setWorkAreaTwo(WorkAreaReferenceXml workAreaTwo) {
        this.workAreaTwo = workAreaTwo;
    }

    /**
     * Returns the configuration.
     *
     * @return the configuration
     */
    public ConfigurationXml getConfiguration() {
        return configuration;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration the configuration
     */
    public void setConfiguration(ConfigurationXml configuration) {
        this.configuration = configuration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FunctionXml other = (FunctionXml) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FunctionXml [id=" + id + "]";
    }

}
