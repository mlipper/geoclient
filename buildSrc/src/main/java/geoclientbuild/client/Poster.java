package geoclientbuild.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;

public class Poster {

    public static final Map<String, String> DEFAULT_HEADERS = Map.of(
        "Content-Type", "application/x-www-form-urlencoded",
        "Accept", "application/json");
    public static final Map<String, String> DEFAULT_PARAMETERS = Collections.emptyMap();

    /**
     * Performs an HTTP POST request to the specified URL with given parameters and headers.
     * Suitable for a typical form submission.
     * 
     * @param url
     * @param parameters
     * @param httpHeaders
     * @return String response content
     * @throws Exception
     */
    public String post(String url,
        Map<String, String> parameters,
        Map<String, String> httpHeaders) throws Exception {

        List<NameValuePair> formParams = createFormParams(parameters);
        HttpPost httpPost = createHttpPost(url, formParams);
        // Add headers (optional)
        addHttpHeaders(httpPost, httpHeaders);
        return doPost(httpPost); 
    }

    /**
     * Performs an HTTP POST request to the specified URI without parameters or headers.
     * Suitable for endpoints that do not require a request body.
     * 
     * @param uri
     * @return String response content
     * @throws Exception
     */
    public String post(URI uri) throws Exception {
        return doPost(new HttpPost(uri));
    }

    private String doPost(HttpPost httpPost) throws Exception {
        final StringBuffer content = new StringBuffer();
        try (CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httpPost, httpResponse -> {
                    content.append(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                    return httpResponse;
                })) {
            return content.toString();
        }
    }

    private void addHttpHeaders(HttpPost httpPost, Map<String, String> httpHeaders) {
        if(httpHeaders != null && !httpHeaders.isEmpty()) {
            httpHeaders.forEach((k, v) -> httpPost.addHeader(k, v));
        }
    }

    private HttpPost createHttpPost(String url, List<NameValuePair> parameters) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));
        return httpPost;
    }

    private List<NameValuePair> createFormParams(Map<String, String> parameters) {
        List<NameValuePair> formParams = new ArrayList<>();
        if(parameters == null || parameters.isEmpty()) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return formParams;
    }
}