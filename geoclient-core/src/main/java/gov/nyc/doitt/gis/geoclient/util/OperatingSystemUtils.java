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
package gov.nyc.doitt.gis.geoclient.util;

/**
 * Operating system utilities.
 *
 * @author mlipper
 */
public class OperatingSystemUtils {

    /**
     * Creates a new instance.
     */
    public OperatingSystemUtils() {
    }

    private static final String osName = System.getProperty("os.name");

    /**
     * Indicates whether the current operating system is Windows.
     *
     * @return {@code true} if the {@code os.name} system property contains "Windows"
     */
    public static boolean isWindows() {
        return osName.contains("Windows");
    }

    /**
     * Returns the operating system name.
     *
     * @return the value of the {@code os.name} system property
     */
    public static String uname() {
        return osName;
    }
}
