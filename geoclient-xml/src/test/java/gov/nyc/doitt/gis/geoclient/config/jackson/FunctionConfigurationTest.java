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
package gov.nyc.doitt.gis.geoclient.config.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FunctionConfigurationTest {
    private ConfigurationXml configuration;
    private String expectedArgumentOne = "arg1";
    private String expectedValueOne = "value1";
    private String expectedArgumentTwo = "arg2";
    private String expectedValueTwo = "value2";
    private WorkAreaXml workAreaOne;
    private WorkAreaXml workAreaTwo;

    @BeforeEach
    public void setUp() {
        Map<String, String> namesAndValues = Map.of(expectedArgumentOne, expectedValueOne, expectedArgumentTwo,
            expectedValueTwo);
        List<RequiredArgumentXml> requiredArguments = Builders.RequiredArgmunentsXmlBuilder.builder().requiredArguments(
            namesAndValues).build();
        configuration = new ConfigurationXml();
        configuration.setRequiredArguments(requiredArguments);
        workAreaOne = Builders.WorkAreaXmlBuilder.builder().id("wa_1").build();
        workAreaTwo = Builders.WorkAreaXmlBuilder.builder().id("wa_2").build();
    }

    @Test
    void testGetConfiguration() {
        FunctionConfiguration functionConfiguration = new FunctionConfiguration("f1", configuration, workAreaOne,
            workAreaTwo);
        Map<String, Object> configMap = functionConfiguration.getConfiguration();
        assertEquals(2, configMap.size());
        assertEquals(expectedValueOne, configMap.get(expectedArgumentOne));
        assertEquals(expectedValueTwo, configMap.get(expectedArgumentTwo));
    }

    @Test
    void testIsTwoWorkAreas_true() {
        FunctionConfiguration functionConfiguration = new FunctionConfiguration("f1", configuration, workAreaOne,
            workAreaTwo);
        assert functionConfiguration.isTwoWorkAreas();
    }

    @Test
    void testIsTwoWorkAreas_false() {
        FunctionConfiguration functionConfiguration = new FunctionConfiguration("f1", configuration, workAreaOne);
        assert !functionConfiguration.isTwoWorkAreas();
    }
}
