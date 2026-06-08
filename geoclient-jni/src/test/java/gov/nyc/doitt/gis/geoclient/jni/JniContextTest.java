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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class JniContextTest {

    private String existingExtractDir;

    @AfterEach
    void afterEach() {
        if (this.existingExtractDir == null) {
            System.clearProperty(JniContext.GC_JNI_EXTRACT_DIR_PROPERTY);
        }
        else {
            System.setProperty(JniContext.GC_JNI_EXTRACT_DIR_PROPERTY, this.existingExtractDir);
        }
    }

    @Test
    void resolveNativeExtractDirPrefersSystemProperty() {
        String result = JniContext.resolveNativeExtractDir("/tmp/from-system-property", "/tmp/from-env",
            "/tmp/default");
        assertEquals("/tmp/from-system-property", result);
    }

    @Test
    void resolveNativeExtractDirUsesEnvironmentVariableWhenSystemPropertyIsMissing() {
        String result = JniContext.resolveNativeExtractDir(null, "/tmp/from-env", "/tmp/default");
        assertEquals("/tmp/from-env", result);
    }

    @Test
    void resolveNativeExtractDirFallsBackToJavaIoTmpdir() {
        String result = JniContext.resolveNativeExtractDir(null, null, "/tmp/default");
        assertEquals("/tmp/default", result);
    }

    @Test
    void getNativeExtractDirUsesSystemPropertyWhenConfigured() {
        this.existingExtractDir = System.getProperty(JniContext.GC_JNI_EXTRACT_DIR_PROPERTY);
        System.setProperty(JniContext.GC_JNI_EXTRACT_DIR_PROPERTY, "/tmp/from-system-property");
        String result = JniContext.getNativeExtractDir();
        assertEquals("/tmp/from-system-property", result);
    }
}
