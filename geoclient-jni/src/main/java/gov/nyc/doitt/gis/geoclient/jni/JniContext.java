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
package gov.nyc.doitt.gis.geoclient.jni;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JniContext {

    static final Logger logger = LoggerFactory.getLogger(JniContext.class);

    public static final String DEFAULT_GC_JNI_VERSION = "geoclient-jni-2";
    private static final String GC_SHAREDLIB_BASENAME = "geoclientjni";
    private static final String GC_PACKAGE_PATH = JniContext.class.getPackage().getName().replaceAll("\\.", "\\/");

    private enum SystemProperty {

        GC_JNI_VERSION("gc.jni.version"), JAVA_IO_TMPDIR("java.io.tmpdir"), JAVA_LIBRARY_PATH("java.library.path");

        private final String key;

        private SystemProperty(String name) {
            this.key = name;
        }

        public String key() {
            return key;
        }

        @Override
        public String toString() {
            return key();
        }

    }

    private static String getSystemProperty(SystemProperty sysProp, String defaultValue) {
        String value = System.getProperty(sysProp.key());
        if (value != null) {
            logger.info("Using Java system property {} with value {}.", sysProp, value);
            return value;
        }
        if (defaultValue != null) {
            logger.info("Java system property {} is not set. Using default value {}.", sysProp, defaultValue);
            return defaultValue;
        }
        logger.warn("Java system property {} is not set and has no default value.", sysProp);
        return null;
    }

    public static String getGeoclientJniVersion() {
        return JniContext.getSystemProperty(SystemProperty.GC_JNI_VERSION, DEFAULT_GC_JNI_VERSION);
    }

    public static String getJvmTempDir() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_IO_TMPDIR, null);
    }

    public static String getJvmLibraryPath() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_LIBRARY_PATH, null);
    }

    public static String getSharedLibraryBaseName() {
        return GC_SHAREDLIB_BASENAME;
    }

    public static String getJavaPackagePath() {
        return GC_PACKAGE_PATH;
    }
}
