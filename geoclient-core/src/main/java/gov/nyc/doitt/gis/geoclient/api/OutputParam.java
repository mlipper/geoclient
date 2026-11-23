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
 * Constants for the names of output parameters (i.e., response map keys)
 * returned by Geosupport functions.
 *
 * @author mlipper
 */
public class OutputParam {
    /** Name of the data set name fields returned by function HR. */
    public static final String HR_DSNAME = "dsname";
    /** Number of {@link #HR_DSNAME} fields returned by function HR. */
    public static final int HR_DSNAME_COUNT = 20;
    /** Name of the APEQUIV file information returned by function HR. */
    public static final String HR_FILE_APEQUIV = "apequiv";
    /** Name of the AUXSEG file information returned by function HR. */
    public static final String HR_FILE_AUXSEG = "auxseg";
    /** Name of the GEO file information returned by function HR. */
    public static final String HR_FILE_GEO = "geo";
    /** Number of GEO file information entries returned by function HR. */
    public static final int HR_FILE_GEO_COUNT = 9;
    /** Name of the GRID1R file information returned by function HR. */
    public static final String HR_FILE_GRID1R = "grid1R";
    /** Name suffix of a file's date field returned by function HR. */
    public static final String HR_FILE_INFO_DATE = "FileDate";
    /** Name suffix of a file's record count field returned by function HR. */
    public static final String HR_FILE_INFO_RECORD_COUNT = "FileRecordCount";
    /** Name suffix of a file's record type field returned by function HR. */
    public static final String HR_FILE_INFO_RECORD_TYPE = "FileRecordType";
    /** Name suffix of a file's release field returned by function HR. */
    public static final String HR_FILE_INFO_RELEASE = "FileRelease";
    /** Name suffix of a file's tag field returned by function HR. */
    public static final String HR_FILE_INFO_TAG = "FileTag";
    /** Name of the THIN file information returned by function HR. */
    public static final String HR_FILE_THIN = "thin";
    /** Name of the filler fields in the THIN file information returned by function HR. */
    public static final String HR_FILE_THIN_FILLER = "Filler";
    /** Number of filler fields in the THIN file information returned by function HR. */
    public static final int HR_FILE_THIN_FILLER_COUNT = 3;
    /** Number of record types in the THIN file information returned by function HR. */
    public static final int HR_FILE_THIN_REC_TYPE_COUNT = 3;
    /** Name of the TPAD file information returned by function HR. */
    public static final String HR_FILE_TPAD = "tpad";
    /** X coordinate of the location. */
    public static final String XCOORD = "xCoordinate";
    /** X coordinate of the internal label point. */
    public static final String XCOORD_INTERNAL_LABEL = "internalLabelXCoordinate";
    /** Y coordinate of the location. */
    public static final String YCOORD = "yCoordinate";
    /** Y coordinate of the internal label point. */
    public static final String YCOORD_INTERNAL_LABEL = "internalLabelYCoordinate";

    // Return code information is from WA1 output fields
    /** Geosupport return code. */
    public static final String GEOSUPPORT_RETURN_CODE = "geosupportReturnCode";
    /** Second Geosupport return code. */
    public static final String GEOSUPPORT_RETURN_CODE2 = "geosupportReturnCode2";
    /** Message accompanying {@link #GEOSUPPORT_RETURN_CODE}. */
    public static final String MESSAGE = "message";
    /** Message accompanying {@link #GEOSUPPORT_RETURN_CODE2}. */
    public static final String MESSAGE2 = "message2";
    /** Reason code accompanying {@link #GEOSUPPORT_RETURN_CODE}. */
    public static final String REASON_CODE = "reasonCode";
    /** Reason code accompanying {@link #GEOSUPPORT_RETURN_CODE2}. */
    public static final String REASON_CODE2 = "reasonCode2";
    /** Qualifier of {@link #REASON_CODE}. */
    public static final String REASON_CODE_QUALIFIER = "reasonCodeQualifier";
    /** Qualifier of {@link #REASON_CODE2}. */
    public static final String REASON_CODE_QUALIFIER2 = "reasonCodeQualifier2";

    private OutputParam() {
    }

}
