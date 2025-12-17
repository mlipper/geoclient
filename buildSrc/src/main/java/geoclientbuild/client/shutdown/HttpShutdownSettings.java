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
import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import geoclientbuild.jarexec.settings.SettingsException;

/**
 * JSON-based configuration file loader for HTTP shutdown settings.
 */
public class HttpShutdownSettings {

    private File settingsFile;
    private HttpShutdownInfo info;

    public HttpShutdownSettings() {
        this.info = new HttpShutdownInfo(this);
    }

    public String info() {
        return info.info();
    }

    public void setHttpShutdownFile(String settingsFile) {
        this.setSettingsFile(new File(settingsFile));
    }

    public void setSettingsFile(File settingsFile) {
        this.settingsFile = settingsFile;
    }

    public File getSettingsFile() {
        return settingsFile;
    }

    public boolean settingsFileExists() {
        return settingsFile != null && settingsFile.exists();
    }

    /**
     * Loads HTTP shutdown settings from a JSON file.
     *
     * @param file the JSON file containing HTTP shutdown settings
     * @return the loaded HttpShutdown settings
     * @throws SettingsException if the settings file is null or does not exist
     */
    public HttpShutdown getHttpShutdown() throws SettingsException {
        if (!settingsFileExists()) {
            throw new IllegalStateException(
                "HTTP shutdown file not found: " + (settingsFile != null ? settingsFile.getAbsolutePath() : "<null>"));
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(settingsFile, HttpShutdown.class);
        }
        catch (StreamReadException e) {
            throw new SettingsException("Error parsing file " + settingsFile.getAbsolutePath(), e);
        }
        catch (DatabindException e) {
            throw new SettingsException(
                "Error binding HttpShutdown instance using file " + settingsFile.getAbsolutePath(), e);
        }
        catch (IOException e) {
            throw new SettingsException("I/O error reading file " + settingsFile.getAbsolutePath(), e);
        }
    }
}
