package geoclientbuild.client;

import java.util.Map;

public class EndpointMapping {


    public static final Map<String, String> ACCEPT_HEADER_ANY = Map.of("Accept", "*/*");
    public static final Map<String, String> ACCEPT_HEADER_JSON = Map.of("Accept", "application/json");
    public static final Map<String, String> CONTENT_TYPE_HEADER_FORM_URLENCODED = Map.of("Content-Type", "application/x-www-form-urlencoded");
    public static final String HTTP_GET_METHOD = "GET";
    public static final String HTTP_HEAD_METHOD = "HEAD";
    public static final String HTTP_POST_METHOD = "POST";

    private final Endpoint endpoint;
    private final Map<String, String> headers;
    private final String method;
    private final Map<String, String> parameters;

    public EndpointMapping(Endpoint endpoint, String method, Map<String, String> parameters, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.method = method;
        this.parameters = parameters;
        this.headers = headers;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
        result = prime * result + ((headers == null) ? 0 : headers.hashCode());
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
        EndpointMapping other = (EndpointMapping) obj;
        if (endpoint == null) {
            if (other.endpoint != null)
                return false;
        } else if (!endpoint.equals(other.endpoint))
            return false;
        if (headers == null) {
            if (other.headers != null)
                return false;
        } else if (!headers.equals(other.headers))
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
        return true;
    }

}
