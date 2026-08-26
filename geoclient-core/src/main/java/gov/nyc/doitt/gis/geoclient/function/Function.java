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
package gov.nyc.doitt.gis.geoclient.function;

import java.util.Map;

/**
 * Interface representing a Geosupport function that can be executed with a
 * set of parameters. Most Geosupport functions are called with two
 * {@link WorkArea}s, typically referred to as work area one and work area two.
 * <p>
 * This interface is the primary abstraction for calling Geosupport functions.
 * It also provides methods to retrieve configuration, work areas, and other metadata.
 * <p>
 * This interface defines the supported functions through a set of constant string identifiers.
 * These values correspond to the identifiers used for {@link Function#getId()}.
 *
 * @author mlipper
 * @since 1.0
 */
public interface Function {
    /** Constant representing function 1 */
    public static final String F1 = "1";
    /** Constant representing function 1A */
    public static final String F1A = "1A";
    /** Constant representing function 1AX */
    public static final String F1AX = "1AX";
    /** Constant representing function 1B */
    public static final String F1B = "1B";
    /** Constant representing function 1E */
    public static final String F1E = "1E";
    /** Constant representing function 2 */
    public static final String F2 = "2";
    /** Constant representing function 2W */
    public static final String F2W = "2W";
    /** Constant representing function 3 */
    public static final String F3 = "3";
    /** Constant representing function AP */
    public static final String FAP = "AP";
    /** Constant representing function BB */
    public static final String FBB = "BB";
    /** Constant representing function BF */
    public static final String FBF = "BF";
    /** Constant representing function BL */
    public static final String FBL = "BL";
    /** Constant representing function BN */
    public static final String FBN = "BN";
    /** Constant representing function D */
    public static final String FD = "D";
    /** Constant representing function DG */
    public static final String FDG = "DG";
    /** Constant representing function DN */
    public static final String FDN = "DN";
    /** Constant representing function HR */
    public static final String FHR = "HR";
    /** Constant representing function N */
    public static final String FN = "N";

    /**
     * Executes the function with the given parameters.
     *
     * @param parameters a map of parameter names to values
     * @return a map of result names to values
     */
    public Map<String, Object> call(Map<String, Object> parameters);

    /**
     * Returns the configuration associated with this function.
     *
     * @return the configuration
     */
    public Configuration getConfiguration();

    /**
     * Returns the identifier of this function.
     *
     * @return the function identifier
     */
    public String getId();

    /**
     * Returns the first work area associated with this function.
     *
     * @return the first work area
     */
    public WorkArea getWorkAreaOne();

    /**
     * Returns the second work area associated with this function.
     *
     * @return the second work area
     */
    public WorkArea getWorkAreaTwo();

    /**
     * Indicates whether this function uses two work areas.
     *
     * @return true if the function uses two work areas, false otherwise
     */
    public boolean isTwoWorkAreas();
}
