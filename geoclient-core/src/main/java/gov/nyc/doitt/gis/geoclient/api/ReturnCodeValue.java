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
 * Selected Geosupport return codes.
 *
 * @author mlipper
 */
public enum ReturnCodeValue {

    /** The call succeeded. */
    SUCCESS("00"),
    /** The call succeeded with a warning. */
    WARN("01"),
    /** A compass direction is required to resolve the location. */
    COMPASS_DIRECTION_REQUIRED("02"),
    /** A street name was not recognized and no similar names were found. */
    NOT_RECOGNIZED_NO_SIMILAR_NAMES("11"),
    /** A street name was not recognized but similar names were found. */
    NOT_RECOGNIZED_WITH_SIMILAR_NAMES("EE");

    private final String value;

    private ReturnCodeValue(String value) {
        this.value = value;
    }

    /**
     * Returns the Geosupport return code string.
     *
     * @return the return code
     */
    public String value() {
        return this.value;
    }

    /**
     * Determines whether the given string is this return code.
     *
     * @param string a Geosupport return code
     * @return <code>true</code> if the string equals this return code
     */
    public boolean is(String string) {
        return this.value.equals(string);
    }

}
