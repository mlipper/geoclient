package geoclientbuild.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Endpoints {

    public static final String ACTUATOR_SHUTDOWN = "actuator/shutdown";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_POINT= "addresspoint";
    public static final String BBL = "bbl";
    public static final String BIN = "bin";
    public static final String BLOCKFACE = "blockface";
    public static final String INTERSECTION = "intersection";
    public static final String NORMALIZE = "normalize";
    public static final String PLACE = "place";
    public static final String SEARCH = "search";
    public static final String STREETCODE = "streetcode";
    public static final String STREETCODE_B5SC = "streetcode/b5sc";
    public static final String VERSION = "version";
    public static final String READINESS = VERSION;


    public static final String RC_NOT_IMPLEMENTED_JSON_TEMPLATE = "{ \"returnCode\": \"EE\", \"message\": \"Endpoint %s has not been implemented.\" }";

    private final String baseUri;
    private final Map<String, Endpoint> endpoints = new HashMap<>();
    private final Map<String, EndpointMapping> mappings = new HashMap<>();

    /**
     * @param baseUri
     */
    public Endpoints(String baseUri) {
        this.baseUri = baseUri;
        init();
    }

    public String getBaseUri() {
        return baseUri;
    }

    public boolean contains(String name) {
        return this.endpoints.containsKey(name);
    }

    public Endpoint get(String name) {
        return this.endpoints.get(name);
    }

    public Endpoint getActuatorShutdown() {
        return endpoints.get(ACTUATOR_SHUTDOWN);
    }

    public Endpoint getAddress() {
        return endpoints.get(ADDRESS);
    }

    public Endpoint getSearch() {
        return endpoints.get(SEARCH);
    }

    public Endpoint getVersion() {
        return endpoints.get(VERSION);
    }

    private void init(){
        List<String> names = List.of(
            ACTUATOR_SHUTDOWN,
            ADDRESS,
            ADDRESS_POINT,
            BBL,
            BIN,
            BLOCKFACE,
            INTERSECTION,
            NORMALIZE,
            PLACE,
            SEARCH,
            STREETCODE,
            STREETCODE_B5SC,
            VERSION);

        for (String name : names) {
            Endpoint endpoint = new Endpoint(name, baseUri);
            endpoints.put(name, endpoint);
            switch (name) {
                case ACTUATOR_SHUTDOWN:
                    mappings.put(name, new EndpointMapping(endpoint, EndpointMapping.HTTP_POST_METHOD, null, null));
                    break;
                case READINESS:
                    mappings.put(name, new EndpointMapping(endpoint, EndpointMapping.HTTP_HEAD_METHOD, null, null));
                    break;
                default:
                    mappings.put(name, new EndpointMapping(endpoint, EndpointMapping.HTTP_GET_METHOD, null, null));
                    break;
            }
        }
    }
}
