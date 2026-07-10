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
 * {@code ContentType}s.
 * <p>
 * This class uses a Spring API to receive callbacks from requests per the
 * standard {@code Servlet} filter API.
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
     * <li>If the URI string ends with {@code .json}, remove it and add
     * {@code f=json} as a query parameter.</li>
     * <li>Else if the URI string ends with {@code .xml}, remove it and add
     * {@code f=xml} as a query parameter.</li>
     * <li>Else use the accept header to determine the content type.</li>
     * </ul>
     * <p>
     * Before Geoclient {@code 2.0.4}, the default was to return
     * {@code application/json} if the endpoint did not specify a file
     * extension. Starting with {@code 2.0.4}, use of file extensions is
     * deprecated and the default is to use the accept header to
     * determine the content type.
     * <p>
     * This logic usually produces the preferred result, which is that if the
     * {@code f} parameter is not specified, it is the responsibility of the
     * client to specify the desired format. Given that {@code application/json}
     * is returned for the <code>*&#47;*</code> accept header, this change will
     * (hopefully) not break existing clients that do not specify a format.
     * <p>
     * However, {@code GET} requests from a browser will usually be
     * {@code text/html,application/xhtml+xml,application/xml} so
     * {@code application/xml} will be returned. This may be unexpected but
     * acceptable given the manual nature of using browser this way. 
     *
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
        
        String lowerUri = originalUri.toLowerCase();
        boolean hasSuffix = false;
        if (lowerUri.endsWith(FILE_EXT_JSON)) {
            newUri = originalUri.substring(0, originalUri.length() - FILE_EXT_JSON.length());
            hasSuffix = true;
        }
        else if (lowerUri.endsWith(FILE_EXT_XML)) {
            newUri = originalUri.substring(0, originalUri.length() - FILE_EXT_XML.length());
            hasSuffix = true;
        }
        
        String existingFormat = requestedFormat(httpRequest);
        if (existingFormat != null) {
            logger.info("f={}", existingFormat);
            newQueryParams.put(QUERY_PARAM_FORMAT, new String[] { existingFormat });
        } else if (hasSuffix) {
            if (lowerUri.endsWith(FILE_EXT_JSON)) {
                newQueryParams.put(QUERY_PARAM_FORMAT, new String[] { "json" });
            }
            else if (lowerUri.endsWith(FILE_EXT_XML)) {
                newQueryParams.put(QUERY_PARAM_FORMAT, new String[] { "xml" });
            }
        }
        LegacyFileExtensionRequestWrapper wrappedRequest = new LegacyFileExtensionRequestWrapper(httpRequest, newUri,
            newQueryParams);
        chain.doFilter(wrappedRequest, response);
    }

    private String requestedFormat(HttpServletRequest httpRequest) {
        Map<String, String[]> params = httpRequest.getParameterMap();
        if (params.containsKey(QUERY_PARAM_FORMAT)) {
            String[] formatArray = httpRequest.getParameterValues(QUERY_PARAM_FORMAT);
            if (formatArray != null && formatArray.length > 0) {
                return formatArray[0];
            }
        }
        return null;
    }
}
