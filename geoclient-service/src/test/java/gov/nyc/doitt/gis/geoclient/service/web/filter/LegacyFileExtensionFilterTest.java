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

import static gov.nyc.doitt.gis.geoclient.service.web.filter.LegacyFileExtensionRequestWrapper.QUERY_PARAM_FORMAT;
import static gov.nyc.doitt.gis.geoclient.service.web.filter.QueryParamUtils.containsParam;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LegacyFileExtensionFilterTest {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    private LegacyFileExtensionFilter filter;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
        // Class under test
        filter = new LegacyFileExtensionFilter();
    }

    @Test
    void testDoFilter_jsonFileExtension() throws Exception {
        request.setRequestURI("/geoclient/v2/address.json");
        request.setQueryString("houseNumber=150&street=broadway&borough=manhattan");
        filter.doFilter(request, response, filterChain);
        LegacyFileExtensionRequestWrapper modifiedRequest = (LegacyFileExtensionRequestWrapper) filterChain.getRequest();
        assertThat(modifiedRequest.getRequestURI()).isEqualTo("/geoclient/v2/address");
        assertThat(modifiedRequest.getRequestURL()).endsWith("/geoclient/v2/address");
        assertThat(containsParam(modifiedRequest.getParameterMap(), QUERY_PARAM_FORMAT, "json"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "houseNumber", "150"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "street", "broadway"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "borough", "manhattan"));
    }

    @Test
    void testDoFilter_xmlFileExtension() throws Exception {
        request.setRequestURI("/geoclient/v2/address.xml");
        request.setQueryString("houseNumber=150&street=broadway&borough=manhattan");
        filter.doFilter(request, response, filterChain);
        LegacyFileExtensionRequestWrapper modifiedRequest = (LegacyFileExtensionRequestWrapper) filterChain.getRequest();
        assertThat(modifiedRequest.getRequestURI()).isEqualTo("/geoclient/v2/address");
        assertThat(modifiedRequest.getRequestURL()).endsWith("/geoclient/v2/address");
        assertThat(containsParam(modifiedRequest.getParameterMap(), QUERY_PARAM_FORMAT, "xml"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "houseNumber", "150"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "street", "broadway"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "borough", "manhattan"));
    }

    @Test
    void testDoFilter_noFileExtension_jsonFormatParameter() throws Exception {
        request.setRequestURI("/geoclient/v2/address");
        request.setQueryString("houseNumber=150&street=broadway&borough=manhattan&f=json");
        filter.doFilter(request, response, filterChain);
        LegacyFileExtensionRequestWrapper modifiedRequest = (LegacyFileExtensionRequestWrapper) filterChain.getRequest();
        assertThat(modifiedRequest.getRequestURI()).isEqualTo("/geoclient/v2/address");
        assertThat(modifiedRequest.getRequestURL()).endsWith("/geoclient/v2/address");
        assertThat(containsParam(modifiedRequest.getParameterMap(), QUERY_PARAM_FORMAT, "json"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "houseNumber", "150"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "street", "broadway"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "borough", "manhattan"));
    }

    @Test
    void testDoFilter_noFileExtension_xmlFormatParameter() throws Exception {
        request.setRequestURI("/geoclient/v2/address");
        request.setQueryString("houseNumber=150&street=broadway&borough=manhattan&f=xml");
        filter.doFilter(request, response, filterChain);
        LegacyFileExtensionRequestWrapper modifiedRequest = (LegacyFileExtensionRequestWrapper) filterChain.getRequest();
        assertThat(modifiedRequest.getRequestURI()).isEqualTo("/geoclient/v2/address");
        assertThat(modifiedRequest.getRequestURL()).endsWith("/geoclient/v2/address");
        assertThat(containsParam(modifiedRequest.getParameterMap(), QUERY_PARAM_FORMAT, "xml"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "houseNumber", "150"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "street", "broadway"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "borough", "manhattan"));
    }

    @Test
    void testDoFilter_noFileExtension_noFormatParameter() throws Exception {
        request.setRequestURI("/geoclient/v2/address");
        request.setQueryString("houseNumber=150&street=broadway&borough=manhattan");
        filter.doFilter(request, response, filterChain);
        LegacyFileExtensionRequestWrapper modifiedRequest = (LegacyFileExtensionRequestWrapper) filterChain.getRequest();
        assertThat(modifiedRequest.getRequestURI()).isEqualTo("/geoclient/v2/address");
        assertThat(modifiedRequest.getRequestURL()).endsWith("/geoclient/v2/address");
        assertThat(containsParam(modifiedRequest.getParameterMap(), QUERY_PARAM_FORMAT, "json"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "houseNumber", "150"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "street", "broadway"));
        assertThat(containsParam(modifiedRequest.getParameterMap(), "borough", "manhattan"));
    }
}
