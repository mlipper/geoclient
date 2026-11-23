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
package gov.nyc.doitt.gis.geoclient.api;

/**
 * Constants for the names of input parameters (i.e., argument map keys) and
 * their well-known values used when calling Geosupport functions.
 *
 * @author mlipper
 */
public class InputParam {
    /** Borough code of a borough-block-lot (BBL) input. */
    public static final String BBL_BOROUGH_CODE = "bblBoroughCode";
    /** Tax block of a borough-block-lot (BBL) input. */
    public static final String BBL_TAX_BLOCK = "bblTaxBlock";
    /** Tax lot of a borough-block-lot (BBL) input. */
    public static final String BBL_TAX_LOT = "bblTaxLot";
    /** Building identification number (BIN). */
    public static final String BIN = "bin";
    /** Borough code of the first (or only) street or location. */
    public static final String BOROUGH_CODE = "boroughCode";
    /** Borough code of the second street. */
    public static final String BOROUGH_CODE2 = "boroughCode2";
    /** Borough code of the third street. */
    public static final String BOROUGH_CODE3 = "boroughCode3";
    /** Compass direction used to resolve ambiguous intersections. */
    public static final String COMPASS_DIRECTION = "compassDirection";
    /** {@link #COMPASS_DIRECTION} value for north. */
    public static final String COMPASS_DIR_NORTH_VALUE = "N";
    /** {@link #COMPASS_DIRECTION} value for south. */
    public static final String COMPASS_DIR_SOUTH_VALUE = "S";
    /** {@link #COMPASS_DIRECTION} value for east. */
    public static final String COMPASS_DIR_EAST_VALUE = "E";
    /** {@link #COMPASS_DIRECTION} value for west. */
    public static final String COMPASS_DIR_WEST_VALUE = "W";
    /** Flag requesting that cross street names be returned. */
    public static final String CROSS_STREET_NAMES_FLAG = "crossStreetNamesFlag";
    /** {@link #CROSS_STREET_NAMES_FLAG} value requesting cross street names. */
    public static final String CROSS_STREET_NAMES_FLAG_VALUE = "E";
    /** Geosupport function code. */
    public static final String GEOSUPPORT_FUNCTION_CODE = "geosupportFunctionCode";
    /** Address house number. */
    public static final String HOUSE_NUMBER = "houseNumber";
    /** Mode switch controlling the work area format. */
    public static final String MODE_SWITCH = "modeSwitch";
    /** {@link #MODE_SWITCH} value for extended mode. */
    public static final String MODE_SWITCH_EXTENDED_VALUE = "X";
    /** Street code of the first (or only) street. */
    public static final String STREET_CODE = "streetCode";
    /** Street code of the second street. */
    public static final String STREET_CODE2 = "streetCode2";
    /** Street code of the third street. */
    public static final String STREET_CODE3 = "streetCode3";
    /** Name of the first (or only) street. */
    public static final String STREET_NAME = "streetName";
    /** Name of the second street. */
    public static final String STREET_NAME2 = "streetName2";
    /** Name of the third street. */
    public static final String STREET_NAME3 = "streetName3";
    /** Street name normalization format flag. */
    public static final String NORMALIZATION_FORMAT = "streetNameNormalizationFormatFlag";
    /** {@link #NORMALIZATION_FORMAT} value for the compact format. */
    public static final String NORMALIZATION_FORMAT_COMPACT_VALUE = "C";
    /** {@link #NORMALIZATION_FORMAT} value for the sort format; the default if the format is unspecified. */
    public static final String NORMALIZATION_FORMAT_SORT_VALUE = "S"; // Default if format unspecified
    /** Street name normalization length limit; defaults to the maximum value of 32 if unspecified. */
    public static final String NORMALIZATION_LENGTH = "streetNameNormalizationLengthLimit"; // Defaults to maximum value of 32 if unspecified
    /** Work area format indicator. */
    public static final String WORK_AREA_FORMAT_INDICATOR = "workAreaFormatIndicator";
    /** {@link #WORK_AREA_FORMAT_INDICATOR} value for the character (<code>C</code>) format. */
    public static final String WORK_AREA_FORMAT_INDICATOR_VALUE = "C";
    /** ZIP code. */
    public static final String ZIP_CODE = "zipCode";

    private InputParam() {
    }
}
