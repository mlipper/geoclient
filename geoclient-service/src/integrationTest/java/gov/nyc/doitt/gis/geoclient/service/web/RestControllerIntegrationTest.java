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
package gov.nyc.doitt.gis.geoclient.service.web;

import static gov.nyc.doitt.gis.geoclient.api.InputParam.STREET_CODE;
import static gov.nyc.doitt.gis.geoclient.api.OutputParam.GEOSUPPORT_RETURN_CODE;
import static gov.nyc.doitt.gis.geoclient.api.ReturnCodeValue.SUCCESS;
import static gov.nyc.doitt.gis.geoclient.function.Function.F1B;
import static gov.nyc.doitt.gis.geoclient.function.Function.FDG;
import static gov.nyc.doitt.gis.geoclient.service.web.RestController.ADDRESS_URI;
import static gov.nyc.doitt.gis.geoclient.service.web.RestController.STREETCODE_URI;
import static gov.nyc.doitt.gis.geoclient.service.web.RestController.VERSION_URI;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.util.UriComponentsBuilder;

import gov.nyc.doitt.gis.geoclient.service.domain.Version;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControllerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerIntegrationTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @SuppressWarnings("unchecked")
    @Test
    public void testAddress_jsonFileExtension() {
        URI uri = UriComponentsBuilder.fromPath(ADDRESS_URI + ".json").queryParam("houseNumber", "100").queryParam(
            "street", "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = this.restTemplate.getForEntity(uri, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(MediaType.APPLICATION_JSON).isEqualTo(response.getHeaders().getContentType());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> result = (Map<String, Object>) body.get("address");
        assertThat(result.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(result.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(result.containsKey("geosupportFunctionCode"));
        assertThat(result.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddress_xmlFileExtension() {
        URI uri = UriComponentsBuilder.fromPath(ADDRESS_URI + ".xml").queryParam("houseNumber", "100").queryParam(
            "street", "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = this.restTemplate.getForEntity(uri, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(MediaType.APPLICATION_XML).isEqualTo(response.getHeaders().getContentType());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> result = (Map<String, Object>) body.get("address");
        assertThat(result.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(result.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(result.containsKey("geosupportFunctionCode"));
        assertThat(result.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddress_noFileExtension() {
        URI uri = UriComponentsBuilder.fromPath(ADDRESS_URI).queryParam("houseNumber", "100").queryParam("street",
            "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> response = this.restTemplate.getForEntity(uri, Map.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        // Assert that the application/json is the default ContentType
        assertThat(MediaType.APPLICATION_JSON).isEqualTo(response.getHeaders().getContentType());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> result = (Map<String, Object>) body.get("address");
        assertThat(result.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(result.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(result.containsKey("geosupportFunctionCode"));
        assertThat(result.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testStreetcode_FDG() {
        String streetCodeUriTemplate = String.format("%s.%s?%s={%s}", STREETCODE_URI, "json", STREET_CODE, STREET_CODE);
        LOGGER.info("URI=" + streetCodeUriTemplate);
        Map<String, Object> body = (Map<String, Object>) this.restTemplate.getForObject(streetCodeUriTemplate,
            Map.class, "110610");
        LOGGER.info("{} response body: {}", STREETCODE_URI, body);
        Map<String, Object> result = (Map<String, Object>) body.get(STREET_CODE.toLowerCase());
        assertThat(result.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(result.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(result.containsKey("geosupportFunctionCode"));
        assertThat(result.get("geosupportFunctionCode").equals(FDG));
    }

    @Test
    public void testVersion() throws IOException {
        URI uri = jsonResource(VERSION_URI);
        ResponseEntity<Version> httpResponse = restTemplate.exchange(uri, HttpMethod.GET, jsonRequest(), Version.class);
        assertThat(httpResponse.getStatusCode() == HttpStatusCode.valueOf(200));
        Version actual = httpResponse.getBody();
        LOGGER.info("{} -> {}", uri.toASCIIString(), actual);
        assertThat(actual.getAccessMethod()).isNotEmpty();
        assertThat(actual.getGeoclientJniVersion()).isNotEmpty();
        assertThat(actual.getGeoclientParserVersion()).isNotEmpty();
        assertThat(actual.getGeoclientServiceVersion()).isNotEmpty();
    }

    protected HttpEntity<?> jsonRequest() {
        return new HttpEntity<>(jsonHeaders());
    }

    protected URI jsonResource(String pathWithoutMediaType) {
        return uriComponentsBuilder(pathWithoutMediaType + ".json").build().toUri();
    }

    protected HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    protected UriComponentsBuilder uriComponentsBuilder(@NonNull String path) {
        return UriComponentsBuilder.fromPath(path);
    }
}
