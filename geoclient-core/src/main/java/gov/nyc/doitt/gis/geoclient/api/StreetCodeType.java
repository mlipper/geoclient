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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Constants representing Geosupport street codes.
 *
 * @author mlipper
 *
 */
public enum StreetCodeType {

    /** Five digit street code preceded by a borough code. */
    B5SC(1 + 5),
    /** Seven digit street code preceded by a borough code. */
    B7SC(1 + 7),
    /** Ten digit street code preceded by a borough code. */
    B10SC(1 + 10),
    /** Five digit street code without a borough code. */
    _5SC(5),
    /** Seven digit street code without a borough code. */
    _7SC(7),
    /** Ten digit street code without a borough code. */
    _10SC(10),
    /** A code that does not match any other type. */
    UNRECOGNIZED(-1);

    private final int length;
    private final Pattern pattern;

    private StreetCodeType(int length) {
        this.length = length;
        switch (this.length) {
            case -1:
                this.pattern = null;
                break;
            case 5:
            case 7:
            case 10:
                this.pattern = Pattern.compile("[0-9]{" + this.length + "}");
                break;
            case 6:
            case 8:
            case 11:
                this.pattern = Pattern.compile("[1-5][0-9]{" + (this.length - 1) + "}");
                break;
            default:
                throw new IllegalArgumentException("Invalid street code length: " + this.length);
        }
    }

    /**
     * Returns the total number of characters in a code of this type, including any
     * leading borough code character.
     *
     * @return the code length or <code>-1</code> for {@link #UNRECOGNIZED}
     */
    public int length() {
        return this.length;
    }

    /**
     * Indicates whether codes of this type start with a borough code character.
     *
     * @return <code>true</code> for {@link #B5SC}, {@link #B7SC} and {@link #B10SC}
     */
    public boolean hasBorough() {
        return this.equals(B5SC) || this.equals(B7SC) || this.equals(B10SC);
    }

    /**
     * Determines whether the given street code has the format of this type.
     *
     * @param streetCode the street code to test
     * @return <code>true</code> if the code matches; always <code>false</code> for
     *         {@link #UNRECOGNIZED}
     */
    public boolean isValid(String streetCode) {
        return this.pattern != null && this.pattern.matcher(streetCode).matches();
    }

    /**
     * Determines whether the given street code is valid as a code without a
     * borough.
     *
     * @param streetCode the street code to test
     * @return <code>true</code> if the code is a valid {@link #_5SC}, {@link #_7SC}
     *         or {@link #_10SC}; <code>false</code> otherwise, including for
     *         <code>null</code>
     */
    public static boolean isValidNoBorough(String streetCode) {
        if (streetCode == null) {
            return false;
        }
        return _5SC.isValid(streetCode) || _7SC.isValid(streetCode) || _10SC.isValid(streetCode);
    }

    /**
     * Determines whether the given street code is valid as a code preceded by a
     * borough.
     *
     * @param streetCode the street code to test
     * @return <code>true</code> if the code is a valid {@link #B5SC}, {@link #B7SC}
     *         or {@link #B10SC}; <code>false</code> otherwise, including for
     *         <code>null</code>
     */
    public static boolean isValidWithBorough(String streetCode) {
        if (streetCode == null) {
            return false;
        }

        return B5SC.isValid(streetCode) || B7SC.isValid(streetCode) || B10SC.isValid(streetCode);
    }

    /**
     * Determines the type of the given street code.
     *
     * @param streetCode the street code
     * @return the matching type or {@link #UNRECOGNIZED} if none match
     * @throws NullPointerException if the street code is null
     */
    public static StreetCodeType fromCode(String streetCode) throws NullPointerException {
        if (streetCode == null) {
            throw new NullPointerException("street code argument cannot be null");
        }

        for (StreetCodeType type : StreetCodeType.types()) {
            if (type.isValid(streetCode)) {
                return type;
            }
        }
        return UNRECOGNIZED;
    }

    private static List<StreetCodeType> types() {
        List<StreetCodeType> allTypes = new ArrayList<StreetCodeType>();
        allTypes.add(B5SC);
        allTypes.add(B7SC);
        allTypes.add(B10SC);
        allTypes.add(_5SC);
        allTypes.add(_7SC);
        allTypes.add(_10SC);
        return allTypes;
    }

}
