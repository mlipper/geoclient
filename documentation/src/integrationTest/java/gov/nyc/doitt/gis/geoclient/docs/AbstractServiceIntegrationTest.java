package gov.nyc.doitt.gis.geoclient.docs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AbstractServiceIntegrationTest {

    private static Process process;

    @BeforeAll
    public static void setUpAll() throws Exception {
        process = new ServiceExecutor().startServer();
    }

    @AfterAll
    public static void tearDownAll() {
        new ServiceExecutor().stopServer(process);
    }

}
