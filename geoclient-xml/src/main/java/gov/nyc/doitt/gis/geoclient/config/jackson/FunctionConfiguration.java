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

public class FunctionConfiguration {

    private final String id;
    private final Map<String, Object> configuration;
    private final WorkAreaXml workAreaOne;
    private final WorkAreaXml workAreaTwo;

    public FunctionConfiguration(String id, ConfigurationXml configuration, WorkAreaXml workAreaOne,
            WorkAreaXml workAreaTwo) {
        this.id = id;
        this.configuration = configuration.toMap();
        this.workAreaOne = workAreaOne;
        this.workAreaTwo = workAreaTwo;
    }

    public FunctionConfiguration(String id, ConfigurationXml configuration, WorkAreaXml workAreaOne) {
        this(id, configuration, workAreaOne, null);
    }

    public boolean isTwoWorkAreas() {
        return workAreaTwo != null;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public WorkAreaXml getWorkAreaOne() {
        return workAreaOne;
    }

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
