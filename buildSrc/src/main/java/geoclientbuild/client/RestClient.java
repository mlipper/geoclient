package geoclientbuild.client;

import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.gradle.api.logging.Logging;
import org.gradle.api.logging.Logger;

public class RestClient {

    private final Logger logger = Logging.getLogger(this.getClass());

    private Endpoints endpoints;
    //private HttpClientManager httpClientManager;
    private Map<String, String> httpHeaders;

    public RestClient(String baseUri, Map<String, String> httpHeaders) {
        //httpClientManager = new DefaultHttpClientManager();
        this.endpoints = new Endpoints(baseUri);
        this.httpHeaders = httpHeaders;
    }

    public String call(Request request) throws Exception {
        if (!this.endpoints.contains(request.getType())) {
            return String.format(Endpoints.RC_NOT_IMPLEMENTED_JSON_TEMPLATE, request.getType());
        }
        Endpoint endpoint = endpoints.get(request.getType());
        HttpGet httpGet = request.hasParameters() ? endpoint.httpGetRequest(request.getParameters()) : endpoint.httpGetRequest();
        addHttpHeaders(httpGet);
        logger.lifecycle("Calling REST endpoint: " + httpGet.getUri());
        HttpClient client = new HttpClient() {
            
        };
        Response response = client.execute(httpGet);
        return response.getBody();
        //return call(httpGet);
    }

    private void addHttpHeaders(HttpGet httpGet) {
        if(httpHeaders != null && !httpHeaders.isEmpty()) {
            httpHeaders.forEach((k, v) -> httpGet.addHeader(k, v));
        }
    }

    private String call(HttpGet httpGet) throws Exception {
        final StringBuffer content = new StringBuffer();
        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response =
                (CloseableHttpResponse) client.execute(httpGet, httpResponse -> {
                    content.append(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                    logger.lifecycle("Response status: " + httpResponse.getCode());
                    return httpResponse;
                })) {
            return content.toString();
        }
    }
}
