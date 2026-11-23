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

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Registry of {@link Mapper}s keyed by mapper class. At most one mapper of a
 * given class is held at a time.
 *
 * @author Matthew Lipper
 */
public class MappingContext {

    private final ConcurrentMap<Class<?>, Mapper<?>> mappers = new ConcurrentHashMap<Class<?>, Mapper<?>>();

    /**
     * Creates an empty context.
     */
    public MappingContext() {
    }

    /**
     * Creates a context containing the given mappers.
     *
     * @param mappers the mappers to register; must not contain two mappers of the
     *        same class
     */
    public MappingContext(List<Mapper<?>> mappers) {
        this.mappers.putAll(mappers.stream().collect(Collectors.toMap(Mapper::getClass, Function.identity())));
    }

    /**
     * Registers the given mapper under its class, replacing any existing mapper
     * of that class.
     *
     * @param mapper the mapper to register
     * @return the mapper previously registered for the class, or {@code null}
     */
    public Mapper<?> add(Mapper<?> mapper) {
        return this.mappers.put(mapper.getClass(), mapper);
    }

    /**
     * Indicates whether a mapper is registered for the given class.
     *
     * @param mapperClass the mapper class
     * @return {@code true} if a mapper is registered under the class
     */
    public boolean containsMapper(Class<?> mapperClass) {
        return this.mappers.containsKey(mapperClass);
    }

    /**
     * Retrieves the mapper registered for the given class.
     *
     * @param clazz the mapper class
     * @return the registered mapper
     * @throws IllegalArgumentException if no mapper is registered for the class
     */
    public Mapper<?> getMapper(Class<?> clazz) {
        if (this.mappers.containsKey(clazz)) {
            return this.mappers.get(clazz);
        }
        throw new IllegalArgumentException(
            String.format("No Mapper configured for Class<%s>", clazz.getCanonicalName()));
    }
}
