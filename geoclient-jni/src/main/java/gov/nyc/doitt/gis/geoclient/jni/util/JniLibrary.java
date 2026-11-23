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
package gov.nyc.doitt.gis.geoclient.jni.util;

/**
 * Used to determine the platform-specific name of a native library. Provides
 * a <code>Builder</code> for fluid-style construction.
 *
 * @author mlipper
 */
public class JniLibrary {

    private final String name;

    private final Platform platform;

    private final String version;

    /**
     * Builder class for creating a JniLibrary using a fluid-style construction.
     */
    public static class Builder {

        private String name;

        private Platform platform;

        private String version;

        /**
         * Builder for setting the platform.
         * @param platform the platform to set
         * @return the builder instance
         */
        public Builder platform(Platform platform) {
            this.platform = platform;
            return this;
        }

        /**
         * Builder for setting the platform using operating system and architecture.
         * @param operatingSystem the operating system to set
         * @param architecture the architecture to set
         * @return the builder instance
         */
        public Builder platform(String operatingSystem, String architecture) {
            this.platform = new Platform(operatingSystem, architecture);
            return this;
        }

        /**
         * Builder for setting the name of the library.
         * @param name the name to set
         * @return the builder instance
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Builder for setting the version of the library.
         * @param version the version to set
         * @return the builder instance
         */
        public Builder version(String version) {
            this.version = version;
            return this;
        }

        /**
         * Builder for constructing the JniLibrary instance.
         * @return the constructed JniLibrary instance
         */
        public JniLibrary build() {
            if (this.name == null) {
                throw new IllegalStateException("Name cannot be null");
            }

            if (this.platform == null) {
                throw new IllegalStateException("Platform cannot be null");
            }

            if (this.version == null) {
                this.version = "UNKNOWN";
            }

            return new JniLibrary(this.name, this.platform, this.version);
        }

    }

    private JniLibrary(String name, Platform platform, String version) {
        super();
        this.name = name;
        this.platform = platform;
        this.version = version;
    }

    /**
     * Creates a new {@link Builder}.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the platform this library is built for.
     *
     * @return the platform
     */
    public Platform getPlatform() {
        return this.platform;
    }

    /**
     * Returns the name of the platform-specific directory containing the library,
     * i.e., the platform name with underscores replaced by hyphens (e.g.,
     * <code>linux-x64</code>).
     *
     * @return the platform directory name
     */
    public String getPlatformDirName() {
        return this.platform.getName().replace("_", "-");
    }

    /**
     * Returns the platform-specific shared library file name (e.g.,
     * <code>libgeoclientjni.so</code>).
     *
     * @return the library file name
     */
    public String getLibraryFileName() {
        return this.platform.getSharedLibraryFileName(this.name);
    }

    /**
     * Returns the resource name of the library relative to this library's package
     * path, in the form <code>&lt;platformDirName&gt;/&lt;libraryFileName&gt;</code>.
     *
     * @return the relative resource name
     */
    public String getResourceName() {
        return String.format("%s/%s", getPlatformDirName(), getLibraryFileName());
    }

    /**
     * Returns the platform-independent base name of the library.
     *
     * @return the library name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the version of the library.
     *
     * @return the library version
     */
    public String getVersion() {
        return this.version;
    }

}
