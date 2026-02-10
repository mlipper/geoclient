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

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ConfigurationXmlTest {
    @Test
    void testToMap() {
        Map<String, Object> map = new ConfigurationXml() {
            {
                setRequiredArguments(List.of(new RequiredArgumentXml() {
                    {
                        setName("arg1");
                        setValue("value1");
                    }
                }, new RequiredArgumentXml() {
                    {
                        setName("arg2");
                        setValue("value2");
                    }
                }));
            }
        }.toMap();
        assert map.get("arg1").equals("value1");
        assert map.get("arg2").equals("value2");
    }
}
