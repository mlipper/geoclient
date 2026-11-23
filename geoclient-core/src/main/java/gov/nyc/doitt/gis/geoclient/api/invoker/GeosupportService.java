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
package gov.nyc.doitt.gis.geoclient.api.invoker;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.api.version.Version;

/**
 * Full Geosupport invocation API used by higher-level Geoclient components
 * (REST controllers, CLI, etc.). Extends the narrow {@link GeosupportInvoker}
 * SPI so that consumers requiring only the five search-pipeline methods can
 * depend on the smaller contract.
 */
public interface GeosupportService extends GeosupportInvoker {
    /**
     * Address point search &mdash; Geosupport function {@code AP}.
     *
     * @param houseNumber the house number component, or {@code null}
     * @param street the street name
     * @param borough the borough code or name, or {@code null}
     * @param zip the ZIP code, or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionAP(String houseNumber, String street, String borough, String zip);

    /**
     * Street name lookup for a 5-digit street code &mdash; Geosupport function
     * {@code D}.
     *
     * @param streetCodeOne the first borough-prefixed street code, required
     * @param streetCodeTwo the second street code, or {@code null}
     * @param streetCodeThree the third street code, or {@code null}
     * @param length the maximum length of the normalized names, or {@code null}
     * @param format the normalization format ({@code S} for sort or {@code C} for
     *        compact), or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionD(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format);

    /**
     * Street name lookup for a 7-digit street code &mdash; Geosupport function
     * {@code DG}.
     *
     * @param streetCodeOne the first borough-prefixed street code, required
     * @param streetCodeTwo the second street code, or {@code null}
     * @param streetCodeThree the third street code, or {@code null}
     * @param length the maximum length of the normalized names, or {@code null}
     * @param format the normalization format ({@code S} for sort or {@code C} for
     *        compact), or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionDG(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format);

    /**
     * Street name lookup for a 10-digit street code &mdash; Geosupport function
     * {@code DN}.
     *
     * @param streetCodeOne the first borough-prefixed street code, required
     * @param streetCodeTwo the second street code, or {@code null}
     * @param streetCodeThree the third street code, or {@code null}
     * @param length the maximum length of the normalized names, or {@code null}
     * @param format the normalization format ({@code S} for sort or {@code C} for
     *        compact), or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionDN(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format);

    /**
     * Geosupport version information &mdash; Geosupport function {@code HR}.
     *
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionHR();

    /**
     * Calls the Geosupport function identified by the
     * {@code geosupportFunctionCode} entry of the given parameters.
     *
     * @param params the input parameters, which must include the Geosupport
     *        function code
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callGeosupport(Map<String, Object> params);

    /**
     * Street name normalization &mdash; Geosupport function {@code N}.
     *
     * @param name the street name to normalize
     * @param length the maximum length of the normalized name, or {@code null}
     * @param format the normalization format ({@code S} for sort or {@code C} for
     *        compact), or {@code null}
     * @return the raw Geosupport work-area parameter map
     */
    Map<String, Object> callFunctionN(String name, Integer length, String format);

    /**
     * Street name lookup which selects function {@code D}, {@code DG} or
     * {@code DN} based on the format of the first street code.
     *
     * @param streetCodeOne the first borough-prefixed street code, required
     * @param streetCodeTwo the second street code, or {@code null}
     * @param streetCodeThree the third street code, or {@code null}
     * @param length the maximum length of the normalized names, or {@code null}
     * @param format the normalization format ({@code S} for sort or {@code C} for
     *        compact), or {@code null}
     * @return the raw Geosupport work-area parameter map
     * @throws gov.nyc.doitt.gis.geoclient.api.InvalidStreetCodeException if the
     *         first street code is not a valid 5, 7 or 10-digit code
     */
    Map<String, Object> callStreetNameFunction(String streetCodeOne, String streetCodeTwo, String streetCodeThree,
            Integer length, String format);

    /**
     * Retrieves the version of the Geosupport installation by calling function
     * {@code HR}.
     *
     * @return the Geosupport {@link Version}
     */
    Version version();
}
