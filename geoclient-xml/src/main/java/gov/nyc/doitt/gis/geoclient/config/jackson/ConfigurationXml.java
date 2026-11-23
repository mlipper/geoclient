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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Jackson binding for the {@code <configuration>} element of a function, holding its required arguments.
 */
public class ConfigurationXml {

    @JacksonXmlProperty(localName = "requiredArgument")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<RequiredArgumentXml> requiredArguments;

    /**
     * Returns the required arguments.
     *
     * @return the required arguments
     */
    public List<RequiredArgumentXml> getRequiredArguments() {
        return requiredArguments;
    }

    /**
     * Sets the required arguments.
     *
     * @param requiredArguments the required arguments
     */
    public void setRequiredArguments(List<RequiredArgumentXml> requiredArguments) {
        this.requiredArguments = requiredArguments;
    }

    /**
     * Converts the required arguments to a map.
     *
     * @return the argument values keyed by argument name
     */
    public Map<String, Object> toMap() {
        return requiredArguments.stream().collect(
            Collectors.toMap(RequiredArgumentXml::getName, RequiredArgumentXml::getValue));
    }
}
