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
package gov.nyc.doitt.gis.geoclient.service.web.jackson;

import java.util.List;

import org.springframework.boot.jackson.JacksonMixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import gov.nyc.doitt.gis.geoclient.api.version.FileInfo;
import gov.nyc.doitt.gis.geoclient.api.version.GeosupportVersion;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Jackson mix-in that supplies wire-format metadata for
 * {@link GeosupportVersion}.
 */
@JacksonMixin(GeosupportVersion.class)
@JsonPropertyOrder({ "dsNames", "geoFileInfo", "thinFileInfo", "apFileInfo", "apequivFileInfo", "auxsegFileInfo",
        "grid1RFileInfo", "sneqFileInfo", "stat1AFileInfo", "stat1FileInfo", "stat2FileInfo", "stat3FileInfo",
        "stat3SFileInfo", "statAPFileInfo", "statBLFileInfo", "statBNFileInfo", "statDFileInfo", "statFileInfo",
        "tpadFileInfo", "upadFileInfo", "geofilesDirectory" })
abstract class GeosupportVersionMixin {

    @JacksonXmlElementWrapper(localName = "dsNames")
    @JacksonXmlProperty(localName = "string")
    private List<String> dsNames;

    @JacksonXmlElementWrapper(localName = "geoFileInfo")
    @JacksonXmlProperty(localName = "fileInfo")
    private List<FileInfo> geoFileInfo;
}
