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
package gov.nyc.doitt.gis.geoclient.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Geoclient response value object.  This class is a simple subclass of {@link HashMap} that:
 * <ul>
 * <li>Provides an explicit type for Geoclient responses instead of using {@code Map<String, Object>}</li>
 * <li>Allows Jackson to serialize/deserialize controller responses as JSON with the root element name
 * "geosupportResponse" instead of "Map"</li>
 * </ul>
 *
 * @author mlipper
 * @since 2.0.4
 */
public class GeosupportResponse extends HashMap<String, Object> {

    /**
     * Constructs a new GeosupportResponse with the same mappings as the specified map.
     */
    public GeosupportResponse(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    /**
     * Constructs an empty GeosupportResponse.
     */
    public GeosupportResponse() {
    }

    /**
     * Constructs a new GeosupportResponse with the specified initial capacity.
     */
    public GeosupportResponse(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a new GeosupportResponse with the specified initial capacity and load factor.
     */
    public GeosupportResponse(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

}
