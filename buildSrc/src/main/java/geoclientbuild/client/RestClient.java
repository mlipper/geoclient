package geoclientbuild.client;

import org.apache.hc.client5.http.classic.methods.HttpGet;

public class RestClient extends HttpClient {

    private Endpoints endpoints;

    public RestClient(String baseUri) {
        this.endpoints = new Endpoints(baseUri);
    }

    public String call(Request request) throws Exception {
        if (!this.endpoints.contains(request.getType())) {
            return String.format(Endpoints.RC_NOT_IMPLEMENTED_JSON_TEMPLATE, request.getType());
        }
        Endpoint endpoint = endpoints.get(request.getType());
        HttpGet httpGet = request.hasParameters() ? endpoint.httpGetRequest(request.getParameters()) : endpoint.httpGetRequest();
        getLogger().lifecycle("Calling REST endpoint: " + httpGet.getUri());
        Response response = execute(httpGet);
        return response.getBody();
    }
}
