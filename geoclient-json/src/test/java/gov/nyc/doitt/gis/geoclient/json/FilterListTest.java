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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import gov.nyc.doitt.gis.geoclient.function.Filter;

public class FilterListTest {

    @Test
    void testFilterList_add() {
        FilterList flist = new FilterList();
        Filter filter1 = new Filter("foo");
        flist.add(filter1);
        Filter filter2 = new Filter("bar");
        flist.add(filter2);
        List<Filter> filters = flist.getFilters();
        assertEquals("Filter [pattern=foo]", filters.get(0).toString());
        assertEquals("Filter [pattern=bar]", filters.get(1).toString());
    }

    @Test
    void testFilterList_constructor() {
        assertEquals(0, new FilterList().getFilters().size());
    }
}
