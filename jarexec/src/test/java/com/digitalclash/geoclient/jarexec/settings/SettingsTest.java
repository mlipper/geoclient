package com.digitalclash.geoclient.jarexec.settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.digitalclash.geoclient.jarexec.http.Request;
import com.digitalclash.geoclient.jarexec.settings.Settings.Builder;

public class SettingsTest extends AbstractSettingsTest {

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
        Settings settings = builder
            .withJarFile(jarFile)
            .withArguments(arguments)
            .withEnvironment(environment)
            .withHttpShutdownFile(httpShutdownFile)
            .build();
        Settings fixture = settingsFixture();
        assertEquals(fixture.getJarFile(), settings.getJarFile(), "Jar files should match");
        assertEquals(fixture.getArguments(), settings.getArguments(), "Arguments should match");
        assertEquals(fixture.getEnvironment(), settings.getEnvironment(), "Environments should match");
        assertEquals(fixture.httpShutdown().settings(), settings.httpShutdown().settings(), "HTTP shutdown settings should match");
    }

    @Test
    void testCommandLineAsList_noArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        assertEquals(3, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2), "Third command line element should match");
    }

    @Test
    void testCommandLineAsList_customJavaNoArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        settings.setJavaCommand("/usr/local/java");
        assertEquals(3, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("/usr/local/java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2), "Third command line element should match");
    }

    @Test
    void testCommandLineAsList_withArgs() {
        Settings settings = new Settings();
        settings.setJarFile(jarFile);
        settings.setArguments(arguments);
        assertEquals(6, settings.commandLineAsList().size(), "Command line list size should match");
        assertEquals("java", settings.commandLineAsList().get(0), "First command line element should match");
        assertEquals("-jar", settings.commandLineAsList().get(1), "Second command line element should match");
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2), "Third command line element should match");
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
        assertEquals("/app/example.jar", settings.commandLineAsList().get(2), "Third command line element should match");
        assertEquals("arg1", settings.commandLineAsList().get(3), "Fourth command line element should match");
        assertEquals("arg2", settings.commandLineAsList().get(4), "Fifth command line element should match");
        assertEquals("arg3", settings.commandLineAsList().get(5), "Sixth command line element should match");
    }

    @Test
    void testSupportsHttpShutdown() {
        Settings settings = new Settings();
        assertEquals(false, settings.supportsHttpShutdown(), "Expected supportsHttpShutdown to be false when httpShutdownFile is null");
        settings.setHttpShutdownFile(new File("fake-file.json"));
        assertEquals(false, settings.supportsHttpShutdown(), "Expected supportsHttpShutdown to be false when httpShutdownFile is missing");
        settings.setHttpShutdownFile(httpShutdownFile);
        assertEquals(true, settings.supportsHttpShutdown(), "Expected supportsHttpShutdown to be true when httpShutdownFile exists and is valid");
    }

    @Test
    void testCreateHttpShutdownRequest() {
        Settings settings = new Settings();
        settings.setHttpShutdownFile(httpShutdownFile);
        assertEquals(true, settings.supportsHttpShutdown(), "Expected supportsHttpShutdown to be true when httpShutdownFile exists and is valid");
        HttpShutdown httpShutdown = settings.httpShutdown();
        Request request = settings.createHttpShutdownRequest();
        assertNotNull(request.getId());
        assertEquals(httpShutdown.getUrl(), request.getUri(), "Request URL should match HttpShutdown URL");
        assertEquals(httpShutdown.getHttpMethod(), request.getMethod(), "Request method should match HttpShutdown method");
        assertEquals(httpShutdown.getParameters(), request.getParameters(), "Request parameters should match HttpShutdown parameters");
        assertEquals(httpShutdown.getHeaders(), request.getHeaders(), "Request headers should match HttpShutdown headers");
    }
}
