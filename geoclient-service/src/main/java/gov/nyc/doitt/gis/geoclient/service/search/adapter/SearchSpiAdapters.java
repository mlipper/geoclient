/*
 * Copyright 2013-2026 the original author or authors.
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
package gov.nyc.doitt.gis.geoclient.service.search.adapter;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.search.spi.GeosupportInvoker;
import gov.nyc.doitt.gis.geoclient.search.spi.ResponseStatusReader;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;

/**
 * Adapts the {@code geoclient-service} module's Geosupport wiring to the
 * framework-neutral SPI contracts published by the {@code geoclient-search}
 * module. Used exclusively from Spring configuration to keep the search
 * module free of any Spring or {@code service}-package types.
 */
public final class SearchSpiAdapters {

    private SearchSpiAdapters() {
    }

    /**
     * Wraps a {@link GeosupportService} bean as a {@link GeosupportInvoker}.
     */
    public static GeosupportInvoker asInvoker(GeosupportService svc) {
        return new GeosupportInvoker() {
            @Override
            public Map<String, Object> callFunction1B(String houseNumber, String street, String borough, String zip) {
                return svc.callFunction1B(houseNumber, street, borough, zip);
            }

            @Override
            public Map<String, Object> callFunction2(String crossStreetOne, String boroughCrossStreetOne,
                    String crossStreetTwo, String boroughCrossStreetTwo, String compassDirection) {
                return svc.callFunction2(crossStreetOne, boroughCrossStreetOne, crossStreetTwo, boroughCrossStreetTwo,
                    compassDirection);
            }

            @Override
            public Map<String, Object> callFunction3(String onStreet, String boroughOnStreet, String crossStreetOne,
                    String boroughCrossStreetOne, String crossStreetTwo, String boroughCrossStreetTwo,
                    String compassDirection) {
                return svc.callFunction3(onStreet, boroughOnStreet, crossStreetOne, boroughCrossStreetOne,
                    crossStreetTwo, boroughCrossStreetTwo, compassDirection);
            }

            @Override
            public Map<String, Object> callFunctionBL(String borough, String block, String lot) {
                return svc.callFunctionBL(borough, block, lot);
            }

            @Override
            public Map<String, Object> callFunctionBN(String bin) {
                return svc.callFunctionBN(bin);
            }
        };
    }

    /**
     * Wraps a {@code Mapper<ResponseStatus>} bean as a
     * {@link ResponseStatusReader}.
     */
    public static ResponseStatusReader asStatusReader(Mapper<ResponseStatus> mapper) {
        return params -> mapper.fromParameters(params, ResponseStatus.class);
    }
}
