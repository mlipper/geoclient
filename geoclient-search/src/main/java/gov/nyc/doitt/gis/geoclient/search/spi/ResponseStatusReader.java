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
package gov.nyc.doitt.gis.geoclient.search.spi;

import java.util.Map;

import gov.nyc.doitt.gis.geoclient.search.ResponseStatus;

/**
 * Framework-neutral SPI for producing a {@link ResponseStatus} from a raw
 * Geosupport work-area parameter map. Hosts adapt this contract to whichever
 * mapping strategy they use (reflection-based bean mapper, hand-written
 * converter, test fake, etc.).
 * <p>
 * This is the single mapping operation that the single-field search pipeline
 * needs; declaring it explicitly avoids a dependency on any generic
 * {@code Mapper} abstraction from the enclosing application.
 *
 * @since 3.0
 */
@FunctionalInterface
public interface ResponseStatusReader {

    /**
     * Interpret the given Geosupport work-area parameter map as a
     * {@link ResponseStatus}.
     *
     * @param parameters the raw Geosupport work-area parameter map
     * @return the parsed response status; never {@code null}
     */
    ResponseStatus read(Map<String, Object> parameters);
}
