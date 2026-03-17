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

import java.net.URI;
import java.util.Map;

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

import gov.nyc.doitt.gis.geoclient.service.domain.Version;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class RestControllerIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerIntegrationTest.class);

    // TODO replace with environment variable
    private static final String BASE_URI = "/geoclient/v2";

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testAddress_jsonFileExtension(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + ADDRESS_URI + ".json").queryParam("houseNumber", "100").queryParam(
            "street", "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        assertThat(address.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(address.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(address.containsKey("geosupportFunctionCode"));
        assertThat(address.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    public void testAddress_xmlFileExtension(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + ADDRESS_URI + ".xml").queryParam("houseNumber", "100").queryParam(
            "street", "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_XML)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        assertThat(address.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(address.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(address.containsKey("geosupportFunctionCode"));
        assertThat(address.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testAddress_noFileExtensionNoParam(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + ADDRESS_URI).queryParam("houseNumber", "100").queryParam("street",
            "Centre St").queryParam("borough", "Manhattan").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        assertThat(address.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(address.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(address.containsKey("geosupportFunctionCode"));
        assertThat(address.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testAddress_noFileExtensionJsonParam(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + ADDRESS_URI).queryParam("houseNumber", "100").queryParam("street",
            "Centre St").queryParam("borough", "Manhattan").queryParam("f", "json").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        assertThat(address.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(address.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(address.containsKey("geosupportFunctionCode"));
        assertThat(address.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testAddress_noFileExtensionXmlParam(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + ADDRESS_URI).queryParam("houseNumber", "100").queryParam("street",
            "Centre St").queryParam("borough", "Manhattan").queryParam("f", "xml").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_XML)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", ADDRESS_URI, body);
        Map<String, Object> address = (Map<String, Object>) body.get("address");
        assertThat(address.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(address.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(address.containsKey("geosupportFunctionCode"));
        assertThat(address.get("geosupportFunctionCode").equals(F1B));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void testStreetcode_FDG(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + STREETCODE_URI).queryParam(STREET_CODE, "110610").build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Map> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(Map.class).returnResult();
        Map<String, Object> body = result.getResponseBody();
        LOGGER.info("{} response body: {}", STREETCODE_URI, body);
        Map<String, Object> streetcode = (Map<String, Object>) body.get(STREET_CODE.toLowerCase());
        assertThat(streetcode.containsKey(GEOSUPPORT_RETURN_CODE));
        assertThat(streetcode.get("geosupportReturnCode").equals(SUCCESS));
        assertThat(streetcode.containsKey("geosupportFunctionCode"));
        assertThat(streetcode.get("geosupportFunctionCode").equals(FDG));
    }

    @Test
    public void testVersion(@Autowired RestTestClient client) {
        URI uri = UriComponentsBuilder.fromPath(BASE_URI + VERSION_URI).build().toUri();
        LOGGER.info("URI={}", uri);
        EntityExchangeResult<Version> result = client.get()
                            .uri(uri)
                            .exchange()
                            .expectStatus()
                            .is2xxSuccessful()
                            .expectHeader()
                            .contentType(MediaType.APPLICATION_JSON)
                            .expectBody(Version.class).returnResult();
        Version actual = result.getResponseBody();
        LOGGER.info("{} -> {}", uri.toASCIIString(), actual);
        assertThat(actual.getAccessMethod()).isNotEmpty();
        assertThat(actual.getGeoclientJniVersion()).isNotEmpty();
        assertThat(actual.getGeoclientParserVersion()).isNotEmpty();
        assertThat(actual.getGeoclientServiceVersion()).isNotEmpty();
    }
}
