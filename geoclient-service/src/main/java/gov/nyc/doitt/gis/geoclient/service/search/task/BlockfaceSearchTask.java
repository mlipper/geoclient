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
package gov.nyc.doitt.gis.geoclient.service.search.task;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.service.invoker.GeosupportService;
import gov.nyc.doitt.gis.geoclient.service.mapper.Mapper;
import gov.nyc.doitt.gis.geoclient.service.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.service.search.request.BlockfaceRequest;
import gov.nyc.doitt.gis.geoclient.service.search.request.Request;

public class BlockfaceSearchTask extends SearchTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockfaceSearchTask.class);

    public BlockfaceSearchTask(Request request, GeosupportService geosupportService, Mapper<ResponseStatus> mapper) {
        super(request, geosupportService, mapper);
    }

    @Override
    protected Map<String, Object> doCall() {
        BlockfaceRequest blockfaceRequest = (BlockfaceRequest) this.request;
        LOGGER.debug("Calling {} with {}.", Function.F3, blockfaceRequest);
        return this.geosupportService.callFunction3(blockfaceRequest.getOnStreet(), blockfaceRequest.getBorough(),
            blockfaceRequest.getCrossStreetOne(), null, // borough cross street one
            blockfaceRequest.getCrossStreetTwo(), null, // borough cross street two
            null // compass direction
        );
    }

}
