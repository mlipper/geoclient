package gov.nyc.doitt.gis.geoclient.service.search.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractPolicyTest {
    private static class TestAbstractPolicy extends AbstractPolicy {

        @Override
        public String getDescription() {
            return "This is a test.";
        }
    };

    private TestAbstractPolicy instance;

    @BeforeEach
    public void beforeEach() {
        this.instance = new TestAbstractPolicy();
    }

    @Test
    void testGetDescription() {
        assertEquals("This is a test.", instance.getDescription());
    }

    @Test
    void testGetName() {
        assertEquals("TestAbstractPolicy", this.instance.getName());
    }
}
