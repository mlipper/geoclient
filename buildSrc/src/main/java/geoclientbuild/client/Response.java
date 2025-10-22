package geoclientbuild.client;

public class Response {

    private final int statusCode;
    private final String body;

    public Response(int statusCode, String body) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
