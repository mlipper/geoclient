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
package gov.nyc.doitt.gis.geoclient.api.mapper;

import java.util.Map;

/**
 * Maps between Geosupport parameter {@link Map}s (field name and value pairs)
 * and objects of type {@code T}.
 *
 * @author Matthew Lipper
 * @param <T> the type of object mapped to and from parameters
 * @see AbstractParameterMapper
 */
public interface Mapper<T> {

    /**
     * Creates a new instance of the destination class and populates it from the
     * given parameters.
     *
     * @param source the parameter names and values
     * @param destinationClass the class of the object to create
     * @return a new, populated instance
     * @throws MappingException if the mapping fails
     */
    T fromParameters(Map<String, Object> source, Class<T> destinationClass) throws MappingException;

    /**
     * Populates the given destination object from the given parameters.
     *
     * @param source the parameter names and values
     * @param destination the object to populate
     * @return the populated destination object
     * @throws MappingException if the mapping fails
     */
    T fromParameters(Map<String, Object> source, T destination) throws MappingException;

    /**
     * Adds the state of the given object to the destination parameter map.
     *
     * @param source the object to map
     * @param destination the map to populate
     * @return the populated destination map
     * @throws MappingException if the mapping fails
     */
    Map<String, Object> toParameters(T source, Map<String, Object> destination) throws MappingException;

    /**
     * Maps the state of the given object to a new parameter map.
     *
     * @param source the object to map
     * @return a new map of parameter names and values
     * @throws MappingException if the mapping fails
     */
    Map<String, Object> toParameters(T source) throws MappingException;
}
