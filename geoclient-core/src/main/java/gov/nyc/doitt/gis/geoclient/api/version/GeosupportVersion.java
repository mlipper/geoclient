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

import java.util.List;

/**
 * Bean class for data returned by Geosupport function HR.
 *
 * @author mlipper
 * @since 1.00
 */
public class GeosupportVersion {
    /**
     * Tag identifying the geo file used to determine the Geosupport version.
     */
    public static final String GEO_FILE_TAG = "GEO";
    /**
     * Tag identifying the PAD file used to determine the Geosupport release.
     */
    public static final String PAD_FILE_TAG = "PAD";
    /**
     * Value returned by {@link #getRelease()} when the release cannot be determined.
     */
    public static final String RELEASE_NOT_AVAILABLE = "RELEASE_NOT_AVAILABLE";
    /**
     * Value returned by {@link #getVersion()} when the version cannot be determined.
     */
    public static final String VERSION_NOT_AVAILABLE = "VERSION_NOT_AVAILABLE";

    private List<String> dsNames;
    private List<FileInfo> geoFileInfo;
    private ThinFileInfo thinFileInfo;
    private FileInfo apFileInfo;
    private FileInfo apequivFileInfo;
    private FileInfo auxsegFileInfo;
    private FileInfo grid1RFileInfo;
    private FileInfo sneqFileInfo;
    private FileInfo stat1AFileInfo;
    private FileInfo stat1FileInfo;
    private FileInfo stat2FileInfo;
    private FileInfo stat3FileInfo;
    private FileInfo stat3SFileInfo;
    private FileInfo statAPFileInfo;
    private FileInfo statBLFileInfo;
    private FileInfo statBNFileInfo;
    private FileInfo statDFileInfo;
    private FileInfo statFileInfo;
    private FileInfo tpadFileInfo;
    private FileInfo upadFileInfo;
    private String geofilesDirectory;

    /**
     * Returns the directory containing the Geosupport files.
     *
     * @return the geofiles directory
     */
    public String getGeofilesDirectory() {
        return geofilesDirectory;
    }

    /**
     * Sets the directory containing the Geosupport files.
     *
     * @param geofilesDirectory the geofiles directory
     */
    public void setGeofilesDirectory(String geofilesDirectory) {
        this.geofilesDirectory = geofilesDirectory;
    }

    /**
     * Creates an empty instance.
     */
    public GeosupportVersion() {

    }

    /**
     * Returns the formatted Geosupport version derived from the release of the
     * geo file tagged {@value #GEO_FILE_TAG}.
     *
     * @return the version, or {@value #VERSION_NOT_AVAILABLE} if it cannot be determined
     */
    public String getVersion() {
        FileInfo f = findGeoFileInfo(GEO_FILE_TAG);
        if (f != null && f.getRelease() != null) {
            return formatVersion(f.getRelease());
        }
        return VERSION_NOT_AVAILABLE;
    }

    /**
     * Returns the Geosupport release derived from the release of the file
     * tagged {@value #PAD_FILE_TAG}.
     *
     * @return the release, or {@value #RELEASE_NOT_AVAILABLE} if it cannot be determined
     */
    public String getRelease() {
        FileInfo f = findGeoFileInfo(PAD_FILE_TAG);
        if (f != null && f.getRelease() != null) {
            return formatRelease(f.getRelease());
        }
        return RELEASE_NOT_AVAILABLE;
    }

    String formatVersion(String version) {

        String v = version.trim();
        // Remove trailing zeros
        while (v.endsWith("0")) {
            v = v.substring(0, v.length() - 1);
        }
        // Remove leading zeros
        while (v.startsWith("0")) {
            v = v.substring(1);
        }
        int len = v.length();
        switch (len) {
            case 0:
                return VERSION_NOT_AVAILABLE;
            case 3:
            case 4:
                v = v.substring(0, 2) + "." + v.substring(2);
                break;
            default:
                // It is 1 or 2 characters, so do nothing
                break;
        }
        return v;
    }

    String formatRelease(String version) {
        return version;
    }

    private FileInfo findGeoFileInfo(String tag) {
        if (this.geoFileInfo != null) {
            for (FileInfo fileInfo : this.geoFileInfo) {
                if (tag.equals(fileInfo.getTag())) {
                    return fileInfo;
                }
            }
        }
        return null;
    }

    /**
     * Returns the geo files information.
     *
     * @return the file information for each of the geo files
     */
    public List<FileInfo> getGeoFileInfo() {
        return geoFileInfo;
    }

