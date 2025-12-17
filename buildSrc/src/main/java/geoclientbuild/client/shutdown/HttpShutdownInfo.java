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
package geoclientbuild.client.shutdown;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import geoclientbuild.base.AbstractSettingsInfo;

/**
 * Info about HTTP shutdown settings.
 */
public class HttpShutdownInfo extends AbstractSettingsInfo {

    static final String HTTP_SHUTDOWN_SECTION_TITLE = "HTTP shutdown";

    private final HttpShutdownSettings settings;

    public HttpShutdownInfo(HttpShutdownSettings settings) {
        this.settings = settings;
    }

    /**
     * Get info about the HTTP shutdown settings.
     */
    @Override
    public String info() {
        StringBuilder txt = new StringBuilder();
        appendInfoSection(txt, HTTP_SHUTDOWN_SECTION_TITLE, DEFAULT_MAP_SUMMARY.execute(getFileInfo()));
        return txt.toString();
    }

    /**
     * Get info about the settings file. Package private for testing.
     *
     * @return Map with file info.
     */
    Map<String, String> getFileInfo() {
        Map<String, String> fileInfo = new HashMap<>();
        File file = settings.getSettingsFile();
        if (!settings.settingsFileExists()) {
            boolean isNotNull = (file != null);
            fileInfo.put("file", isNotNull ? file.getAbsolutePath() : "<null>");
            fileInfo.put("status", isNotNull ? "ERROR: file does not exist." : "ERROR: file is null.");
            return fileInfo;
        }
        fileInfo.put("file", file.getAbsolutePath());
        fileInfo.put("status", "enabled");

        return fileInfo;
    }
}
