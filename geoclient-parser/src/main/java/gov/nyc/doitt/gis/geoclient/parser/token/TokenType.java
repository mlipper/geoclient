/*
 * Copyright 2013-2024 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.parser.token;

/**
 * Enumeration of token types recognized by the Geoclient parser.
 * <p>
 * Each constant represents a specific type of token that may be identified
 * in an address or place string during parsing.
 * </p>
 *
 * <ul>
 *   <li>{@link #AND} - Logical "and" connector</li>
 *   <li>{@link #BETWEEN} - "Between" keyword</li>
 *   <li>{@link #BIN} - Building Identification Number</li>
 *   <li>{@link #BLOCK} - Block number</li>
 *   <li>{@link #BOROUGH_CODE} - Numeric borough code</li>
 *   <li>{@link #BOROUGH_NAME} - Borough name</li>
 *   <li>{@link #CITY_NAME} - City name</li>
 *   <li>{@link #COMPASS_DIRECTION} - Compass direction (e.g., N, S, E, W)</li>
 *   <li>{@link #CROSS_STREET_ONE} - First cross street</li>
 *   <li>{@link #CROSS_STREET_TWO} - Second cross street</li>
 *   <li>{@link #COUNTRY} - Country name</li>
 *   <li>{@link #HOUSE_NUMBER} - House number</li>
 *   <li>{@link #HOUSE_NUMBER_SUFFIX} - House number suffix</li>
 *   <li>{@link #LOT} - Lot number</li>
 *   <li>{@link #NEIGHBORHOOD_NAME} - Neighborhood name</li>
 *   <li>{@link #ON} - "On" keyword</li>
 *   <li>{@link #ON_STREET} - Street name following "on"</li>
 *   <li>{@link #STATE} - State name or abbreviation</li>
 *   <li>{@link #STREET_NAME} - Street name</li>
 *   <li>{@link #PLACE} - Place name (previously called UNRECOGNIZED).</li>
 *   <li>{@link #ZIP} - ZIP code</li>
 *   <li>{@link #PLUS4} - ZIP+4 code</li>
 * </ul>
 *
 * @author mlipper
 * @since 2.0
 * @see gov.nyc.doitt.gis.geoclient.parser.regex.UnrecognizedTextParser
 */
public enum TokenType {
    AND,
    BETWEEN,
    BIN,
    BLOCK,
    BOROUGH_CODE,
    BOROUGH_NAME,
    CITY_NAME,
    COMPASS_DIRECTION,
    CROSS_STREET_ONE,
    CROSS_STREET_TWO,
    COUNTRY,
    HOUSE_NUMBER,
    HOUSE_NUMBER_SUFFIX,
    LOT,
    NEIGHBORHOOD_NAME,
    ON,
    ON_STREET,
    STATE,
    STREET_NAME,
    PLACE,
    ZIP,
    PLUS4
}
