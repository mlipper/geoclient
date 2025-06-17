package gov.nyc.doitt.gis.geoclient.json;

import com.fasterxml.jackson.databind.JsonNode;

import gov.nyc.doitt.gis.geoclient.config.GeosupportConfig;

public class Configuration {
    private static final String DEFAULT_CONFIG_FILE = "geoclient.json";
    private String configFile;
    
    public Configuration(String configFile) {
        this.configFile = configFile;
    }

    public Configuration() {
        this(DEFAULT_CONFIG_FILE);
    } 

    public GeosupportConfig loadGeosupportConfig() {
        return null;
    }

    protected JsonNode loadConfig(String configFile) {
        return null;
    }
}
