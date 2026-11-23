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
 * Similar to a {@link Street} except adds additional semantics around code
 * length and the expectation of the first character representing a
 * {@link Borough} code.
 *
 * Internally, this class uses {@link StreetCodeType} to determine potential
 * validity of a given code represented as a string.
 *
 * @see StreetCodeType
 *
 * @author mlipper
 *
 */
public class StreetCode extends CodeNamedValue {

    private final StreetCodeType streetCodeType;

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor
     * with the given code, a null street name.
     *
     * @param code street code
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     */
    public StreetCode(String code) {
        super(code, null);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor.
     *
     * @param code street code
     * @param name street name (may be null)
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     *
     */
    public StreetCode(String code, String name) {
        super(code, name);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    /**
     * Creates a new instance by calling parent {@link CodeNamedValue} constructor.
     *
     * @param code          street code
     * @param name          street name (may be null)
     * @param caseSensitive whether the street name is considered case sensitive
     * @param streetCodeType ignored; the type is always determined from the code
     *
     * @throws NullPointerException       if the given code is null
     *
     * @see CodeNamedValue
     * @see StreetCodeType
     */
    public StreetCode(String code, String name, boolean caseSensitive, StreetCodeType streetCodeType) {
        super(code, name, caseSensitive);
        this.streetCodeType = StreetCodeType.fromCode(code);
    }

    /**
     * Indicates whether this street code has a leading borough code character.
     *
     * @return <code>true</code> if the code type includes a borough
     */
    public boolean hasBorough() {
        return this.streetCodeType.hasBorough();
    }

    /**
     * Returns the borough code, which is the first character of the street code.
     *
     * @return the borough code or <code>null</code> if this street code does not
     *         include a borough
     */
    public String getBoroughCode() {
        if (hasBorough()) {
            return Boroughs.fromCode(getCode().substring(0, 1)).getCode();
        }
        return null;
    }

    /**
     * Returns the borough code as an integer.
     *
     * @return the borough code or {@link Boroughs#UNPARSABLE_BOROUGH_CODE_SENTINEL_VALUE}
     *         if this street code does not include a borough
     */
    public int getBoroughCodeAsInt() {
        return Boroughs.parseInt(getBoroughCode());
    }

    /**
     * Returns the street code with the leading borough code character removed.
     *
     * @return the street code without a borough or the unchanged code if it does
     *         not include a borough
     */
    public String getStreetCodeWithoutBorough() {
        if (hasBorough()) {
            return getCode().substring(1);
        }
        return getCode();
    }

    /**
     * Returns the type determined from the street code.
     *
     * @return the street code type
     */
    public StreetCodeType getStreetCodeType() {
        return this.streetCodeType;
    }

}
