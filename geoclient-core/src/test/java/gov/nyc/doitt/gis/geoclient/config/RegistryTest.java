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
package gov.nyc.doitt.gis.geoclient.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Configuration;
import gov.nyc.doitt.gis.geoclient.function.Field;
import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

public class RegistryTest {
    private static final String WA_NAME = "WA1";
    private WorkArea workArea = new WorkArea(WA_NAME, new TreeSet<Field>());
    private String functionId = Function.F1B;

    @BeforeEach
    public void setUp() {
        Registry.clearAll();
    }

    @Test
    public void testWorkAreaRegistry() {
        assertFalse(Registry.containsWorkArea(WA_NAME));
        assertNull(Registry.getWorkArea(WA_NAME));
        Registry.addWorkArea(workArea);
        assertTrue(Registry.containsWorkArea(WA_NAME));
        assertSame(workArea, Registry.getWorkArea(WA_NAME));
        Registry.clearWorkAreas();
        assertFalse(Registry.containsWorkArea(WA_NAME));
        assertNull(Registry.getWorkArea(WA_NAME));
    }

    @Test
    public void testFunctionRegistry() {
        Function function = new Function() {
            @Override
            public String getId() {
                return functionId;
            }

            @Override
            public Map<String, Object> call(Map<String, Object> parameters) {
                return null;
            }

            @Override
            public WorkArea getWorkAreaOne() {
                return null;
            }

            @Override
            public WorkArea getWorkAreaTwo() {
                return null;
            }

            @Override
            public boolean isTwoWorkAreas() {
                return false;
            }

            @Override
            public Configuration getConfiguration() {
                return null;
            }
        };
        assertFalse(Registry.containsFunction(functionId));
        assertNull(Registry.getFunction(functionId));
        Registry.addFunction(function);
        assertTrue(Registry.containsFunction(functionId));
        assertSame(function, Registry.getFunction(functionId));
        Registry.clearFunctions();
        assertFalse(Registry.containsFunction(functionId));
        assertNull(Registry.getFunction(functionId));
    }

    @Test
    public void testFilterListRegistry_null_list() {
        String filterListId = "FL1";
        assertFalse(Registry.containsFilterList(filterListId));
        assertThrows(IllegalArgumentException.class, () -> {
            Registry.addFilterList(filterListId, null);
        });
    }

    @Test
    public void testFilterListRegistry() {
        String filterListId = "FL1";
        assertFalse(Registry.containsFilterList(filterListId));
        List<Filter> filterList = List.of(new Filter(".*"));
        Registry.addFilterList(filterListId, filterList);
        assertTrue(Registry.containsFilterList(filterListId));
        assertSame(filterList, Registry.getFilterList(filterListId));
    }

}
