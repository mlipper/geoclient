package geoclientbuild.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;

public class Endpoint {

    private final String name;
    private final String baseUri;

    /**
     * Shared endpoint request configuration.
     *
     * @param name
     * @param baseUri
     */
    public Endpoint(String name, String baseUri) {
        this.name = name;
        this.baseUri = baseUri;
    }

    public HttpGet httpGetRequest(Map<String, String> parameters, Map<String, String> httpHeaders) throws URISyntaxException {
        return buildHttpGet(parameters, httpHeaders);
    }

    public HttpGet httpGetRequest(Map<String, String> parameters) throws URISyntaxException {
        return httpGetRequest(parameters, null);
    }

    public HttpGet httpGetRequest() {
        return buildHttpGet();
    }

    public String getName() {
        return name;
    }

    private List<NameValuePair> params(Map<String, String> parameters) {
        List<NameValuePair> params = new ArrayList<>();
        parameters.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
        return params;
    }

    private HttpGet buildHttpGet(Map<String, String> parameters, Map<String, String> headers) throws URISyntaxException {
        HttpGet httpGet = buildHttpGet();
        URI uri = new URIBuilder(httpGet.getUri()).addParameters(params(parameters)).build();
        httpGet.setUri(uri);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> httpGet.addHeader(k, v));
        }
        return httpGet;
    }

    private HttpGet buildHttpGet() {
        return new HttpGet(this.baseUri + "/" + this.name);
    }

    private HttpPost buildHttpPost(Map<String, String> parameters, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(this.baseUri + "/" + this.name);
        if (parameters != null && !parameters.isEmpty()) {
            httpPost.setEntity(new UrlEncodedFormEntity(params(parameters)));
        }
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> httpPost.addHeader(k, v));
        }
        return httpPost;
    }

    private HttpPost buildHttpPost() {
        return buildHttpPost(null, null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((baseUri == null) ? 0 : baseUri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Endpoint other = (Endpoint) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (baseUri == null) {
            if (other.baseUri != null)
                return false;
        } else if (!baseUri.equals(other.baseUri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Endpoint [" + name + ": " + baseUri + "/" + name + "]";
    }

}
