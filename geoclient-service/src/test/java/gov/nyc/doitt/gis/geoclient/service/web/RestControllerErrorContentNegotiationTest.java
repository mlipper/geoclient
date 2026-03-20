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
package gov.nyc.doitt.gis.geoclient.service.web;

import static org.hamcrest.Matchers.startsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;

import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.web.filter.LegacyFileExtensionFilter;

public class RestControllerErrorContentNegotiationTest {

    private MockMvc mockMvc;
    private GeosupportService geosupportService;

    @BeforeEach
    void setUp() {
        this.geosupportService = mock(GeosupportService.class);
        RestController restController = new RestController();
        restController.setGeosupportService(this.geosupportService);
        this.mockMvc = standaloneSetup(restController)
            .setControllerAdvice(new CustomRestControllerAdvice())
            .setContentNegotiationManager(contentNegotiationManager())
            .addFilter(new LegacyFileExtensionFilter())
            .build();
    }

    @Test
    void addressXmlWithMissingBoroughAndZipReturnsXmlProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address.xml").queryParam("street", "Broadway"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressJsonWithMissingBoroughAndZipReturnsJsonProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address.json").queryParam("street", "Broadway"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressXmlWithMissingRequiredStreetReturnsXmlProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address.xml").queryParam("borough", "manhattan"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressWithLegacyFormatParameterJsonReturnsJsonProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan").queryParam("f", "json"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressWithoutFormatParamWithAcceptXmlReturnsXmlProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressWithoutFormatParamWithAcceptJsonReturnsJsonProblemDetail() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressWithCaseInsensitiveXmlSuffixReturnsXml() throws Exception {
        this.mockMvc.perform(get("/address.XML").queryParam("street", "Broadway"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void addressWithCaseInsensitiveJsonSuffixReturnsJson() throws Exception {
        this.mockMvc.perform(get("/address.JSON").queryParam("street", "Broadway"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void fParameterTakesPrecedenceOverXmlSuffix() throws Exception {
        this.mockMvc.perform(get("/address.xml").queryParam("street", "Broadway").queryParam("f", "json"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void fParameterCaseInsensitiveJson() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan").queryParam("f", "JSON"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void defaultsToJsonWhenNeitherFormatNorAcceptSpecified() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));

        verifyNoInteractions(this.geosupportService);
    }

    @Test
    void acceptHeaderFallbackWhenNoFormatParam() throws Exception {
        this.mockMvc.perform(get("/address").queryParam("borough", "manhattan").accept(MediaType.APPLICATION_XML))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));

        verifyNoInteractions(this.geosupportService);
    }

    private ContentNegotiationManager contentNegotiationManager() {
        Map<String, MediaType> mediaTypes = new HashMap<>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
        parameterStrategy.setParameterName("f");
        return new ContentNegotiationManager(parameterStrategy, new HeaderContentNegotiationStrategy());
    }
}
