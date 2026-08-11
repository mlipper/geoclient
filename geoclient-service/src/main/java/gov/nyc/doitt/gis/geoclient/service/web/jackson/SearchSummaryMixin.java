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

import java.util.Map;

import org.springframework.boot.jackson.JacksonMixin;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import gov.nyc.doitt.gis.geoclient.search.response.MatchStatus;
import gov.nyc.doitt.gis.geoclient.search.response.SearchSummary;

/**
 * Jackson mix-in that supplies wire-format metadata for {@link SearchSummary}.
 */
@JacksonMixin(SearchSummary.class)
@JsonPropertyOrder({"level", "status", "request", "response"})
abstract class SearchSummaryMixin {

    @JacksonXmlProperty(isAttribute = true)
    private String level;

    @JacksonXmlProperty(isAttribute = true)
    private MatchStatus status;

    @JacksonXmlProperty(isAttribute = true)
    private String request;

    @JacksonXmlProperty(localName = "geosupportResponse")
    private Map<String, Object> response;
}
