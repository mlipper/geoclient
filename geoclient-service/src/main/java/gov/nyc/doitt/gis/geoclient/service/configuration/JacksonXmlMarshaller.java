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
package gov.nyc.doitt.gis.geoclient.service.configuration;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import gov.nyc.doitt.gis.geoclient.service.domain.ServiceType;

public class JacksonXmlMarshaller implements Marshaller, Unmarshaller {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonXmlMarshaller.class);
    private final XmlMapper xmlMapper;

    public JacksonXmlMarshaller() {
        this.xmlMapper = new XmlMapper();
        // Note: Default Map serialization may not match XStream exactly
        // SimpleModule module = new SimpleModule();
        // module.addSerializer(Map.class, new MapSerializer());
        // this.xmlMapper.registerModule(module);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true; // Assume all classes are supported
    }

    @Override
    public void marshal(Object graph, Result result) throws IOException, XmlMappingException {
        LOGGER.info("Marshalling object of type: {} XML", graph.getClass().getName());
        if (needsCustomMapSerialization(graph)) {
            LOGGER.info("Adapting response content: {}", graph);
            graph = adaptMapForXmlSerialization(graph);
        }
        // For simplicity, assuming result is a StreamResult or similar
        // In practice, need to handle different Result types
        if (result instanceof javax.xml.transform.stream.StreamResult) {
            javax.xml.transform.stream.StreamResult streamResult = (javax.xml.transform.stream.StreamResult) result;
            if (streamResult.getWriter() != null) {
                xmlMapper.writeValue(streamResult.getWriter(), graph);
            }
            else if (streamResult.getOutputStream() != null) {
                xmlMapper.writeValue(streamResult.getOutputStream(), graph);
            }
        }
        else {
            throw new UnsupportedOperationException("Unsupported Result type: " + result.getClass());
        }
    }

    @Override
    public Object unmarshal(Source source) throws IOException, XmlMappingException {
        // For simplicity, assuming source is a StreamSource
        if (source instanceof javax.xml.transform.stream.StreamSource) {
            javax.xml.transform.stream.StreamSource streamSource = (javax.xml.transform.stream.StreamSource) source;
            if (streamSource.getReader() != null) {
                return xmlMapper.readValue(streamSource.getReader(), Object.class);
            }
            else if (streamSource.getInputStream() != null) {
                return xmlMapper.readValue(streamSource.getInputStream(), Object.class);
            }
        }
        throw new UnsupportedOperationException("Unsupported Source type: " + source.getClass());
    }

    boolean needsCustomMapSerialization(Object responseObject) {
        if (responseObject instanceof Map) {
            EnumSet<ServiceType> sericeTypes = EnumSet.allOf(ServiceType.class);
            for (ServiceType serviceType : sericeTypes) {
                Map<?, ?> map = (Map<?, ?>) responseObject;
                if (map.containsKey(serviceType.elementName())) {
                    return true;
                }
            }
        }
        return false;
    }

    GeosupportResponseXmlMapAdapter adaptMapForXmlSerialization(Object responseObject) {
        @SuppressWarnings("unchecked")
        Map<String, Object> originalMap = (Map<String, Object>) responseObject;
        return new GeosupportResponseXmlMapAdapter(originalMap);
    }
}
