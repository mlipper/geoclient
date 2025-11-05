package com.digitalclash.geoclient.jarexec;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.digitalclash.geoclient.jarexec.Main;
import com.digitalclash.geoclient.jarexec.settings.Settings;

class MainTest {
    @Test void appHasAGreeting() {
        Main classUnderTest = new Main(new Settings());
        assertNotNull(classUnderTest.getSettings(), "app should have settings");
    }
}
