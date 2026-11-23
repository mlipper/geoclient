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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Constants and interrogative methods for determining platform and whether it
 * is supported.
 *
 * @author mlipper
 */
public class Platform {

    /** Architecture name for 64-bit platforms. */
    public static final String ARCH_X64 = "x64";

    /** Operating system family name for Linux. */
    public static final String LINUX_OS_FAMILY = "linux";

    /** Shared library file name prefix on Linux. */
    public static final String LINUX_SHARED_LIB_PREFIX = "lib";

    /** Environment variable used to locate shared libraries on Linux. */
    public static final String LINUX_SHARED_LIB_PATH_VAR = "LD_LIBRARY_PATH";

    /** Shared library file extension on Linux. */
    public static final String LINUX_SHARED_LIB_FILE_EXTENSION = "so";

    /** Operating system family name for Windows. */
    public static final String WINDOWS_OS_FAMILY = "windows";

    /** Shared library file name prefix on Windows. */
    public static final String WINDOWS_SHARED_LIB_PREFIX = "";

    /** Environment variable used to locate shared libraries on Windows. */
    public static final String WINDOWS_SHARED_LIB_PATH_VAR = "PATH";

    /** Shared library file extension on Windows. */
    public static final String WINDOWS_SHARED_LIB_FILE_EXTENSION = "dll";

    /**
     * Combines an operating system family and an architecture into a platform name
     * of the form <code>&lt;os&gt;_&lt;arch&gt;</code> (e.g.,
     * <code>linux_x64</code>).
     */
    public static final BiFunction<String, String, String> PLATFORM_NAMER = (String s1, String s2) -> {
        return String.format("%s_%s", s1, s2);
    };

    /** The supported 64-bit Linux platform. */
    public static final Platform SUPPORTED_LINUX_PLATFORM = new Platform(LINUX_OS_FAMILY, ARCH_X64);

    /** The supported 64-bit Windows platform. */
    public static final Platform SUPPORTED_WINDOWS_PLATFORM = new Platform(WINDOWS_OS_FAMILY, ARCH_X64);

    /** All supported platforms. */
    public static final List<Platform> SUPPORTED_PLATFORMS = Arrays.asList(SUPPORTED_LINUX_PLATFORM,
        SUPPORTED_WINDOWS_PLATFORM);

    private final String name;

    private final String operatingSystem;

    private final String architecture;

    /**
     * Creates a platform with the given operating system and architecture.
     *
     * @param operatingSystem operating system name, e.g., the value of the
     *                        <code>os.name</code> system property
     * @param architecture    architecture name, e.g., the value of the
     *                        <code>os.arch</code> system property
     * @throws IllegalArgumentException if either argument is <code>null</code>
     */
    public Platform(String operatingSystem, String architecture) {
        super();
        if (operatingSystem == null) {
            throw new IllegalArgumentException("Argument 'operatingSystem' cannot be null");
        }
        this.operatingSystem = operatingSystem;
        if (architecture == null) {
            throw new IllegalArgumentException("Argument 'architecture' cannot be null");
        }
        this.architecture = architecture;
        this.name = getName();
    }

    /**
     * Creates a platform from the <code>os.name</code> and <code>os.arch</code>
     * system properties of the running JVM.
     */
    public Platform() {
        this(System.getProperty("os.name"), System.getProperty("os.arch"));
    }

    /**
     * Returns the normalized platform name, e.g., <code>linux_x64</code>.
     *
     * @return the platform name
     * @throws UnsupportedPlatformException if the operating system or architecture
     *                                      is not supported
     */
    public String getName() {
        if (this.name == null) {
            String os = null;
            if (isLinux()) {
                os = LINUX_OS_FAMILY;
            }
            else if (isWindows()) {
                os = WINDOWS_OS_FAMILY;
            }

            String arch = null;
            if (is64Bit()) {
                arch = ARCH_X64;
            }

            if (os == null || arch == null) {
                throw new UnsupportedPlatformException(getOperatingSystem(), getArchitecture());
            }
            return PLATFORM_NAMER.apply(os, arch);
        }

        return this.name;
    }

    /**
     * Returns the operating system name this platform was created with.
     *
     * @return the operating system name
     */
    public String getOperatingSystem() {
        return this.operatingSystem;
    }

    /**
     * Returns the architecture name this platform was created with.
     *
     * @return the architecture name
     */
    public String getArchitecture() {
        return this.architecture;
    }

    /**
     * Indicates whether the operating system is Windows.
     *
     * @return <code>true</code> if the operating system name contains
     *         <code>windows</code> (case-insensitive)
     */
    public boolean isWindows() {
        return this.operatingSystem.toLowerCase().contains(WINDOWS_OS_FAMILY);
    }

    /**
     * Indicates whether the operating system is Linux.
     *
     * @return <code>true</code> if the operating system name contains
     *         <code>linux</code> (case-insensitive)
     */
    public boolean isLinux() {
        return this.operatingSystem.toLowerCase().contains(LINUX_OS_FAMILY);
    }

    /**
     * Indicates whether the architecture is 64-bit.
     *
     * @return <code>true</code> if the architecture name contains <code>64</code>
     */
    public boolean is64Bit() {
        return this.architecture.indexOf(ARCH_X64.substring(1)) >= 0;
    }

    private String getSharedLibraryPrefix() {
        if (isWindows()) {
            return WINDOWS_SHARED_LIB_PREFIX;
        }
        return LINUX_SHARED_LIB_PREFIX;
    }

    private String getSharedLibraryFileExtension() {
        if (isWindows()) {
            return WINDOWS_SHARED_LIB_FILE_EXTENSION;
        }
        return LINUX_SHARED_LIB_FILE_EXTENSION;
    }

    /**
     * Returns the platform-specific shared library file name for the given base
     * name, e.g., <code>libfoo.so</code> on Linux or <code>foo.dll</code> on
     * Windows.
     *
     * @param baseName the library name without prefix or file extension
     * @return the shared library file name
     */
    public String getSharedLibraryFileName(String baseName) {
        return String.format("%s%s.%s", getSharedLibraryPrefix(), baseName, getSharedLibraryFileExtension());
    }

    /**
     * Returns the name of the environment variable this platform uses to locate
     * shared libraries.
     *
     * @return <code>PATH</code> on Windows, otherwise <code>LD_LIBRARY_PATH</code>
     */
    public String getSharedLibraryPathEnvironmentVarName() {
        if (isWindows()) {
            return WINDOWS_SHARED_LIB_PATH_VAR;
        }
        return LINUX_SHARED_LIB_PATH_VAR;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Platform other = (Platform) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Platform [name=" + this.name + ", operatingSystem=" + this.operatingSystem + ", architecture="
                + this.architecture + "]";
    }

}
