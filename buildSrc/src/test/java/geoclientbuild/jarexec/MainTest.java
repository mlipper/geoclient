package geoclientbuild.jarexec;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import geoclientbuild.jarexec.settings.Settings;

class MainTest {
    @Test void appHasAGreeting() {
        Main classUnderTest = new Main(new Settings());
        assertNotNull(classUnderTest.getSettings(), "app should have settings");
    }
}
