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
package gov.nyc.doitt.gis.geoclient.service.search.web;

import static gov.nyc.doitt.gis.geoclient.service.search.web.SingleFieldSearchController.SEARCH_URI;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.client.EntityExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import gov.nyc.doitt.gis.geoclient.service.search.web.response.MatchStatus;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.SearchResponse;
import gov.nyc.doitt.gis.geoclient.service.search.web.response.Status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class SingleFieldSearchControllerIntegrationTest {

    private final Logger logger = LoggerFactory.getLogger(SingleFieldSearchController.class);

    // TODO replace with environment variable
    private static final String BASE_URI = "/geoclient/v2";

    @Test
    public void testSearch(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + SEARCH_URI).queryParam("input", "120 broadway").build().toUri();
        logger.info("URI={}", uri);
        EntityExchangeResult<SearchResponse> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(SearchResponse.class).returnResult();
        SearchResponse searchResponse = result.getResponseBody();
        logger.debug("Response: {}", searchResponse);
        Status status = searchResponse.getStatus();
        assertThat(status.equals(Status.OK));
        assertThat(
            searchResponse.getResults().stream().anyMatch(s -> s.getStatus().equals(MatchStatus.POSSIBLE_MATCH)));
    }
}
