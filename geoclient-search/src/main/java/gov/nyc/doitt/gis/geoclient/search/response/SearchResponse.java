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
package gov.nyc.doitt.gis.geoclient.search.response;

import java.util.List;

import gov.nyc.doitt.gis.geoclient.parser.token.Chunk;

public class SearchResponse {
    private String id;

    private Status status;

    private String input;

    private List<SearchSummary> results;

    private List<Chunk> parseTree;

    private List<PolicySummary> policy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<Chunk> getParseTree() {
        return parseTree;
    }

    public void setParseTree(List<Chunk> parseTree) {
        this.parseTree = parseTree;
    }

    public List<PolicySummary> getPolicy() {
        return policy;
    }

    public void setPolicy(List<PolicySummary> policy) {
        this.policy = policy;
    }

    public List<SearchSummary> getResults() {
        return results;
    }

    public void setResults(List<SearchSummary> results) {
        this.results = results;
    }

}
