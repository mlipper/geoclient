package geoclientbuild.client;

/**
 * A very simple REST client interface for making HTTP requests.
 * This interface defines a method to send a request and receive a response
 * without relying on HTTP-specific libraries.
 * 
 * It intentionally provides a minimal abstraction over HTTP communication, limiting
 * the functionality to just sending HTTP GET, POST, and HEAD requests with the ability
 * to set headers and parameters and read response status and body.
 *
 * @author mlipper
 */
public interface RestClient {

    /**
     * Makes an HTTP request and returns the response.
     * @param request
     * @return response
     * @throws Exception
     */
    Response call(Request request) throws Exception;
}
