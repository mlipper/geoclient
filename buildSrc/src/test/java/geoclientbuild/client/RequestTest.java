package geoclientbuild.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestTest {

    private Map<String, String> headers;
    private Map<String, String> headersEmpty;
    private Map<String, String> parameters;
    private Map<String, String> parametersEmpty;

    @BeforeEach
    void setUp() {
        headers = Map.of("Accept", "application/json");
        headersEmpty = Map.of();
        parameters = Map.of("param1", "value1", "param2", "value2");
        parametersEmpty = Map.of();
    }

    @AfterEach
    void tearDown() {
        headers = null;
        headersEmpty = null;
        parameters = null;
        parametersEmpty = null;
    }

    @Test
    void testHasHeaders() {
        assertTrue(new Request(null, null, null, null, headers).hasHeaders());
        Request request = new Request();
        assertFalse(request.hasHeaders());
        request.setHeaders(headers);
        assertTrue(request.hasHeaders());
        Request requestWithEmptyHeaders = new Request(null, null, null, null, headersEmpty);
        assertFalse(requestWithEmptyHeaders.hasHeaders());
        assertFalse(new Request(null, null, null, null).hasHeaders());
    }

    @Test
    void testHasParameters() {
        assertTrue(new Request(null, null, null, parameters, null).hasParameters());
        assertTrue(new Request(null, null, null, parameters).hasParameters());
        Request request = new Request();
        assertFalse(request.hasParameters());
        request.setParameters(parameters);
        assertTrue(request.hasParameters());
        Request requestWithEmptyParameters = new Request(null, null, null, parametersEmpty);
        assertFalse(requestWithEmptyParameters.hasParameters());
        assertFalse(new Request(null, null, null, null).hasParameters());
    }

    @Test
    void testConstructor_noArguments() {
        Request request = new Request();
        assertNull(request.getId());
        assertNull(request.getUri());
        assertNull(request.getParameters());
        assertNull(request.getHeaders());
        assertTrue(request.getMethod().equals(Request.HTTP_METHOD_DEFAULT));
    }

    @Test
    void testConstructor_parametersArgument() {
        Request request = new Request("1", "http://example.com", Request.HTTP_HEAD_METHOD, parameters);
        assertEquals(request.getId(), "1");
        assertEquals(request.getUri(), "http://example.com");
        assertEquals(request.getParameters(), parameters);
        assertNull(request.getHeaders());
        assertEquals(request.getMethod(), Request.HTTP_HEAD_METHOD);
    }

    @Test
    void testConstructor_parametersAndHeadersArguments() {
        Request request = new Request("1", "http://example.com", Request.HTTP_HEAD_METHOD, parameters, headers);
        assertEquals(request.getId(), "1");
        assertEquals(request.getUri(), "http://example.com");
        assertEquals(request.getParameters(), parameters);
        assertEquals(request.getHeaders(), headers);
        assertEquals(request.getMethod(), Request.HTTP_HEAD_METHOD);
    }

    @Test
    void testConstructor_unsupportedMethod() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            new Request("1", "http://example.com", "PUT", parameters, headers);
        });
        assertEquals(e.getMessage(), String.format(Request.UNSUPPORTED_HTTP_METHOD_EXCEPTION_TEMPLATE, "PUT"));
    }
}
