/*
 * Copyright 2013-2026 the original author or authors.
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

import java.util.Map;

/**
 * Resolved configuration of a Geosupport function: its required arguments and the
 * {@link WorkAreaXml}s it uses.
 */
public class FunctionConfiguration {

    private final String id;
    private final Map<String, Object> configuration;
    private final WorkAreaXml workAreaOne;
    private final WorkAreaXml workAreaTwo;

    /**
     * Creates a configuration for a function using two work areas.
     *
     * @param id the function identifier
     * @param configuration the function's configuration, converted to a map of required arguments
     * @param workAreaOne the first work area
     * @param workAreaTwo the second work area, or {@code null}
     */
    public FunctionConfiguration(String id, ConfigurationXml configuration, WorkAreaXml workAreaOne,
            WorkAreaXml workAreaTwo) {
        this.id = id;
        this.configuration = configuration.toMap();
        this.workAreaOne = workAreaOne;
        this.workAreaTwo = workAreaTwo;
    }

    /**
     * Creates a configuration for a function using a single work area.
     *
     * @param id the function identifier
     * @param configuration the function's configuration, converted to a map of required arguments
     * @param workAreaOne the work area
     */
    public FunctionConfiguration(String id, ConfigurationXml configuration, WorkAreaXml workAreaOne) {
        this(id, configuration, workAreaOne, null);
    }

    /**
     * Indicates whether the function uses two work areas.
     *
     * @return {@code true} if a second work area is configured
     */
    public boolean isTwoWorkAreas() {
        return workAreaTwo != null;
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
     * Returns the function's required arguments.
     *
     * @return the required arguments keyed by argument name
     */
    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    /**
     * Returns the first work area.
     *
     * @return the first work area
     */
    public WorkAreaXml getWorkAreaOne() {
        return workAreaOne;
    }

    /**
     * Returns the second work area.
     *
     * @return the second work area
     */
    public WorkAreaXml getWorkAreaTwo() {
        return workAreaTwo;
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
        FunctionConfiguration other = (FunctionConfiguration) obj;
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
        return "FunctionConfiguration [id=" + id + "]";
    }

}
