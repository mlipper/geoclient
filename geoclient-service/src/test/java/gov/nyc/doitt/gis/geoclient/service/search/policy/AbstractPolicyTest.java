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
package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractPolicyTest {
    private static class TestAbstractPolicy extends AbstractPolicy {

        @Override
        public String getDescription() {
            return "This is a test.";
        }
    };

    private TestAbstractPolicy instance;

    @BeforeEach
    public void beforeEach() {
        this.instance = new TestAbstractPolicy();
    }

    @Test
    void testGetDescription() {
        assertEquals("This is a test.", instance.getDescription());
    }

    @Test
    void testGetName() {
        assertEquals("TestAbstractPolicy", this.instance.getName());
    }
}
