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

import java.text.SimpleDateFormat;

/**
 * Base for different types of file information returned by Geosupport's HR
 * function.
 *
 * Note: Prior to version 2.0.4, XML responses did not include <code>&lt;formattedDate&gt;</code>
 * as child elements of the various <code>&lt;fileInfo&gt;</code> types.
 * This element is returned now to better align with the JSON responses using this same class.
 *
 * @author mlipper
 * @since 1.00
 */
class BaseFileInfo {

    private static final SimpleDateFormat STRING_TO_DATE = new SimpleDateFormat("yyMMdd");
    private static final SimpleDateFormat DATE_TO_STRING = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * The file tag.
     */
    protected String tag;
    /**
     * The file date in {@code yyMMdd} format.
     */
    protected String date;
    /**
     * The file release.
     */
    protected String release;
    /**
     * The number of records in the file.
     */
    protected String recordCount;

    /**
     * Creates an empty instance.
     */
    public BaseFileInfo() {
    }

    /**
     * Creates an instance with the given values.
     *
     * @param tag the file tag
     * @param date the file date in {@code yyMMdd} format
     * @param release the file release
     * @param recordCount the number of records in the file
     */
    public BaseFileInfo(String tag, String date, String release, String recordCount) {
        super();
        this.tag = tag;
        this.date = date;
        this.release = release;
        this.recordCount = recordCount;
    }

    /**
     * Returns the file date formatted as {@code yyyy-MM-dd}.
     *
     * @return the formatted date, or the unmodified date if it cannot be parsed
     */
    public String getFormattedDate() {
        return applyFormat(this.date);
    }

    String applyFormat(String dateString) {
        try {
            return DATE_TO_STRING.format(STRING_TO_DATE.parse(dateString));
        }
        catch (Exception ignored) {
            return dateString;
        }
    }

    /**
     * Returns the file date.
     *
     * @return the date in {@code yyMMdd} format
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the file date.
     *
     * @param date the date in {@code yyMMdd} format
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the file tag.
     *
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * Sets the file tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the file release.
     *
     * @return the release
     */
    public String getRelease() {
        return release;
    }

    /**
     * Sets the file release.
     *
     * @param release the release
     */
    public void setRelease(String release) {
        this.release = release;
    }

    /**
     * Returns the number of records in the file.
     *
     * @return the record count
     */
    public String getRecordCount() {
        return recordCount;
    }

    /**
     * Sets the number of records in the file.
     *
     * @param recordCount the record count
     */
    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }
}
