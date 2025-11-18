package geoclientbuild.client;

/**
 * A very simple representation of an HTTP response that
 * does not use HTTP libraries.
 */
public class Response {
    private final int httpCode;
    private final String body;

    /**
     * Constructs a Response object.
     * 
     * @param httpCode the HTTP status code
     * @param body the response body as a string
     */
    public Response(int httpCode, String body) {
        this.httpCode = httpCode;
        this.body = body;
    }

    /**
     * Gets the HTTP status code.
     * @return the HTTP status code
     */
    public int getHttpCode() {
        return httpCode;
    }

    /**
     * Gets the response body.
     * @return the response body as a string
     */
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Response [httpCode=" + httpCode + ", body=" + body + "]";
    }

}
