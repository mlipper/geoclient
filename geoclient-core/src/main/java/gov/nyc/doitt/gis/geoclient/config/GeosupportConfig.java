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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;
import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

public class GeosupportConfig {

    public static final String DEFAULT_CONFIG_FILE = "geoclient.xml";

    private static final Logger log = LoggerFactory.getLogger(GeosupportConfig.class);
    private static final String WORKAREA_ONE_ID = "WA1"; // Hack alert

    public GeosupportConfig(Geoclient geoclient) {
        this(DEFAULT_CONFIG_FILE, geoclient);
    }

    public GeosupportConfig(String configFile, Geoclient geoclient) {
        log.info("Loading geoclient configuration from file {}", configFile);
        XmlConfigurationLoader xmlConfigurationLoader = new XmlConfigurationLoader();
        try {
            xmlConfigurationLoader.load(geoclient, configFile);
        }
        catch (Exception e) {
            log.error("Error loading configuration file: {}", configFile, e);
            throw new XmlConfigurationException(configFile, e);
        }
        log.info("Successfully loaded file {}", configFile);
    }

    public Function getFunction(String id) {
        if (!Registry.containsFunction(id)) {
            throw new UnknownFunctionException(id);
        }
        return Registry.getFunction(id);
    }

    WorkArea getWorkAreaOne() {
        return Registry.getWorkArea(WORKAREA_ONE_ID);
    }

}
