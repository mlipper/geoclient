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
package geoclientbuild.client.shutdown;

import java.time.LocalTime;
import java.util.Map;

import geoclientbuild.client.Request;

/**
 * Value class representing an HTTP shutdown JSON file configuration.
 */
public class HttpShutdown {

    static final String REQUEST_ID_TEMPLATE = "http-shutdown-%d";

    private String url;
    private String httpMethod;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public String settings() {
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("       url: %s%n", url));
        sb.append(String.format("    method: %s%n", httpMethod));
        sb.append(String.format("parameters: %s%n", mapToString(parameters)));
        sb.append(String.format("   headers: %s%n", mapToString(headers)));
        return sb.toString();
    }

    private String mapToString(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "<none>";
        }
        StringBuilder sb = new StringBuilder();
        map.forEach((k, v) -> sb.append(k).append("=").append(v).append(", "));
        // Remove trailing comma and space
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    public boolean hasHeaders() {
        return headers != null && !headers.isEmpty();
    }

    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Request createShutdownRequest() {
        String id = String.format(REQUEST_ID_TEMPLATE, LocalTime.now().toSecondOfDay());
        return new Request(id, url, httpMethod, parameters, headers);
    }

}
