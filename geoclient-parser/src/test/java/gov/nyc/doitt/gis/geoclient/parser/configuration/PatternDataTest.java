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
package gov.nyc.doitt.gis.geoclient.parser.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PatternDataTest {

    private ParserConfig patternData;

    @BeforeEach
    public void setUp() {
        patternData = new ParserConfig();
    }

    @Test
    public void testBoroughNames() {
        assertFalse(patternData.boroughNamesToBoroughMap().isEmpty());
    }

    @Test
    public void testCityNames() {
        assertFalse(patternData.cityNamesToBoroughMap().isEmpty());
    }
}
