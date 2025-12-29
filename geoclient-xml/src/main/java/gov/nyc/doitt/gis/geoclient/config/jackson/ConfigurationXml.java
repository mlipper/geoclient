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
