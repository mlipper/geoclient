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
package gov.nyc.doitt.gis.geoclient.json;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.jni.test.GeoclientStub;
import gov.nyc.doitt.gis.geoclient.json.JsonConfiguration.JsonRegistry;

class JsonConfigurationTest {

    private GeoclientStub geoclientStub;
    private JsonConfiguration jsonConfiguration;
    private JsonRegistry jsonRegistry;

    @BeforeEach
    void setUp() {
        this.geoclientStub = new GeoclientStub();
        this.jsonConfiguration = new JsonConfiguration(this.geoclientStub);
        this.jsonRegistry = this.jsonConfiguration.getJsonRegistry();
    }

    @Test
    void test_loadFilters() throws IOException {
        this.jsonConfiguration.loadFilters();
        FilterList filterList = this.jsonRegistry.getFilterList("allFilters");
        assertNotNull(filterList);
        assertNotNull(filterList.getId());
        assertNotNull(filterList.getFilters());
        assumeFalse(filterList.getFilters().isEmpty());
        for (Filter filter : filterList.getFilters()) {
            assertTrue(filter.toString().startsWith("Filter [pattern="));
            assertTrue(filter.toString().endsWith("]"));
        }
    }

    @Test
    void test_loadWorkAreas() throws IOException {
        this.jsonConfiguration.loadFilters();
        this.jsonConfiguration.loadWorkAreas();
        assertNotNull(this.jsonRegistry.getWorkArea("WA1"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_FAP"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F1"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F1A"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F1AX"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F1B"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F2"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F2W"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_F3"));
        assertNotNull(this.jsonRegistry.getWorkArea("WA2_HR"));
        assertFalse(this.jsonRegistry.hasWorkArea("WA2_F6X"));
    }

    @Test
    void test_loadFunctions() throws IOException {
        this.jsonConfiguration.loadFilters();
        this.jsonConfiguration.loadWorkAreas();
        this.jsonConfiguration.loadFunctions();
        assertNotNull(this.jsonRegistry.getFunction("1"));
        assertNotNull(this.jsonRegistry.getFunction("1A"));
        assertNotNull(this.jsonRegistry.getFunction("1AX"));
        assertNotNull(this.jsonRegistry.getFunction("1B"));
        assertNotNull(this.jsonRegistry.getFunction("1E"));
        assertNotNull(this.jsonRegistry.getFunction("2"));
        assertNotNull(this.jsonRegistry.getFunction("2W"));
        assertNotNull(this.jsonRegistry.getFunction("3"));
        assertNotNull(this.jsonRegistry.getFunction("AP"));
        assertNotNull(this.jsonRegistry.getFunction("BB"));
        assertNotNull(this.jsonRegistry.getFunction("BF"));
        assertNotNull(this.jsonRegistry.getFunction("BL"));
        assertNotNull(this.jsonRegistry.getFunction("BN"));
        assertNotNull(this.jsonRegistry.getFunction("D"));
        assertNotNull(this.jsonRegistry.getFunction("DG"));
        assertNotNull(this.jsonRegistry.getFunction("DN"));
        assertNotNull(this.jsonRegistry.getFunction("HR"));
        assertNotNull(this.jsonRegistry.getFunction("N"));
        assertFalse(this.jsonRegistry.hasFunction("6X"));
    }

    @Test
    void test_loadWorkAreas_withoutLoadedFilters() {
        assertThrows(NullPointerException.class, () -> {
            this.jsonConfiguration.loadWorkAreas();
        });
    }

    @Test
    void test_loadFunctions_withoutLoadedWorkAreas() {
        assertThrows(JsonConfigurationException.class, () -> {
            this.jsonConfiguration.loadFunctions();
        });
    }
}
