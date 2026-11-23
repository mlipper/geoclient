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
 * Versions of the Geoclient components and of the Geosupport installation in
 * use.
 *
 * @author Matthew Lipper
 */
public class Version {
    private String geoclientJniVersion;
    private String geoclientVersion;
    private String geoclientParserVersion;
    private String geoclientServiceVersion;
    private GeosupportVersion geosupportVersion;
    private String accessMethod;
    /**
     * Returns the method used to access Geosupport.
     *
     * @return the method used to access Geosupport
     */
    public String getAccessMethod() {
        return accessMethod;
    }

    /**
     * Sets the method used to access Geosupport.
     *
     * @param accessMethod the method used to access Geosupport
     */
    public void setAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
    }

    /**
     * Returns the Geosupport version information.
     *
     * @return the Geosupport version information
     */
    public GeosupportVersion getGeosupportVersion() {
        return geosupportVersion;
    }

    /**
     * Sets the Geosupport version information.
     *
     * @param geosupportVersion the Geosupport version information
     */
    public void setGeosupportVersion(GeosupportVersion geosupportVersion) {
        this.geosupportVersion = geosupportVersion;
    }

    /**
     * Returns the Geoclient version.
     *
     * @return the Geoclient version
     */
    public String getGeoclientVersion() {
        return geoclientVersion;
    }

    /**
     * Sets the Geoclient version.
     *
     * @param geoclientVersion the Geoclient version
     */
    public void setGeoclientVersion(String geoclientVersion) {
        this.geoclientVersion = geoclientVersion;
    }

    /**
     * Returns the Geoclient JNI version.
     *
     * @return the Geoclient JNI version
     */
    public String getGeoclientJniVersion() {
        return geoclientJniVersion;
    }

    /**
     * Sets the Geoclient JNI version.
     *
     * @param geoclientJniVersion the Geoclient JNI version
     */
    public void setGeoclientJniVersion(String geoclientJniVersion) {
        this.geoclientJniVersion = geoclientJniVersion;
    }

    /**
     * Returns the Geoclient service version.
     *
     * @return the Geoclient service version
     */
    public String getGeoclientServiceVersion() {
        return geoclientServiceVersion;
    }

    /**
     * Sets the Geoclient service version.
     *
     * @param geoclientServiceVersion the Geoclient service version
     */
    public void setGeoclientServiceVersion(String geoclientServiceVersion) {
        this.geoclientServiceVersion = geoclientServiceVersion;
    }

    /**
     * Returns the Geoclient parser version.
     *
     * @return the Geoclient parser version
     */
    public String getGeoclientParserVersion() {
        return geoclientParserVersion;
    }

    /**
     * Sets the Geoclient parser version.
     *
     * @param geoclientParserVersion the Geoclient parser version
     */
    public void setGeoclientParserVersion(String geoclientParserVersion) {
        this.geoclientParserVersion = geoclientParserVersion;
    }

    @Override
    public String toString() {
        return "Version [geoclientJniVersion=" + geoclientJniVersion + ", geoclientVersion=" + geoclientVersion
                + ", geoclientServiceVersion=" + geoclientServiceVersion + ", geosupportVersion=" + geosupportVersion
                + "]";
    }

}
