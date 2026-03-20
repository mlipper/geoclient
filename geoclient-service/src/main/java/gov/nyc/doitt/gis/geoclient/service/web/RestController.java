/*
 * Copyright 2013-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.service.web;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.nyc.doitt.gis.geoclient.service.domain.GeosupportResponse;
import gov.nyc.doitt.gis.geoclient.service.domain.ServiceType;
import gov.nyc.doitt.gis.geoclient.service.domain.Version;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;

/**
 * Handles RESTful requests for Geosupport data.
 */
@CrossOrigin
@org.springframework.web.bind.annotation.RestController
public class RestController {

    public static final String ADDRESS_URI = "/address";
    public static final String ADDRESSPOINT_URI = "/addresspoint";
    public static final String BBL_URI = "/bbl";
    public static final String BIN_URI = "/bin";
    public static final String BLOCKFACE_URI = "/blockface";
    public static final String GEOSUPPORT_URI = "/geosupport";
    public static final String INTERSECTION_URI = "/intersection";
    public static final String NORMALIZE_URI = "/normalize";
    public static final String PLACE_URI = "/place";
    public static final String STREETCODE_URI = "/streetcode";
    public static final String STREETCODE_B5SC_URI = "/streetcode/b5sc";
    public static final String VERSION_URI = "/version";

    public static final String ADDRESS_OBJ = ServiceType.ADDRESS.elementName();
    public static final String ADDRESSPOINT_OBJ = ServiceType.ADDRESSPOINT.elementName();
    public static final String BBL_OBJ = ServiceType.BBL.elementName();
    public static final String BIN_OBJ = ServiceType.BIN.elementName();
    public static final String BLOCKFACE_OBJ = ServiceType.BLOCKFACE.elementName();
    public static final String INTERSECTION_OBJ = ServiceType.INTERSECTION.elementName();
    public static final String NORMALIZE_OBJ = ServiceType.NORMALIZE.elementName();
    public static final String PLACE_OBJ = ServiceType.PLACE.elementName();
    public static final String STREETCODE_OBJ = ServiceType.STREETCODE.elementName();
    public static final String STREETCODE_B5SC_OBJ = ServiceType.STREETCODE_B5SC.elementName();

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    private GeosupportService geosupportService;