    /**
     * Returns the thin file information.
     *
     * @return the thin file information
     */
    public ThinFileInfo getThinFileInfo() {
        return thinFileInfo;
    }

    /**
     * Returns the grid1R file information.
     *
     * @return the file information
     */
    public FileInfo getGrid1RFileInfo() {
        return grid1RFileInfo;
    }

    /**
     * Returns the auxseg file information.
     *
     * @return the file information
     */
    public FileInfo getAuxsegFileInfo() {
        return auxsegFileInfo;
    }

    /**
     * Returns the tpad file information.
     *
     * @return the file information
     */
    public FileInfo getTpadFileInfo() {
        return tpadFileInfo;
    }

    /**
     * Returns the apequiv file information.
     *
     * @return the file information
     */
    public FileInfo getApequivFileInfo() {
        return apequivFileInfo;
    }

    /**
     * Sets the geo files information.
     *
     * @param geoFileInfo the file information for each of the geo files
     */
    public void setGeoFileInfo(List<FileInfo> geoFileInfo) {
        this.geoFileInfo = geoFileInfo;
    }

    /**
     * Sets the thin file information.
     *
     * @param thinFileInfo the thin file information
     */
    public void setThinFileInfo(ThinFileInfo thinFileInfo) {
        this.thinFileInfo = thinFileInfo;
    }

    /**
     * Sets the grid1R file information.
     *
     * @param grid1rFileInfo the file information
     */
    public void setGrid1RFileInfo(FileInfo grid1rFileInfo) {
        grid1RFileInfo = grid1rFileInfo;
    }

    /**
     * Sets the auxseg file information.
     *
     * @param auxsegFileInfo the file information
     */
    public void setAuxsegFileInfo(FileInfo auxsegFileInfo) {
        this.auxsegFileInfo = auxsegFileInfo;
    }

    /**
     * Sets the tpad file information.
     *
     * @param tpadFileInfo the file information
     */
    public void setTpadFileInfo(FileInfo tpadFileInfo) {
        this.tpadFileInfo = tpadFileInfo;
    }

    /**
     * Sets the apequiv file information.
     *
     * @param apequivFileInfo the file information
     */
    public void setApequivFileInfo(FileInfo apequivFileInfo) {
        this.apequivFileInfo = apequivFileInfo;
    }

    /**
     * Returns the ap file information.
     *
     * @return the file information
     */
    public FileInfo getApFileInfo() {
        return apFileInfo;
    }

    /**
     * Sets the ap file information.
     *
     * @param apFileInfo the file information
     */
    public void setApFileInfo(FileInfo apFileInfo) {
        this.apFileInfo = apFileInfo;
    }

    /**
     * Returns the upad file information.
     *
     * @return the file information
     */
    public FileInfo getUpadFileInfo() {
        return upadFileInfo;
    }

    /**
     * Sets the upad file information.
     *
     * @param upadFileInfo the file information
     */
    public void setUpadFileInfo(FileInfo upadFileInfo) {
        this.upadFileInfo = upadFileInfo;
    }

    /**
     * Returns the stat file information.
     *
     * @return the file information
     */
    public FileInfo getStatFileInfo() {
        return statFileInfo;
    }

    /**
     * Sets the stat file information.
     *
     * @param statFileInfo the file information
     */
    public void setStatFileInfo(FileInfo statFileInfo) {
        this.statFileInfo = statFileInfo;
    }

    /**
     * Returns the stat1 file information.
     *
     * @return the file information
     */
    public FileInfo getStat1FileInfo() {
        return stat1FileInfo;
    }

    /**
     * Sets the stat1 file information.
     *
     * @param stat1FileInfo the file information
     */
    public void setStat1FileInfo(FileInfo stat1FileInfo) {
        this.stat1FileInfo = stat1FileInfo;
    }

    /**
     * Returns the stat1A file information.
     *
     * @return the file information
     */
    public FileInfo getStat1AFileInfo() {
        return stat1AFileInfo;
    }

    /**
     * Sets the stat1A file information.
     *
     * @param stat1aFileInfo the file information
     */
    public void setStat1AFileInfo(FileInfo stat1aFileInfo) {
        stat1AFileInfo = stat1aFileInfo;
    }

    /**
     * Returns the stat2 file information.
     *
     * @return the file information
     */
    public FileInfo getStat2FileInfo() {
        return stat2FileInfo;
    }

