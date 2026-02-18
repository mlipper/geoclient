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
package gov.nyc.doitt.gis.geoclient.parser.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import gov.nyc.doitt.gis.geoclient.parser.Parser;
import gov.nyc.doitt.gis.geoclient.parser.SingleFieldSearchParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.AddressParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BblParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BinParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BlockfaceParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.BoroughParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.CityParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.CountryParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.IntersectionParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.UnrecognizedTextParser;
import gov.nyc.doitt.gis.geoclient.parser.regex.ZipParser;
import gov.nyc.doitt.gis.geoclient.parser.util.ResourceLoader;

public class ParserConfig {

    public static final String NY_CITY = "(New York City|N\\.?Y\\.?C\\.?)";
    public static final String STATES = "(Alabama|Alaska|Arizona|Arkansas|California|Colorado|Connecticut|Delaware|Florida|Georgia|Hawaii|Idaho|Illinois|Indiana|Iowa|Kansas|Kentucky|Louisiana|Maine|Maryland|Massachusetts|Michigan|Minnesota|Mississippi|Missouri|Montana|Nebraska|Nevada|New Hampshire|New Jersey|New Mexico|New York|North Carolina|North Dakota|Ohio|Oklahoma|Oregon|Pennsylvania|Rhode Island|South Carolina|South Dakota|Tennessee|Texas|Utah|Vermont|Virginia|Washington|West Virginia|Wisconsin|Wyoming|Washington DC|Puerto Rico|U\\.?S\\.? Virgin Islands|American Samoa|Guam|Northern Mariana Islands|A\\.?L\\.?|A\\.?K\\.?|A\\.?Z\\.?|A\\.?R\\.?|C\\.?A\\.?|C\\.?O\\.?|C\\.?T\\.?|D\\.?E\\.?|F\\.?L\\.?|G\\.?A\\.?|H\\.?I\\.?|I\\.?D\\.?|I\\.?L\\.?|I\\.?N\\.?|I\\.?A\\.?|K\\.?S\\.?|K\\.?Y\\.?|L\\.?A\\.?|M\\.?E\\.?|M\\.?D\\.?|M\\.?A\\.?|M\\.?I\\.?|M\\.?N\\.?|M\\.?S\\.?|M\\.?O\\.?|M\\.?T\\.?|N\\.?E\\.?|N\\.?V\\.?|N\\.?H\\.?|N\\.?J\\.?|N\\.?M\\.?|N\\.?Y\\.?|N\\.?C\\.?|N\\.?D\\.?|O\\.?H\\.?|O\\.?K\\.?|O\\.?R\\.?|P\\.?A\\.?|R\\.?I\\.?|S\\.?C\\.?|S\\.?D\\.?|T\\.?N\\.?|T\\.?X\\.?|U\\.?T\\.?|V\\.?T\\.?|V\\.?A\\.?|W\\.?A\\.?|W\\.?V\\.?|W\\.?I\\.?|W\\.?Y\\.?|D\\.?C\\.?|P\\.?R\\.?|V\\.?I\\.?|A\\.?S\\.?|G\\.?U\\.?|M\\.?P\\.?)";

    public Properties boroughNameProperties() {
        return new ResourceLoader().classpathProperties("borough-names.properties");
    }

    public Properties cityNameProperties() {
        return new ResourceLoader().classpathProperties("city-names.properties");
    }

    public Map<String, String> boroughNamesToBoroughMap() {
        Map<String, String> map = toMap(boroughNameProperties());
        return map;
    }

    public Map<String, String> cityNamesToBoroughMap() {
        Map<String, String> map = toMap(cityNameProperties());
        return map;
    }

    public Set<String> cityNames() {
        return cityNamesToBoroughMap().keySet();
    }

    public Set<String> boroughNames() {
        return boroughNamesToBoroughMap().keySet();
    }

    public AddressParser addressParser() {
        return new AddressParser();
    }

    public BblParser bblParser() {
        return new BblParser();
    }

    public BinParser binParser() {
        return new BinParser();
    }

    public BlockfaceParser blockfaceParser() {
        return new BlockfaceParser();
    }

    public BoroughParser boroughParser() {
        return new BoroughParser(boroughNames());
    }

    public CityParser cityParser() {
        return new CityParser(cityNames());
    }

    public CountryParser countryParser() {
        return new CountryParser();
    }

    public IntersectionParser intersectionParser() {
        return new IntersectionParser();
    }

    public ZipParser zipParser() {
        return new ZipParser();
    }

    public UnrecognizedTextParser unrecognizedTextParser() {
        return new UnrecognizedTextParser();
    }

    public SingleFieldSearchParser singleFieldSearchParser() {
        List<Parser> parsers = new ArrayList<>();
        parsers.add(bblParser());
        parsers.add(binParser());
        parsers.add(countryParser());
        parsers.add(zipParser());
        parsers.add(boroughParser());
        parsers.add(cityParser());
        parsers.add(blockfaceParser());
        parsers.add(intersectionParser());
        parsers.add(addressParser());
        parsers.add(unrecognizedTextParser());
        return new SingleFieldSearchParser(parsers);
    }

    /**
     * Converts a {@link Properties} object to a {@link Map}.
     *
     * @param properties the Properties object to convert
     * @return a map containing the same key-value pairs as the given properties
     */
    protected Map<String, String> toMap(Properties properties) {
        Map<String, String> map = new TreeMap<>();
        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.getProperty(key));
        }
        return map;
    }
}
