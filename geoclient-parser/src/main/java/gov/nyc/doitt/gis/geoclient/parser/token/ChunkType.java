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
package gov.nyc.doitt.gis.geoclient.parser.token;

/**
 * Enumeration of chunk types recognized by the Geoclient parser.
 * <p>
 * Each constant represents a specific type of chunk that may be identified
 * in an address or place string during parsing.
 * </p>
 *
 * <ul>
 *   <li>{@link #ADDRESS} - Full address</li>
 *   <li>{@link #BBL} - Borough, Block, Lot identifier</li>
 *   <li>{@link #BIN} - Building Identification Number</li>
 *   <li>{@link #BLOCKFACE} - Blockface identifier</li>
 *   <li>{@link #COUNTY} - County name</li>
 *   <li>{@link #INTERSECTION} - Intersection of streets</li>
 *   <li>{@link #HOUSE_NUMBER} - House number</li>
 *   <li>{@link #STREET_NAME} - Street name</li>
 *   <li>{@link #ORIGINAL_INPUT} - Original input text</li>
 *   <li>{@link #SUBSTRING} - Substring of the original input</li>
 *   <li>{@link #PLACE} - Place name (previously called UNRECOGNIZED)</li>
 * </ul>
 *
 * @see gov.nyc.doitt.gis.geoclient.parser.regex.UnrecognizedTextParser
 * @author mlipper
 */
//@formatter:off
public enum ChunkType {
    ADDRESS,
    BBL,
    BIN,
    BLOCKFACE,
    COUNTY,
    INTERSECTION,
    HOUSE_NUMBER,
    STREET_NAME,
    ORIGINAL_INPUT,
    SUBSTRING,
    PLACE
}
//@formatter:on