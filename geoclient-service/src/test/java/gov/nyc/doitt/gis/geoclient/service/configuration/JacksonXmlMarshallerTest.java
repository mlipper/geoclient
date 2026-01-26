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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.service.domain.ServiceType;

public class JacksonXmlMarshallerTest {
    private Map<String, Object> attributes = Map.of("key1", "value1", "key2", "value2");
    private Map<String, Object> addressMap = Map.of(ServiceType.ADDRESS.elementName(), attributes);
    private Map<String, Object> randomMap = Map.of("random", attributes);

    @Test
    void testAdaptMapForXmlSerialization() {
        JacksonXmlMarshaller marshaller = new JacksonXmlMarshaller();
        Object adapted = marshaller.adaptMapForXmlSerialization(addressMap);
        assertTrue(adapted instanceof GeosupportResponseXmlMapAdapter);
    }

    @Test
    void testNeedsCustomMapSerialization() {
        JacksonXmlMarshaller marshaller = new JacksonXmlMarshaller();
        assertTrue(marshaller.needsCustomMapSerialization(addressMap));
        assertFalse(marshaller.needsCustomMapSerialization(randomMap));
    }
}
