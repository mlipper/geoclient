package com.digitalclash.geoclient.jarexec.http;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ResponseTest {
    @Test
    void testConstructor() {
        Response response = new Response(200, "OK");
        assertEquals(response.getHttpCode(), 200);
        assertEquals(response.getBody(), "OK");
    }
}
