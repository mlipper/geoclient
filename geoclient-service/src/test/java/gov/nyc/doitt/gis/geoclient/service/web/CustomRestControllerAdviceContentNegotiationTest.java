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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.nyc.doitt.gis.geoclient.api.InvalidStreetCodeException;
import gov.nyc.doitt.gis.geoclient.service.web.filter.LegacyFileExtensionFilter;

public class CustomRestControllerAdviceContentNegotiationTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = standaloneSetup(new TestController())
            .setControllerAdvice(new CustomRestControllerAdvice())
            .setContentNegotiationManager(contentNegotiationManager())
            .addFilter(new LegacyFileExtensionFilter())
            .build();
    }

    @Test
    void missingRequestParamWithXmlLegacyExtensionReturnsXml() throws Exception {
        this.mockMvc.perform(get("/test/missing-required.xml"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));
    }

    @Test
    void missingRequestParamWithJsonLegacyExtensionReturnsJson() throws Exception {
        this.mockMvc.perform(get("/test/missing-required.json"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));
    }

    @Test
    void customMissingBoroughZipWithXmlFormatParameterReturnsXml() throws Exception {
        this.mockMvc.perform(get("/test/missing-borough-and-zip").queryParam("f", "xml"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
            .andExpect(content().string(startsWith("<")));
    }

    @Test
    void customInvalidStreetCodeWithJsonLegacyExtensionReturnsJson() throws Exception {
        this.mockMvc.perform(get("/test/invalid-streetcode.json"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(content().string(startsWith("{")));
    }

    private ContentNegotiationManager contentNegotiationManager() {
        Map<String, MediaType> mediaTypes = new HashMap<>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        ParameterContentNegotiationStrategy parameterStrategy = new ParameterContentNegotiationStrategy(mediaTypes);
        parameterStrategy.setParameterName("f");
        return new ContentNegotiationManager(parameterStrategy, new HeaderContentNegotiationStrategy());
    }

    @RestController
    private static class TestController {

        @GetMapping("/test/missing-required")
        String missingRequired(@RequestParam String required) {
            return required;
        }

        @GetMapping("/test/missing-borough-and-zip")
        String missingBoroughAndZip() {
            throw new MissingBoroughAndZipRequestParameters();
        }

        @GetMapping("/test/invalid-streetcode")
        String invalidStreetCode() {
            throw new InvalidStreetCodeException("xx");
        }
    }
}
