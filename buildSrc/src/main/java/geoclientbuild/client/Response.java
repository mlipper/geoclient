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
 * A very simple representation of an HTTP response that
 * does not use HTTP libraries.
 */
public class Response {
    private final int httpCode;
    private final String body;

    /**
     * Constructs a Response object.
     *
     * @param httpCode the HTTP status code
     * @param body the response body as a string
     */
    public Response(int httpCode, String body) {
        this.httpCode = httpCode;
        this.body = body;
    }

    /**
     * Gets the HTTP status code.
     * @return the HTTP status code
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Gets the response body.
     * @return the response body as a string
     */
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Response [httpCode=" + httpCode + ", body=" + body + "]";
    }

}
