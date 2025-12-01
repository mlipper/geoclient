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
package geoclientbuild.jarexec.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class HttpShutdownInfoTest extends BaseSettingsTest {

    @Test
    void testInfo_shutdownFileExists() throws Exception {
        HttpShutdownSettings httpShutdownSettingsFixture = httpShutdownSettingsFixture();
        // Only need for the getFileInfo() method
        HttpShutdownInfo expectedHttpShutdownInfo = new HttpShutdownInfo(httpShutdownSettingsFixture);
        // Create expected info manually using methods from abstract parent class
        StringBuilder txt = new StringBuilder();
        // Fill the StringBuffer
        abstractInfo.appendInfoSection(txt, HttpShutdownInfo.HTTP_SHUTDOWN_SECTION_TITLE,
            abstractInfo.DEFAULT_MAP_SUMMARY.execute(expectedHttpShutdownInfo.getFileInfo()));
        String expectedInfoString = txt.toString();
        // Get actual info from new HttpShutdownInfo using the same httpShutdownFile from the fixture
        String actualInfoString = new HttpShutdownInfo(httpShutdownSettingsFixture).info();
        assertEquals(expectedInfoString, actualInfoString);
        assertTrue(actualInfoString.contains("status=enabled"));
        System.out.println(actualInfoString);
    }

    @Test
    void testInfo_shutdownFileDoesNotExist() throws Exception {
        HttpShutdownSettings httpShutdownSettingsFixture = httpShutdownSettingsFixture();
        httpShutdownSettingsFixture.setHttpShutdownFile("fubar.json");
        // Only need for the getFileInfo() method
        HttpShutdownInfo expectedHttpShutdownInfo = new HttpShutdownInfo(httpShutdownSettingsFixture);
        // Create expected info manually using methods from abstract parent class
        StringBuilder txt = new StringBuilder();
        // Fill the StringBuffer
        abstractInfo.appendInfoSection(txt, HttpShutdownInfo.HTTP_SHUTDOWN_SECTION_TITLE,
            abstractInfo.DEFAULT_MAP_SUMMARY.execute(expectedHttpShutdownInfo.getFileInfo()));
        String expectedInfoString = txt.toString();
        // Get actual info from new HttpShutdownInfo using the same httpShutdownFile from the fixture
        String actualInfoString = new HttpShutdownInfo(httpShutdownSettingsFixture).info();
        assertEquals(expectedInfoString, actualInfoString);
        assertTrue(actualInfoString.contains("status=ERROR"));
        System.out.println(actualInfoString);
    }
}
