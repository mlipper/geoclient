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
package gov.nyc.doitt.gis.geoclient.service.configuration;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * {@link HashMap} subclass used to allow jackson-dataformat-xml to use "geosupportResponse"
 * instead of "Map" when serializing controller responses as XML.
 * 
 * @author mlipper
 * @since 2.0.4
 */
@JsonRootName("geosupportResponse")
public class GeosupportResponse extends HashMap<String, Object> {

    public GeosupportResponse(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public GeosupportResponse() {
    }

    public GeosupportResponse(int initialCapacity) {
        super(initialCapacity);
    }

    public GeosupportResponse(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

}
