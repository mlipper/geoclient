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
package gov.nyc.doitt.gis.geoclient.search.task;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.search.Response;
import gov.nyc.doitt.gis.geoclient.search.ResponseStatus;
import gov.nyc.doitt.gis.geoclient.search.Search;
import gov.nyc.doitt.gis.geoclient.search.request.Request;
import gov.nyc.doitt.gis.geoclient.search.spi.GeosupportInvoker;
import gov.nyc.doitt.gis.geoclient.search.spi.ResponseStatusReader;

public abstract class SearchTask implements Callable<Search> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTask.class);

    protected final Request request;
    protected final GeosupportInvoker geosupport;
    private final ResponseStatusReader statusReader;

    public SearchTask(Request request, GeosupportInvoker geosupport, ResponseStatusReader statusReader) {
        super();
        this.request = request;
        this.geosupport = geosupport;
        this.statusReader = statusReader;
    }

    @Override
    public Search call() throws Exception {
        Map<String, Object> actualResult = doCall();
        LOGGER.debug("Results from doCall(): {}", actualResult);
        ResponseStatus responseStatus = this.statusReader.read(actualResult);
        return new Search(request, new Response(responseStatus, actualResult));
    }

    protected abstract Map<String, Object> doCall();
}
