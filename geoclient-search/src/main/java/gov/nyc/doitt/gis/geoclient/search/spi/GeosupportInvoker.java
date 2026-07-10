/*
 * Copyright 2013-2026 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.search.spi;

import java.util.Map;

/**
 * Framework-neutral SPI for invoking the NYC Geosupport functions required by
 * the single-field search pipeline. Implementations adapt this contract to
 * whatever Geosupport backend a host application provides (in-process JNI,
 * remote service, test fake, etc.).
 * <p>
 * Each method returns the raw Geosupport work-area parameter map, keyed by
 * the field aliases declared in {@code geoclient.xml}. The search module
 * treats these maps as opaque payloads and delegates interpretation to the
 * domain classes in the {@code gov.nyc.doitt.gis.geoclient.search} package.
 * <p>
 * The five methods declared here are the complete set of Geosupport calls
 * made by the single-field search implementation; the surface is
 * intentionally narrow so that host applications can supply a minimal
 * adapter without depending on the full Geoclient service API.
 *
 * @since 3.0
 */
public interface GeosupportInvoker {

    /**
     * Address search &mdash; Geosupport function {@code 1B}.
     *
     * @param houseNumber the house number component, or {@code null}
     * @param street the street name
     * @param borough the borough code or name
     * @param zip the ZIP code, or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunction1B(String houseNumber, String street, String borough, String zip);

    /**
     * Intersection search &mdash; Geosupport function {@code 2}.
     *
     * @param crossStreetOne the first cross street
     * @param boroughCrossStreetOne the borough of the first cross street
     * @param crossStreetTwo the second cross street
     * @param boroughCrossStreetTwo the borough of the second cross street
     * @param compassDirection the compass direction qualifier, or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunction2(String crossStreetOne, String boroughCrossStreetOne, String crossStreetTwo,
            String boroughCrossStreetTwo, String compassDirection);

    /**
     * Blockface search &mdash; Geosupport function {@code 3}.
     *
     * @param onStreet the on-street
     * @param boroughOnStreet the borough of the on-street
     * @param crossStreetOne the first cross street
     * @param boroughCrossStreetOne the borough of the first cross street
     * @param crossStreetTwo the second cross street
     * @param boroughCrossStreetTwo the borough of the second cross street
     * @param compassDirection the compass direction qualifier, or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunction3(String onStreet, String boroughOnStreet, String crossStreetOne,
            String boroughCrossStreetOne, String crossStreetTwo, String boroughCrossStreetTwo, String compassDirection);

    /**
     * BBL (Borough-Block-Lot) search &mdash; Geosupport function {@code BL}.
     *
     * @param borough the borough code or name
     * @param block the tax block number
     * @param lot the tax lot number
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionBL(String borough, String block, String lot);

    /**
     * BIN (Building Identification Number) search &mdash; Geosupport function
     * {@code BN}.
     *
     * @param bin the seven-digit BIN
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionBN(String bin);
}