    @RequestMapping(value = ADDRESS_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse address(@RequestParam(required = false) String houseNumber,
            @RequestParam String street, @RequestParam(required = false) String borough,
            @RequestParam(required = false) String zip, @RequestParam(required = false) String f) throws Exception {
        logger.debug("address[houseNumber='{}', street='{}', borough='{}', zip='{}']", houseNumber, street, borough,
            zip);
        String format = ".NONE";
        if (f != null && !f.isEmpty()) {
            format = "." + f.toUpperCase();
        }
        logger.warn("address[{} {} {} {}]{}", houseNumber, street, borough, zip, format);
        if (borough == null && zip == null) {
            throw new MissingBoroughAndZipRequestParameters();
        }
        Map<String, Object> addressMap = new HashMap<String, Object>();
        addressMap.put(ADDRESS_OBJ, this.geosupportService.callFunction1B(houseNumber, street, borough, zip));
        return new GeosupportResponse(addressMap);
    }

    @RequestMapping(value = ADDRESSPOINT_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse addresspoint(@RequestParam(required = false) String houseNumber,
            @RequestParam String street, @RequestParam(required = false) String borough,
            @RequestParam(required = false) String zip) throws Exception {
        logger.debug("addresspoint[houseNumber='{}', street='{}', borough='{}', zip='{}']", houseNumber, street,
            borough, zip);
        if (borough == null && zip == null) {
            throw new MissingBoroughAndZipRequestParameters();
        }
        Map<String, Object> addressPointMap = new HashMap<String, Object>();
        addressPointMap.put(ADDRESSPOINT_OBJ, this.geosupportService.callFunctionAP(houseNumber, street, borough, zip));
        return new GeosupportResponse(addressPointMap);
    }

    @RequestMapping(value = PLACE_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse place(@RequestParam String name,
            @RequestParam(required = false) String borough, @RequestParam(required = false) String zip)
            throws Exception {
        logger.debug("place[name='{}', borough='{}', zip='{}']", name, borough, zip);
        if (borough == null && zip == null) {
            throw new MissingBoroughAndZipRequestParameters();
        }
        Map<String, Object> placeMap = new HashMap<String, Object>();
        placeMap.put(PLACE_OBJ, this.geosupportService.callFunction1B(null, name, borough, zip));
        return new GeosupportResponse(placeMap);
    }

    @RequestMapping(value = INTERSECTION_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse intersection(@RequestParam String crossStreetOne,
            @RequestParam String crossStreetTwo, @RequestParam String borough,
            @RequestParam(required = false) String boroughCrossStreetTwo,
            @RequestParam(required = false) String compassDirection) {
        logger.debug(
            "intersection[crossStreetOne='{}', crossStreetTwo='{}', borough='{}', boroughCrossStreetTwo='{}', compassDirection='{}']",
            crossStreetOne, crossStreetTwo, borough, boroughCrossStreetTwo, compassDirection);
        Map<String, Object> intersectionMap = new HashMap<String, Object>();
        intersectionMap.put(INTERSECTION_OBJ, this.geosupportService.callFunction2(crossStreetOne, borough,
            crossStreetTwo, boroughCrossStreetTwo, compassDirection));
        return new GeosupportResponse(intersectionMap);
    }

    @RequestMapping(value = BLOCKFACE_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse blockface(@RequestParam String onStreet,
            @RequestParam String crossStreetOne, @RequestParam String crossStreetTwo, @RequestParam String borough,
            @RequestParam(required = false) String boroughCrossStreetOne,
            @RequestParam(required = false) String boroughCrossStreetTwo,
            @RequestParam(required = false) String compassDirection) {
        logger.debug(
            "blockface[onStreet='{}',crossStreetOne='{}', crossStreetTwo='{}', borough='{}', boroughCrossStreetOne='{}', boroughCrossStreetTwo='{}', compassDirection='{}']",
            onStreet, crossStreetOne, crossStreetTwo, borough, boroughCrossStreetOne, boroughCrossStreetTwo,
            compassDirection);
        Map<String, Object> blockfaceMap = new HashMap<String, Object>();
        blockfaceMap.put(BLOCKFACE_OBJ, this.geosupportService.callFunction3(onStreet, borough, crossStreetOne,
            boroughCrossStreetOne, crossStreetTwo, boroughCrossStreetTwo, compassDirection));
        return new GeosupportResponse(blockfaceMap);
    }

    @RequestMapping(value = BBL_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse bbl(@RequestParam String borough, @RequestParam String block,
            @RequestParam String lot) {
        logger.debug("bbl[borough='{}',block='{}', lot='{}']", borough, block, lot);
        Map<String, Object> bblMap = new HashMap<String, Object>();
        bblMap.put(BBL_OBJ, this.geosupportService.callFunctionBL(borough, block, lot));
        return new GeosupportResponse(bblMap);
    }

    @RequestMapping(value = BIN_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse bin(@RequestParam String bin) {
        logger.debug("bin[bin='{}']", bin);
        Map<String, Object> binMap = new HashMap<String, Object>();
        binMap.put(BIN_OBJ, this.geosupportService.callFunctionBN(bin));
        return new GeosupportResponse(binMap);
    }

    @RequestMapping(value = NORMALIZE_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse normalize(@RequestParam String name,
            @RequestParam(required = false, defaultValue = "32") Integer length,
            @RequestParam(required = false, defaultValue = "S") String format) {
        logger.debug("normalize[name='{}',length='{}',format='{}']", name, length, format);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(NORMALIZE_OBJ, this.geosupportService.callFunctionN(name, length, format));
        return new GeosupportResponse(resultMap);
    }

    @RequestMapping(value = STREETCODE_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse streetcode(@RequestParam String streetCode,
            @RequestParam(required = false) String streetCodeTwo,
            @RequestParam(required = false) String streetCodeThree,
            @RequestParam(required = false, defaultValue = "32") Integer length,
            @RequestParam(required = false, defaultValue = "S") String format) {
        logger.debug("street[streetCode='{}',streetCodeTwo='{}',streetCodeThree='{}',length='{}',format='{}']",
            streetCode, streetCodeTwo, streetCodeThree, length, format);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(STREETCODE_OBJ,
            this.geosupportService.callStreetNameFunction(streetCode, streetCodeTwo, streetCodeThree, length, format));
        return new GeosupportResponse(resultMap);
    }

    @RequestMapping(value = STREETCODE_B5SC_URI, method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse streetcodeB5sc(@RequestParam String streetCode,
            @RequestParam(required = false) String streetCodeTwo,
            @RequestParam(required = false) String streetCodeThree,
            @RequestParam(required = false, defaultValue = "32") Integer length,
            @RequestParam(required = false, defaultValue = "S") String format) {
        logger.debug("street[streetCode='{}',streetCodeTwo='{}',streetCodeThree='{}',length='{}',format='{}']",
            streetCode, streetCodeTwo, streetCodeThree, length, format);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put(STREETCODE_B5SC_OBJ,
            this.geosupportService.callFunctionD(streetCode, streetCodeTwo, streetCodeThree, length, format));
        return new GeosupportResponse(resultMap);
    }

    @RequestMapping(value = GEOSUPPORT_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody GeosupportResponse geosupport(@RequestParam Map<String, Object> params) {
        logger.debug("geosupport[{}]", params);
        return new GeosupportResponse(this.geosupportService.callGeosupport(params));
    }

    @RequestMapping(value = "/meta/{action}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody Version meta(@PathVariable String action) {
        return this.geosupportService.version();
    }

    @RequestMapping(value = VERSION_URI, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public @ResponseBody Version version(@RequestParam(required = false) String f) {
        return this.geosupportService.version();
    }

    public void setGeosupportService(GeosupportService geosupportService) {
        this.geosupportService = geosupportService;
    }
}
