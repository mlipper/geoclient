package gov.nyc.doitt.gis.geoclient.service.web.jackson;

import org.springframework.boot.jackson.JacksonMixin;
import com.fasterxml.jackson.annotation.JsonRootName;
import gov.nyc.doitt.gis.geoclient.api.GeosupportResponse;

/**
 * Mixin class for GeosupportResponse serialization/deserialization.
 */
@JacksonMixin(GeosupportResponse.class)
@JsonRootName("geosupportResponse")
abstract class GeosupportResponseMixin {

}