    /**
     * Sets the stat2 file information.
     *
     * @param stat2FileInfo the file information
     */
    public void setStat2FileInfo(FileInfo stat2FileInfo) {
        this.stat2FileInfo = stat2FileInfo;
    }

    /**
     * Returns the stat3 file information.
     *
     * @return the file information
     */
    public FileInfo getStat3FileInfo() {
        return stat3FileInfo;
    }

    /**
     * Sets the stat3 file information.
     *
     * @param stat3FileInfo the file information
     */
    public void setStat3FileInfo(FileInfo stat3FileInfo) {
        this.stat3FileInfo = stat3FileInfo;
    }

    /**
     * Returns the stat3S file information.
     *
     * @return the file information
     */
    public FileInfo getStat3SFileInfo() {
        return stat3SFileInfo;
    }

    /**
     * Sets the stat3S file information.
     *
     * @param stat3sFileInfo the file information
     */
    public void setStat3SFileInfo(FileInfo stat3sFileInfo) {
        stat3SFileInfo = stat3sFileInfo;
    }

    /**
     * Returns the statAP file information.
     *
     * @return the file information
     */
    public FileInfo getStatAPFileInfo() {
        return statAPFileInfo;
    }

    /**
     * Sets the statAP file information.
     *
     * @param statAPFileInfo the file information
     */
    public void setStatAPFileInfo(FileInfo statAPFileInfo) {
        this.statAPFileInfo = statAPFileInfo;
    }

    /**
     * Returns the statBL file information.
     *
     * @return the file information
     */
    public FileInfo getStatBLFileInfo() {
        return statBLFileInfo;
    }

    /**
     * Sets the statBL file information.
     *
     * @param statBLFileInfo the file information
     */
    public void setStatBLFileInfo(FileInfo statBLFileInfo) {
        this.statBLFileInfo = statBLFileInfo;
    }

    /**
     * Returns the statBN file information.
     *
     * @return the file information
     */
    public FileInfo getStatBNFileInfo() {
        return statBNFileInfo;
    }

    /**
     * Sets the statBN file information.
     *
     * @param statBNFileInfo the file information
     */
    public void setStatBNFileInfo(FileInfo statBNFileInfo) {
        this.statBNFileInfo = statBNFileInfo;
    }

    /**
     * Returns the statD file information.
     *
     * @return the file information
     */
    public FileInfo getStatDFileInfo() {
        return statDFileInfo;
    }

    /**
     * Sets the statD file information.
     *
     * @param statDFileInfo the file information
     */
    public void setStatDFileInfo(FileInfo statDFileInfo) {
        this.statDFileInfo = statDFileInfo;
    }

    /**
     * Returns the sneq file information.
     *
     * @return the file information
     */
    public FileInfo getSneqFileInfo() {
        return sneqFileInfo;
    }

    /**
     * Sets the sneq file information.
     *
     * @param sneqFileInfo the file information
     */
    public void setSneqFileInfo(FileInfo sneqFileInfo) {
        this.sneqFileInfo = sneqFileInfo;
    }

    /**
     * Returns the data set names information.
     *
     * @return the data set names
     */
    public List<String> getDsNames() {
        return dsNames;
    }

    /**
     * Sets the data set names information.
     *
     * @param dsNames the data set names
     */
    public void setDsNames(List<String> dsNames) {
        this.dsNames = dsNames;
    }

    @Override
    public String toString() {
        return "GeosupportVersion [apFileInfo=" + apFileInfo + ", apequivFileInfo=" + apequivFileInfo
                + ", auxsegFileInfo=" + auxsegFileInfo + ", dsNames=" + dsNames + ", geofilesDirectory="
                + geofilesDirectory + ", geoFileInfo=" + geoFileInfo + ", grid1RFileInfo=" + grid1RFileInfo
                + ", sneqFileInfo=" + sneqFileInfo + ", stat1AFileInfo=" + stat1AFileInfo + ", stat1FileInfo="
                + stat1FileInfo + ", stat2FileInfo=" + stat2FileInfo + ", stat3FileInfo=" + stat3FileInfo
                + ", stat3SFileInfo=" + stat3SFileInfo + ", statAPFileInfo=" + statAPFileInfo + ", statBLFileInfo="
                + statBLFileInfo + ", statBNFileInfo=" + statBNFileInfo + ", statDFileInfo=" + statDFileInfo
                + ", statFileInfo=" + statFileInfo + ", thinFileInfo=" + thinFileInfo + ", tpadFileInfo=" + tpadFileInfo
                + ", upadFileInfo=" + upadFileInfo + "]";
    }

}
