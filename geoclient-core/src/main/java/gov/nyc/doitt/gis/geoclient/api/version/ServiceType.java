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
package gov.nyc.doitt.gis.geoclient.api.version;

/**
 * Types of Geoclient service responses. Each type names the element wrapping
 * the response content.
 *
 * @author Matthew Lipper
 */
public enum ServiceType {

    /**
     * Address response.
     */
    ADDRESS("address"),
    /**
     * Address point response.
     */
    ADDRESSPOINT("addresspoint"),
    /**
     * Borough, block and lot response.
     */
    BBL("bbl"),
    /**
     * Building identification number response.
     */
    BIN("bin"),
    /**
     * Blockface response.
     */
    BLOCKFACE("blockface"),
    /**
     * Documentation response.
     */
    DOC("doc"),
    /**
     * Intersection response.
     */
    INTERSECTION("intersection"),
    /**
     * Street name normalization response.
     */
    NORMALIZE("normalize"),
    /**
     * Place response.
     */
    PLACE("place"),
    /**
     * Street code response.
     */
    STREETCODE("streetcode"),
    /**
     * Street code response for the D function (5-digit street codes).
     */
    STREETCODE_B5SC("response"), // Special case for D* streetcode response in StreetCodeController
    /**
     * Unknown response type.
     */
    UNKNOWN("unknown");
    //VERSION("version"), // Currently unused
    //SEARCH("service");  // Currently unused

    private final String elementName;

    private ServiceType(String elementName) {
        this.elementName = elementName;
    }

    /**
     * Returns the name of the element wrapping the response content.
     *
     * @return the element name
     */
    public String elementName() {
        return this.elementName;
    }

    @Override
    public String toString() {
        return this.elementName;
    }

}
