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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import geoclientbuild.client.Request;

public class HttpShutdownSettingsTest {

    private File settingsFile;

    @BeforeEach
    void setUp() {

        // Should be on the test classpath
        URL fileUrl = this.getClass().getResource("/http-shutdown.json");
        settingsFile = new File(fileUrl.getFile());
        if (!settingsFile.exists()) {
            throw new IllegalStateException("Test httpShutdownFile does not exist: " + settingsFile.getAbsolutePath());
        }
    }

    @Test
    void testInfo() {
        HttpShutdownSettings settings = new HttpShutdownSettings();
        settings.setSettingsFile(settingsFile);
        HttpShutdownInfo info = new HttpShutdownInfo(settings);
        String expected = info.info();
        String actual = settings.info();
        assertEquals(expected, actual,
            "Expected HttpShutdownSettings.info() to equal HttpShutdownSettingsInfo.info().");
    }

    @Test
    void testSettingsFileExists() {
        HttpShutdownSettings settings = new HttpShutdownSettings();
        assertFalse(settings.settingsFileExists(), "Expected settingsFileExists to be false when settingsFile is null");
        settings.setSettingsFile(new File("fake-file.json"));
        assertFalse(settings.settingsFileExists(),
            "Expected settingsFileExists to be false when settingsFile is missing");
        settings.setSettingsFile(settingsFile);
        assertTrue(settings.settingsFileExists(),
            "Expected settingsFileExists to be true when settingsFile exists and is valid");
    }

    @Test
    void testCreateShutdownRequest() {
        HttpShutdownSettings settings = new HttpShutdownSettings();
        settings.setSettingsFile(settingsFile);
        assertTrue(settings.settingsFileExists(),
            "Expected settingsFileExists to be true when settingsFile exists and is valid");
        HttpShutdown httpShutdown = settings.getHttpShutdown();
        Request request = httpShutdown.createShutdownRequest();
        assertNotNull(request.getId());
        assertEquals(httpShutdown.getUrl(), request.getUri(), "Request URL should match HttpShutdown URL");
        assertEquals(httpShutdown.getHttpMethod(), request.getMethod(),
            "Request method should match HttpShutdown method");
        assertEquals(httpShutdown.getParameters(), request.getParameters(),
            "Request parameters should match HttpShutdown parameters");
        assertEquals(httpShutdown.getHeaders(), request.getHeaders(),
            "Request headers should match HttpShutdown headers");
    }
}
