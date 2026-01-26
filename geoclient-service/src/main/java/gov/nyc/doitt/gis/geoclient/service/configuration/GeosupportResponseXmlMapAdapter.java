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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "geosupportResponse")
public class GeosupportResponseXmlMapAdapter extends HashMap<String, Object> {

    public GeosupportResponseXmlMapAdapter(Map<? extends String, ? extends Object> m) {
        super(m);
    }

    public GeosupportResponseXmlMapAdapter() {
    }

    public GeosupportResponseXmlMapAdapter(int initialCapacity) {
        super(initialCapacity);
    }

    public GeosupportResponseXmlMapAdapter(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

}
