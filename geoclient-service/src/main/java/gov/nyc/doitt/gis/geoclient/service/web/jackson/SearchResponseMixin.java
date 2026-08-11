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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;
import gov.nyc.doitt.gis.geoclient.search.response.PolicySummary;
import gov.nyc.doitt.gis.geoclient.search.response.SearchResponse;
import gov.nyc.doitt.gis.geoclient.search.response.SearchSummary;

/**
 * Jackson mix-in that supplies wire-format metadata for {@link SearchResponse}.
 */
@JacksonMixin(SearchResponse.class)
@JsonPropertyOrder(value = {"id", "status", "input", "results", "parseTree", "policy"})
@JsonRootName(namespace = "", value = "searchResponse")
abstract class SearchResponseMixin {

    @JacksonXmlProperty(isAttribute = true)
    private String id;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "result")
    private List<SearchSummary> results;

    @JacksonXmlElementWrapper(localName = "parseTree")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "chunk")
    private List<Chunk> parseTree;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JacksonXmlProperty(localName = "policy")
    private List<PolicySummary> policy;
}
