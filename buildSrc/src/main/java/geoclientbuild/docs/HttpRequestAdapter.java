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
package geoclientbuild.docs;

import java.util.Map;

import geoclientbuild.client.Request;

/**
 * Adapter class for {@link geoclientbuild.client.Request}
 * that adds a {@link HttpRequestAdapter#getType()} method.
 *
 * @since 2.0
 * @author mlipper
 */
public class HttpRequestAdapter extends Request {

    private String type;

    public HttpRequestAdapter() {
        super();
    }

    public HttpRequestAdapter(String id, String uri, String method, Map<String, String> parameters, String type) {
        super(id, uri, method, parameters);
        this.type = type;
    }

    public HttpRequestAdapter(String type, String method) {
        this.type = type;
        this.setMethod(method);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
