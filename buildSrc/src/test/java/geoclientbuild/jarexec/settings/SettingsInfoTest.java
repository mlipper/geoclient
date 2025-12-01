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

import static geoclientbuild.jarexec.settings.SettingsInfo.COMMAND_LINE_SECTION_TITLE;
import static geoclientbuild.jarexec.settings.SettingsInfo.ENV_SECTION_TITLE;
import static geoclientbuild.jarexec.settings.SettingsInfo.WORKING_DIR_SECTION_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SettingsInfoTest extends BaseSettingsTest {

    @Test
    void testInfo() {
        Settings settingsFixture = settingsFixture();

        // Create expected info manually using static methods
        StringBuilder txt = new StringBuilder();
        abstractInfo.appendInfoSection(txt, ENV_SECTION_TITLE,
            abstractInfo.DEFAULT_MAP_SUMMARY.execute(settingsFixture.getEnvironment()));
        abstractInfo.appendInfoSection(txt, WORKING_DIR_SECTION_TITLE,
            settingsFixture.getWorkingDirectory().getAbsolutePath());
        abstractInfo.appendInfoSection(txt, COMMAND_LINE_SECTION_TITLE, settingsFixture.commandLine());
        //abstractInfo.appendInfoSection(txt, HTTP_SHUTDOWN_SECTION_TITLE, httpShutdownInfo(settingsFixture));
        String expectedInfo = txt.toString();
        // Get actual info from SettingsInfo created from fixture
        String actualInfo = new SettingsInfo(settingsFixture).info();
        assertEquals(expectedInfo, actualInfo);
    }
}
