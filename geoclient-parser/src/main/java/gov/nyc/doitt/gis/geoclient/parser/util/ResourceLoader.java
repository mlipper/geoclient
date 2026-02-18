/*
 * Copyright 2013-2026 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.parser.util;

import java.io.InputStream;
import java.util.Properties;

public class ResourceLoader {

    /**
     * Loads a {@link Properties} file from the classpath. The provided filename
     * should be relative to the classpath root and should not include a leading
     * slash.
     * <p>
     * For example, if the properties file is located at
     * {@code src/main/resources/config/app.properties}, the filename should be
     * {@code "config/app.properties"}.
     * <p>
     * Because this method uses {@code getResourceAsStream}, it will work
     * correctly when the application is packaged as a JAR, as long as the
     * properties file is included in the JAR's classpath.
     *
     * @param filename the name of the properties file to load, relative to
     *        the classpath root (without a leading slash)
     * @return the loaded properties
     * @throws RuntimeException if the properties file cannot be loaded
     */
    public Properties classpathProperties(String filename) {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/" + filename));
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load properties from classpath resource: " + filename, e);
        }
        return properties;
    }

    /**
     * Loads a resource file from the classpath as an {@link InputStream}. The provided filename should be relative to the classpath root and should not
     * include a leading slash.
     * <p>
     * For example, if the file is located at
     * {@code src/main/resources/config/foo.xml}, the filename should be
     * {@code "config/foo.xml"}.
     * <p>
     * Because this method uses {@code getResourceAsStream}, it will work
     * correctly when the application is packaged as a JAR, as long as the
     * file is included in the JAR's classpath.
     *
     * @param filename the name of the file to load, relative to
     *        the classpath root (without a leading slash)
     * @return an InputStream for the loaded resource
     * @throws RuntimeException if the resource cannot be loaded
     * @throws NullPointerException if the filename is null
     */
    public InputStream classpathResource(String filename) {
        if (filename == null) {
            throw new NullPointerException("Filename must not be null");
        }
        InputStream inputStream = this.getClass().getResourceAsStream("/" + filename);
        if (inputStream == null) {
            throw new RuntimeException("Unable to load resource from classpath: " + filename);
        }
        return inputStream;
    }
}
