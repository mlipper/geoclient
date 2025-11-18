package geoclientbuild.docs;

import java.util.Map;

import geoclientbuild.client.Request;

/**
 * Adapter class for {@link geoclientbuild.client.Request} 
 * that adds a {@link HttpRequestAdapter#getType()} method. 
 * 
 * @since 2.0
 * @author mlipper
 */
public class HttpRequestAdapter extends Request {

    private String type;

    public HttpRequestAdapter() {
        super();
    }

    public HttpRequestAdapter(String id, String uri, String method, Map<String, String> parameters, String type) {
        super(id, uri, method, parameters);
        this.type = type;
    }

    public HttpRequestAdapter(String type, String method) {
        this.type = type;
        this.setMethod(method);
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
