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
package gov.nyc.doitt.gis.geoclient.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The service that calls {@code geoclient-service} and writes the requests and
 * responses to files.
 *
 * @author mlipper
 */
public class GeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorService.class);
    private ServiceClient serviceClient;
    private ResponseWriter responseWriter;

    /**
     * The generation service that is autowired by Spring.
     *
     * @param serviceClient the ServiceClient to use
     * @param responseWriter  the directory to write output files
     */
    public GeneratorService(ServiceClient serviceClient, ResponseWriter responseWriter) {
        this.serviceClient = serviceClient;
        this.responseWriter = responseWriter;
    }

    public void generate(Sample sample) {
        Response response = this.serviceClient.get(sample);
        logger.info(response.toString());
        this.responseWriter.write(sample, response);
    }
}
