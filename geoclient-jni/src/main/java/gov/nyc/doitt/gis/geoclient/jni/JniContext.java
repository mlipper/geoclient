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

/**
 * Acts as a read-only context object for accessing runtime properties and
 * platform-specific constants.
 *
 * @author mlipper
 */
public class JniContext {

    /**
     * Creates a new instance.
     */
    public JniContext() {
    }

    static final Logger logger = LoggerFactory.getLogger(JniContext.class);

    /**
     * Version used to namespace the extracted native library when the
     * <code>gc.jni.version</code> system property is not set.
     */
    public static final String DEFAULT_GC_JNI_VERSION = "geoclient-jni-2";

    /**
     * Name of the Java system property that specifies the directory to which the
     * native library is extracted.
     */
    public static final String GC_JNI_EXTRACT_DIR_PROPERTY = "gc.jni.extract.dir";

    /**
     * Name of the environment variable that specifies the directory to which the
     * native library is extracted. Used only if the corresponding system property
     * is not set.
     */
    public static final String GC_JNI_EXTRACT_DIR_ENV_VAR = "GC_JNI_EXTRACT_DIR";
    private static final String GC_SHAREDLIB_BASENAME = "geoclientjni";
    private static final String GC_PACKAGE_PATH = JniContext.class.getPackage().getName().replaceAll("\\.", "\\/");

    private enum SystemProperty {

        GC_JNI_VERSION("gc.jni.version"),
        GC_JNI_EXTRACT_DIR(GC_JNI_EXTRACT_DIR_PROPERTY),
        JAVA_IO_TMPDIR("java.io.tmpdir"),
        JAVA_LIBRARY_PATH("java.library.path");

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

    static String getEnvironmentVariable(String envVar, String defaultValue) {
        String value = System.getenv(envVar);
        if (value != null) {
            logger.info("Using environment variable {} with value {}.", envVar, value);
            return value;
        }
        if (defaultValue != null) {
            logger.info("Environment variable {} is not set. Using default value {}.", envVar, defaultValue);
            return defaultValue;
        }
        logger.warn("Environment variable {} is not set and has no default value.", envVar);
        return null;
    }

    static String resolveNativeExtractDir(String extractDirSystemProperty, String extractDirEnvironmentVariable,
            String javaIoTmpDir) {
        if (extractDirSystemProperty != null) {
            return extractDirSystemProperty;
        }
        if (extractDirEnvironmentVariable != null) {
            return extractDirEnvironmentVariable;
        }
        return javaIoTmpDir;
    }

    /**
     * Returns the value of the <code>gc.jni.version</code> system property.
     *
     * @return the property value or {@link #DEFAULT_GC_JNI_VERSION} if not set
     */
    public static String getGeoclientJniVersion() {
        return JniContext.getSystemProperty(SystemProperty.GC_JNI_VERSION, DEFAULT_GC_JNI_VERSION);
    }

    /**
     * Returns the value of the <code>java.io.tmpdir</code> system property.
     *
     * @return the JVM temporary directory path or <code>null</code> if not set
     */
    public static String getJvmTempDir() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_IO_TMPDIR, null);
    }

    /**
     * Returns the value of the <code>java.library.path</code> system property.
     *
     * @return the JVM native library search path or <code>null</code> if not set
     */
    public static String getJvmLibraryPath() {
        return JniContext.getSystemProperty(SystemProperty.JAVA_LIBRARY_PATH, null);
    }

    /**
     * Resolves the directory to which the native library is extracted. In order of
     * precedence, uses the {@value #GC_JNI_EXTRACT_DIR_PROPERTY} system property,
     * the {@value #GC_JNI_EXTRACT_DIR_ENV_VAR} environment variable and
     * finally the JVM temporary directory.
     *
     * @return the resolved extract directory path or <code>null</code> if none of
     *         the sources is set
     */
    public static String getNativeExtractDir() {
        String extractDirSystemProperty = JniContext.getSystemProperty(SystemProperty.GC_JNI_EXTRACT_DIR, null);
        String extractDirEnvironmentVariable = JniContext.getEnvironmentVariable(GC_JNI_EXTRACT_DIR_ENV_VAR, null);
        String javaIoTmpDir = JniContext.getJvmTempDir();
        String extractDir = JniContext.resolveNativeExtractDir(extractDirSystemProperty, extractDirEnvironmentVariable,
            javaIoTmpDir);
        logger.info("Resolved native library extract directory to {}.", extractDir);
        return extractDir;
    }

    /**
     * Returns the platform-independent base name of the Geoclient JNI shared
     * library, i.e., without any prefix or file extension.
     *
     * @return the shared library base name
     */
    public static String getSharedLibraryBaseName() {
        return GC_SHAREDLIB_BASENAME;
    }

    /**
     * Returns this package's name in resource path form (i.e., using
     * <code>/</code> separators), used to locate the bundled native library on the
     * classpath.
     *
     * @return the package path
     */
    public static String getJavaPackagePath() {
        return GC_PACKAGE_PATH;
    }
}
