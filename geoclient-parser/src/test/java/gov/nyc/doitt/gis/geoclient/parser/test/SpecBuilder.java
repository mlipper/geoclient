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
package gov.nyc.doitt.gis.geoclient.parser.test;

import java.util.ArrayList;
import java.util.List;

import gov.nyc.doitt.gis.geoclient.parser.util.ResourceLoader;
import tools.jackson.dataformat.xml.XmlMapper;

public class SpecBuilder {
    private static final String PARSER_TEST_DATA_FILE = "specs.xml";
    private final XmlMapper xmlMapper;
    private final UnparsedSpecs unparsedTokenSpecs;

    public SpecBuilder() {
        xmlMapper = XmlMapper.builder().build();
        try {
            this.unparsedTokenSpecs = xmlMapper.readValue(new ResourceLoader().classpathResource(PARSER_TEST_DATA_FILE),
                UnparsedSpecs.class);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to parse specs.xml", e);
        }
    }

    public List<ChunkSpec> getSpecs(String testAttribute) {
        ChunkSpecParser specParser = new ChunkSpecParser();
        List<ChunkSpec> specs = new ArrayList<>();
        for (UnparsedSpec unparsed : this.unparsedTokenSpecs.getSpecs()) {
            if (testAttribute.equalsIgnoreCase(unparsed.getTest())) {
                specs.add(specParser.parse(unparsed.getId(), unparsed.getBody()));
            }
        }
        return specs;
    }
}
