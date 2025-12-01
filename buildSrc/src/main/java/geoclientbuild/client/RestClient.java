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
package geoclientbuild.client;

/**
 * A very simple REST client interface for making HTTP requests.
 * This interface defines a method to send a request and receive a response
 * without relying on HTTP-specific libraries.
 *
 * It intentionally provides a minimal abstraction over HTTP communication, limiting
 * the functionality to just sending HTTP GET, POST, and HEAD requests with the ability
 * to set headers and parameters and read response status and body.
 *
 * @author mlipper
 */
public interface RestClient {

    /**
     * Makes an HTTP request and returns the response.
     * @param request
     * @return response
     * @throws Exception
     */
    Response call(Request request) throws Exception;

    /**
     * Checks if the response status code indicates a successful request (2xx).
     * @param response
     * @return true if the response status code is in the 2xx range, false otherwise
     */
    default boolean isOk(Response response) {
        int statusCode = response.getHttpCode();
        return statusCode >= 200 && statusCode < 300;
    }
}
