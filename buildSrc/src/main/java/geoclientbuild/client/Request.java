package geoclientbuild.client;

import java.util.List;
import java.util.Map;

/**
 * Represents a request to be sent to a web service.
 */
public class Request {

    public static final Map<String, String> ACCEPT_HEADER_ANY = Map.of("Accept", "*/*");
    public static final Map<String, String> ACCEPT_HEADER_JSON = Map.of("Accept", "application/json");
    public static final Map<String, String> CONTENT_TYPE_HEADER_FORM_URLENCODED = Map.of("Content-Type", "application/x-www-form-urlencoded");
    public static final String HTTP_GET_METHOD = "GET";
    public static final String HTTP_HEAD_METHOD = "HEAD";
    public static final String HTTP_POST_METHOD = "POST";
    public static final String HTTP_METHOD_DEFAULT = HTTP_GET_METHOD;
    public static final List<String> SUPPORTED_HTTP_METHODS = List.of(HTTP_GET_METHOD, HTTP_POST_METHOD, HTTP_HEAD_METHOD);
    public static final String UNSUPPORTED_HTTP_METHOD_EXCEPTION_TEMPLATE = "Unsupported HTTP method: %s";

    private String id;
    private String uri;
    private String method;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    /**
     * Creates an empty Request.
     *
     */
    public Request() {
        this(null, null, HTTP_METHOD_DEFAULT, null, null);
    }

    /**
     * Creates a Request with a uninque id, an endpoint uri, an HTTP method, parameters and headers.
     *
     * @param id
     * @param uri
     * @param method
     * @param parameters
     * @param headers
     */
    public Request(String id, String uri, String method, Map<String, String> parameters ,Map<String, String> headers) {
        this.id = id;
        this.uri = uri;
        this.method = (method != null ? method : HTTP_METHOD_DEFAULT);
        if(method != null && !SUPPORTED_HTTP_METHODS.contains(method)) {
            throw new IllegalArgumentException(String.format(UNSUPPORTED_HTTP_METHOD_EXCEPTION_TEMPLATE, method));
        }
        this.parameters = parameters;
        this.headers = headers;
    }

    /**
     * Creates a Request with a uninque id, a target endpoint uri and parameters.
     * 
     * @param id
     * @param uri
     * @param method
     * @param parameters
     */
    public Request(String id, String uri, String method, Map<String, String> parameters) {
        this(id, uri, method, parameters, null);
    }

    /**
     * @return the headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * @return boolean indicating if the request has headers
     */
    public boolean hasHeaders() {
        return headers != null && !headers.isEmpty();
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the HTTP method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * @return boolean indicating if the request has parameters
     */
    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", uri=" + uri + ", method=" + method + ", parameters=" + parameters + ", headers="
                + headers + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        result = prime * result + ((headers == null) ? 0 : headers.hashCode());
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
        Request other = (Request) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        if (parameters == null) {
            if (other.parameters != null)
                return false;
        } else if (!parameters.equals(other.parameters))
            return false;
        if (headers == null) {
            if (other.headers != null)
                return false;
        } else if (!headers.equals(other.headers))
            return false;
        return true;
    }

}
