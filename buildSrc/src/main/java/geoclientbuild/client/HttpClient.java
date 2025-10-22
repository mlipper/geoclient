package geoclientbuild.client;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public abstract class HttpClient {

    private final Logger logger = Logging.getLogger(this.getClass());

    public Response execute(ClassicHttpRequest httpRequest) throws Exception {
        logger.lifecycle("Executing HTTP request: " + httpRequest.getUri());
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                httpClient.execute(httpRequest, httpResponse -> {
                int status = httpResponse.getCode();
                String responseBody = EntityUtils.toString(httpResponse.getEntity());
                logger.lifecycle("Response Status: " + status);
                logger.debug("Response Body: " + responseBody);
                return new Response(status, responseBody);
            });
        }
        return null;
    }

    protected Logger getLogger() {
        return logger;
    }
}
