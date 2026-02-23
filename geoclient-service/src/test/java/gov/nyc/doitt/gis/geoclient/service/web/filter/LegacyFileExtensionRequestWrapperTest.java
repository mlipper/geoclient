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
package gov.nyc.doitt.gis.geoclient.service.web.filter;

import static gov.nyc.doitt.gis.geoclient.service.web.filter.QueryParamUtils.containsParam;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class LegacyFileExtensionRequestWrapperTest {

    private String originalUri = "/foo/bleh";
    private String existingParamName = "cat";
    private String existingParamValue = "Whiskers";

    private String newUri = "/foo/bar";
    private String newParamName = "dog";
    private String newParamValue = "Fido";
    private Map<String, String[]> newParams;

    private MockHttpServletRequest request;

    private LegacyFileExtensionRequestWrapper wrapper;

    @BeforeEach
    void setUp() {
        // "Original" request
        request = new MockHttpServletRequest();
        request.setRequestURI(originalUri);
        request.addParameter(existingParamName, existingParamValue);
        // Wrapper arguments
        newParams = new TreeMap<>();
        newParams.put(newParamName, new String[] { newParamValue });
        // Class under test
        wrapper = new LegacyFileExtensionRequestWrapper(request, newUri, newParams);
    }

    @Test
    void testGetParameter() {
        String actualExistingParamValue = wrapper.getParameter(existingParamName);
        assertThat(existingParamValue).isEqualTo(actualExistingParamValue);
        String actualNewParamValue = wrapper.getParameter(newParamName);
        assertThat(newParamValue).isEqualTo(actualNewParamValue);
    }

    @Test
    void testGetParameterMap() {
        Map<String, String[]> actualParams = wrapper.getParameterMap();
        assertThat(actualParams.size()).isEqualTo(2);
        assertThat(containsParam(actualParams, existingParamName, existingParamValue));
        assertThat(containsParam(actualParams, newParamName, newParamValue));
    }

    @Test
    void testGetParameterNames() {
        List<String> actualParamNames = Collections.list(wrapper.getParameterNames());
        assertThat(actualParamNames.size()).isEqualTo(2);
        assertThat(actualParamNames.contains(existingParamName));
        assertThat(actualParamNames.contains(newParamName));
    }

    @Test
    void testGetParameterValues() {
        List<String> actualParamValues = Collections.list(wrapper.getParameterNames());
        assertThat(actualParamValues.size()).isEqualTo(2);
        assertThat(actualParamValues.contains(existingParamValue));
        assertThat(actualParamValues.contains(newParamValue));
    }

    @Test
    void testGetRequestURI() {
        assertThat(newUri).isEqualTo(wrapper.getRequestURI());
    }

    @Test
    void testGetRequestURL() {
        assertThat(wrapper.getRequestURL()).endsWith(newUri);
    }
}
