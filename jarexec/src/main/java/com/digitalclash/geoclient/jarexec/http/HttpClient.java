package com.digitalclash.geoclient.jarexec.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple {@link RestClient} implementation using Apache HttpClient.
* @see com.digitalclash.geoclient.jarexec.http.RestClient
 */
public class HttpClient implements RestClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*
     * @see RestClient#call(com.digitalclash.geoclient.samples.Request)
     */
    @Override
    public Response call(Request request) throws Exception {
        ClassicHttpRequest httpRequest = null;
        switch (request.getMethod()) {
            case Request.HTTP_GET_METHOD:
                httpRequest = buildGetRequest(request);    
                break;
            case Request.HTTP_HEAD_METHOD:
                httpRequest = buildHeadRequest(request);    
                break;
            case Request.HTTP_POST_METHOD:
                httpRequest = buildPostRequest(request);    
                break;
            default:
                throw new IllegalArgumentException(String.format(Request.UNSUPPORTED_HTTP_METHOD_EXCEPTION_TEMPLATE, request.getMethod()));
        }
        return execute(httpRequest);
    }

    protected HttpGet buildGetRequest(Request request) throws URISyntaxException {
        HttpGet httpGet = null;
        URI endpoint = new URI(request.getUri());
        if (request.hasParameters() && request.hasHeaders()) {
            httpGet = httpGetRequest(endpoint, request.getParameters(), request.getHeaders());
        } else if (request.hasParameters()) {
            httpGet = httpGetRequest(endpoint, request.getParameters());
        } else {
            httpGet = httpGetRequest(endpoint);
            if (request.hasHeaders()) {
                addHeaders(httpGet, request.getHeaders());
            }
        }
        return httpGet;
    }

    protected HttpHead buildHeadRequest(Request request) throws URISyntaxException {
        HttpHead httpHead = null;
        URI endpoint = new URI(request.getUri());
        if (request.hasParameters() && request.hasHeaders()) {
            httpHead = httpHeadRequest(endpoint, request.getParameters(), request.getHeaders());
        } else if (request.hasParameters()) {
            httpHead = httpHeadRequest(endpoint, null, request.getHeaders());
        } else {
            httpHead = httpHeadRequest(endpoint, null, null);
            if (request.hasHeaders()) {
                addHeaders(httpHead, request.getHeaders());
            }
        }
        return httpHead;
    }

    protected HttpPost buildPostRequest(Request request) throws URISyntaxException {
        HttpPost httpPost = null;
        URI endpoint = new URI(request.getUri());
        if (request.hasParameters() && request.hasHeaders()) {
            httpPost = httpPostRequest(endpoint, request.getParameters(), request.getHeaders());
        } else if (request.hasParameters()) {
            httpPost = httpPostRequest(endpoint, null, request.getHeaders());
        } else {
            httpPost = httpPostRequest(endpoint, null, null);
            if (request.hasHeaders()) {
                addHeaders(httpPost, request.getHeaders());
            }
        }
        return httpPost;
    }

    protected Response execute(ClassicHttpRequest httpRequest) throws Exception {
        logger.info("Executing HTTP request: " + httpRequest.getUri());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                httpClient.execute(httpRequest, httpResponse -> {
                int status = httpResponse.getCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                logger.info("Response Status: " + status);
                logger.debug("Response Body: " + responseBody);
                return new Response(status, responseBody);
            });
        }
        return null;
    }

    protected Logger getLogger() {
        return logger;
    }

    protected HttpGet httpGetRequest(URI uri, Map<String, String> parameters, Map<String, String> httpHeaders) throws URISyntaxException {
        return buildHttpGet(uri, parameters, httpHeaders);
    }

    protected HttpGet httpGetRequest(URI uri, Map<String, String> parameters) throws URISyntaxException {
        return httpGetRequest(uri, parameters, null);
    }

    protected HttpGet httpGetRequest(URI uri) {
        return buildHttpGet(uri);
    }

    protected HttpHead httpHeadRequest(URI uri, Map<String, String> parameters, Map<String, String> httpHeaders) throws URISyntaxException {
        return buildHttpHead(uri, parameters, httpHeaders);
    }

    protected HttpPost httpPostRequest(URI uri, Map<String, String> parameters, Map<String, String> httpHeaders) throws URISyntaxException {
        return buildHttpPost(uri, parameters, httpHeaders);
    }

    protected ClassicHttpRequest addHeaders(ClassicHttpRequest request, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> request.addHeader(k, v));
        }
        return request;
    }

    private List<NameValuePair> params(Map<String, String> parameters) {
        List<NameValuePair> params = new ArrayList<>();
        parameters.forEach((k, v) -> params.add(new BasicNameValuePair(k, v)));
        return params;
    }

    private HttpGet buildHttpGet(URI uri, Map<String, String> parameters, Map<String, String> headers) throws URISyntaxException {
        HttpGet httpGet = buildHttpGet(uri);
        URI targetUri = new URIBuilder(httpGet.getUri()).addParameters(params(parameters)).build();
        httpGet.setUri(targetUri);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> httpGet.addHeader(k, v));
        }
        return httpGet;
    }

    private HttpGet buildHttpGet(URI uri) {
        return new HttpGet(uri);
    }

    private HttpHead buildHttpHead(URI uri, Map<String, String> parameters, Map<String, String> headers) throws URISyntaxException {
        HttpHead httpHead = new HttpHead(uri);
        URI targetUri = new URIBuilder(httpHead.getUri()).addParameters(params(parameters)).build();
        httpHead.setUri(targetUri);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> httpHead.addHeader(k, v));
        }
        return httpHead;
    }

    private HttpPost buildHttpPost(URI uri, Map<String, String> parameters, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(uri);
        if (parameters != null && !parameters.isEmpty()) {
            httpPost.setEntity(new UrlEncodedFormEntity(params(parameters)));
        }
        if (headers != null && !headers.isEmpty()) {
            headers.forEach((k, v) -> httpPost.addHeader(k, v));
        }
        return httpPost;
    }
}
