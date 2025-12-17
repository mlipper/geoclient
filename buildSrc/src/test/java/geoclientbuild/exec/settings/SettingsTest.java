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
package geoclientbuild.exec.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import geoclientbuild.exec.settings.Settings.Builder;

public class SettingsTest extends BaseSettingsTest {

    @Test
    void testCommandLine() {
        Settings settings = new Settings();
        assertThrows(NullPointerException.class, () -> {
            settings.commandLine();
        }, "Expected NullPointerException when jarFile is null");
        settings.setJarFile(jarFile);
        assertEquals("java -jar /app/example.jar", settings.commandLine(), "Command line should match");
        settings.setArguments(arguments);
        assertEquals("java -jar /app/example.jar arg1 arg2 arg3", settings.commandLine(), "Command line should match");
    }

    @Test
    void testBuilder() {
        Builder builder = Settings.builder();
        Settings settings = builder.withJarFile(jarFile).withJavaCommand(javaCommand).withArguments(
            arguments).withEnvironment(environment)
                .withSleepSecondsAfterStart(sleepSecondsAfterStart).withSleepSecondsAfterStop(sleepSecondsAfterStop).build();
        Settings fixture = settingsFixture();
        assertEquals(fixture.getJarFile(), settings.getJarFile(), "Jar files should match");
        assertEquals(fixture.getJavaCommand(), settings.getJavaCommand(), "Java command should match");
        assertEquals(fixture.getArguments(), settings.getArguments(), "Arguments should match");
        assertEquals(fixture.getEnvironment(), settings.getEnvironment(), "Environments should match");
        assertEquals(fixture.getSleepSecondsAfterStart(), settings.getSleepSecondsAfterStart(),
            "Sleep seconds after start should match");
        assertEquals(fixture.getSleepSecondsAfterStop(), settings.getSleepSecondsAfterStop(),
            "Sleep seconds after stop should match");
    }

    @Test
    void testCommandLineAsList_noArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        assertEquals(3, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2),
            "Third command line element should match");
    }

    @Test
    void testCommandLineAsList_customJavaNoArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        settings.setJavaCommand("/usr/local/java");
        assertEquals(3, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("/usr/local/java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2),
            "Third command line element should match");
    }

    @Test
    void testCommandLineAsList_withArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        settings.setArguments(arguments);
        assertEquals(6, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2),
            "Third command line element should match");
        assertEquals("arg1", settings.commandLineAsList().get(3), "Fourth command line element should match");
        assertEquals("arg2", settings.commandLineAsList().get(4), "Fifth command line element should match");
        assertEquals("arg3", settings.commandLineAsList().get(5), "Sixth command line element should match");
    }

    @Test
    void testCommandLineAsList_customJavaWithArgs() {
        Settings settings = new Settings();
        settings.setJavaCommand("/usr/local/java");
        settings.setJarFile(jarFile);
        settings.setArguments(arguments);
        assertEquals(6, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("/usr/local/java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2),
            "Third command line element should match");
        assertEquals("arg1", settings.commandLineAsList().get(3), "Fourth command line element should match");
        assertEquals("arg2", settings.commandLineAsList().get(4), "Fifth command line element should match");
        assertEquals("arg3", settings.commandLineAsList().get(5), "Sixth command line element should match");
    }
}
