package geoclientbuild.docs;

import java.util.HashMap;
import java.util.Map;

public class Endpoints {

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
    public static final String RC_NOT_IMPLEMENTED_JSON_TEMPLATE = "{ \"returnCode\": \"EE\", \"message\": \"Endpoint %s has not been implemented.\" }";

    private final String baseUri;
    private final Map<String, Endpoint> endpoints = new HashMap<>();

    /**
     * @param baseUri
     */
    public Endpoints(String baseUri) {
        this.baseUri = baseUri;
        endpoints.put(ADDRESS, new Endpoint(ADDRESS, baseUri));
        endpoints.put(ADDRESS_POINT, new Endpoint(ADDRESS_POINT, baseUri));
        endpoints.put(BBL, new Endpoint(BBL, baseUri));
        endpoints.put(BIN, new Endpoint(BIN, baseUri));
        endpoints.put(BLOCKFACE, new Endpoint(BLOCKFACE, baseUri));
        endpoints.put(INTERSECTION, new Endpoint(INTERSECTION, baseUri));
        endpoints.put(NORMALIZE, new Endpoint(NORMALIZE, baseUri));
        endpoints.put(PLACE, new Endpoint(PLACE, baseUri));
        endpoints.put(SEARCH, new Endpoint(SEARCH, baseUri));
        endpoints.put(STREETCODE, new Endpoint(STREETCODE, baseUri));
        endpoints.put(STREETCODE_B5SC, new Endpoint(STREETCODE_B5SC, baseUri));
        endpoints.put(VERSION, new Endpoint(VERSION, baseUri));
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

    public Endpoint getAddress() {
        return endpoints.get(ADDRESS);
    }

    public Endpoint getSearch() {
        return endpoints.get(SEARCH);
    }

    public Endpoint getVersion() {
        return endpoints.get(VERSION);
    }


}
