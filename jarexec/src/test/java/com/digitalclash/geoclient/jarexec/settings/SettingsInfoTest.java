package com.digitalclash.geoclient.jarexec.settings;

import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.COMMAND_LINE_SECTION_TITLE;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.DEFAULT_MAP_SUMMARY;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.ENV_SECTION_TITLE;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.HTTP_SHUTDOWN_SECTION_TITLE;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.WORKING_DIR_SECTION_TITLE;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.appendInfoSection;
import static com.digitalclash.geoclient.jarexec.settings.SettingsInfo.httpShutdownInfo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.digitalclash.geoclient.jarexec.settings.Settings;
import com.digitalclash.geoclient.jarexec.settings.SettingsInfo;

public class SettingsInfoTest extends AbstractSettingsTest {

    @Test
    void testInfo() {
        Settings settingsFixture = settingsFixture();
        
        // Create expected info manually using static methods
        StringBuilder txt = new StringBuilder();
        appendInfoSection(txt, ENV_SECTION_TITLE, DEFAULT_MAP_SUMMARY.execute(settingsFixture.getEnvironment()));
        appendInfoSection(txt, WORKING_DIR_SECTION_TITLE, settingsFixture.getWorkingDirectory().getAbsolutePath());
        appendInfoSection(txt, COMMAND_LINE_SECTION_TITLE, settingsFixture.commandLine());
        appendInfoSection(txt, HTTP_SHUTDOWN_SECTION_TITLE, httpShutdownInfo(settingsFixture));
        String expectedInfo = txt.toString();

        // Get actual info from SettingsInfo created from fixture
        String actualInfo = new SettingsInfo(settingsFixture).info();
        assertEquals(expectedInfo, actualInfo);
        showInfo();
    }

    void showInfo() {
        Settings settings = settingsFixture();
        System.out.println("Real file:");
        System.out.println(settings.settings());

        settings.setHttpShutdownFile(new File("missing-file.json"));
        System.out.println("Missing file:");
        System.out.println(settings.settings());
    }
}
