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
package gov.nyc.doitt.gis.geoclient.service.web.filter;

import static gov.nyc.doitt.gis.geoclient.service.web.filter.LegacyFileExtensionRequestWrapper.QUERY_PARAM_FORMAT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Intercepts all requests to check whether they need to be modified to
 * support legacy use of file extensions allowing clients to request supported
 * <code>ContentType</code>s.
 * <p>
 * This class uses a Spring API to receive callbacks from requests per the standard
 * <code>Servlet</code> filter API.
 *
 * @author mlipper
 * @since 2.0.4
 */
@Component
public class LegacyFileExtensionFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(LegacyFileExtensionFilter.class);

    public static final String FILE_EXT_JSON = ".json";
    public static final String FILE_EXT_XML = ".xml";

    /**
     * Implements the following logic based on the requested URI:
     * <ul>
     * <li>If: the URI string ends with <code>.json</code>, remove it and add <code>f=json</code> as a query parameter.</li>
     * <li>Else If: the URI string ends with <code></code>, remove it and add <code>f=</code> as a query parameter.</li>
     * <li>Else: </li>
     * </ul>
     * @param request the {@link jakarta.servlet.ServletRequest}
     * @param response the {@link jakarta.servlet.ServletResponse}
     * @param chain the {@link jakarta.servlet.FilterChain}
     * @since 2.0.4
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String originalUri = httpRequest.getRequestURI();
        logger.info("Examining request URI: {}", originalUri);
        String newUri = originalUri;
        Map<String, String[]> newQueryParams = new HashMap<>();
        if (originalUri.endsWith(FILE_EXT_JSON.toLowerCase())) {
            // .json requested, add f=json
            newUri = originalUri.substring(0, originalUri.length() - FILE_EXT_JSON.length());
            newQueryParams.put(QUERY_PARAM_FORMAT, new String[] { "json" });
        }
        else if (originalUri.endsWith(FILE_EXT_XML.toLowerCase())) {
            // .xml requested, add f=xml
            newUri = originalUri.substring(0, originalUri.length() - FILE_EXT_XML.length());
            newQueryParams.put(QUERY_PARAM_FORMAT, new String[] { "xml" });
        }
        else {
            // No file extension in the request URI, default to f=json only if "f"
            // query string parameter has not been specified.
            // Use "putIfAbsent" to default to JSON if no format has been specified
            newQueryParams.putIfAbsent(QUERY_PARAM_FORMAT, new String[] { "json" });
        }
        LegacyFileExtensionRequestWrapper wrappedRequest = new LegacyFileExtensionRequestWrapper(httpRequest, newUri,
            newQueryParams);
        chain.doFilter(wrappedRequest, response);
    }
}
