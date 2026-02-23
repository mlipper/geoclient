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

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * Adapter to allow continued use of <code>.json</code> and <code>.xml</code>
 * in request URLs for client specification of desired <code>ContentType</code>.
 * <p>
 * This is necessary because the Spring Framework no longer supports this
 * behavior starting in version 7.x.
 *
 * @author mlipper
 * @since 2.0.4
 * @see the documentation for {@link jakarta.servlet.http.HttpServletRequestWrapper}
 * @see the documentation for {@link jakarta.servlet.http.HttpServletRequest}
 */
public class LegacyFileExtensionRequestWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = LoggerFactory.getLogger(LegacyFileExtensionRequestWrapper.class);

    public static final String QUERY_PARAM_FORMAT = "f";

    private final Map<String, String[]> newQueryParams;
    private final String newUri;
    private final StringBuffer newUrl;

    private volatile Map<String, String[]> allParameters = null;

    /**
     * Creates a new instance using a possibly altered URI and possibly added
     * query string parameters.
     *
     * @param request the {@link HttpServletRequest} being wrapped by this instance
     * @param newUri URI string which has possibly been altered
     * @param newQueryParams query parameters which possibly contains added parameters
     */
    public LegacyFileExtensionRequestWrapper(HttpServletRequest request, String newUri,
            Map<String, String[]> newQueryParams) {
        super(request);
        this.newUri = newUri;
        // Reconstruct the new URL based on the original request's components
        // but with the new URI. This is crucial if other components rely on
        // getRequestURL().
        StringBuffer originalUrl = request.getRequestURL();
        logger.debug("Original URL: {}", originalUrl);
        String originalUri = request.getRequestURI();
        logger.debug("Original URI: {}", originalUri);
        this.newUrl = new StringBuffer(originalUrl.toString().replace(originalUri, newUri));
        this.newQueryParams = newQueryParams;
    }

    /**
     * Overriden to insure return of potentially altered request URI.
     *
     * @since 2.0.4
     */
    @Override
    public String getRequestURI() {
        logger.debug("Returning request URI: {}", newUri);
        return newUri;
    }

    /**
     * Overriden to insure return of potentially altered request URL.
     *
     * @since 2.0.4
     */
    @Override
    public StringBuffer getRequestURL() {
        logger.debug("Returning request URL: {}", newUrl);
        return newUrl;
    }

    /*
     * (non-Javadoc)
     * Overriding getServletPath() is unnecessary.
     * This is for debugging purposes.
     */
    @Override
    public String getServletPath() {
        String servletPath = super.getServletPath();
        logger.debug("Returning servlet path: {}", servletPath);
        return servletPath;
    }

    /*
     * (non-Javadoc)
     * Overriding getPathInfo() is is only for debugging purposes.
     */
    @Override
    public String getPathInfo() {
        String pathInfo = super.getPathInfo();
        logger.debug("Returning path info: {}", pathInfo);
        return pathInfo;
    }

    /**
     * Override parent to insure use of customized getParameterMap method.
     *
     * @since 2.0.4
     */
    @Override
    public String getParameter(String name) {
        String[] values = getParameterMap().get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * Overriden to include possible new query parameters like <code>f=xml</code>.
     *
     * @since 2.0.4
     */
    @Override
    public Map<String, String[]> getParameterMap() {

        if (allParameters == null) {
            synchronized (this) {
                if (allParameters == null) {
                    logQueryParameters("Original query parameters: {}", super.getParameterMap());
                    Map<String, String[]> combinedMap = new TreeMap<>();
                    combinedMap.putAll(super.getParameterMap()); // Original parameters
                    combinedMap.putAll(newQueryParams); // New parameters
                    allParameters = Collections.unmodifiableMap(combinedMap);
                    logQueryParameters("New query parameters: {}", allParameters);
                }
            }
        }
        return allParameters;
    }

    /**
     * Override parent to insure use of customized getParameterMap method.
     *
     * @since 2.0.4
     */
    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    /**
     * Override parent to insure use of customized getParameterMap method.
     *
     * @since 2.0.4
     */
    @Override
    public String[] getParameterValues(String name) {
        return getParameterMap().get(name);
    }

    private void logQueryParameters(String message, Map<String, String[]> params) {
        params.forEach((key, value) -> {
            String valString = "null";
            if (value != null) {
                List<String> list = List.of(value);
                valString = String.join(",", list);
            }
            logger.debug(message, key + ": " + valString);
        });
    }
}
