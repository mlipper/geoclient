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
package gov.nyc.doitt.gis.geoclient.config;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.function.Filter;
import gov.nyc.doitt.gis.geoclient.function.Function;
import gov.nyc.doitt.gis.geoclient.function.WorkArea;

/**
 * Registry for threadsafe management of singleton instances of {@link Filter},
 * {@link Function} and {@link WorkArea} providing methods to add, retrieve,
 * check existence, and clear these components.
 *
 * @author mlipper
 */
public class Registry {
    private static final Logger log = LoggerFactory.getLogger(Registry.class);

    private static final ConcurrentMap<String, List<Filter>> filterListRegistry = new ConcurrentHashMap<String, List<Filter>>();
    private static final ConcurrentMap<String, Function> functionRegistry = new ConcurrentHashMap<String, Function>();
    private static final ConcurrentMap<String, WorkArea> workAreaRegistry = new ConcurrentHashMap<String, WorkArea>();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Registry() {
    }

    /**
     * Adds a list of {@link Filter} instances to the registry under the specified ID.
     *
     * @param id the ID under which to register the list of {@link Filter} instances.
     * @param filterList the list of {@link Filter} instances to add to the registry.
     */
    public static void addFilterList(String id, List<Filter> filterList) {
        if (filterList == null) {
            throw new IllegalArgumentException("List<Filter> argument for id " + id + " cannot be null");
        }
        log.debug("add(List<Filter> [{}])", id);
        filterListRegistry.putIfAbsent(id, filterList);
    }

    /**
     * Adds a {@link Function} instance to the registry under its ID.
     *
     * @param function the {@link Function} instance to add to the registry.
     */
    public static void addFunction(Function function) {
        log.debug("add({})", function);
        functionRegistry.putIfAbsent(function.getId(), function);
    }

    /**
     * Adds a {@link WorkArea} instance to the registry under its ID.
     *
     * @param workArea the {@link WorkArea} instance to add to the registry.
     */
    public static void addWorkArea(WorkArea workArea) {
        log.debug("add({})", workArea);
        workAreaRegistry.putIfAbsent(workArea.getId(), workArea);
    }

    /**
     * Clears all registered {@link Filter} lists, {@link Function} instances, and {@link WorkArea} instances from the registry.
     */
    public static void clearAll() {
        clearFilterLists();
        clearFunctions();
        clearWorkAreas();
    }

    /**
     * Clears all registered {@link Filter} lists from the registry.
     */
    public static void clearFilterLists() {
        filterListRegistry.clear();
        log.debug("filterListRegistry.clear()");
    }

    /**
     * Clears all registered {@link Function} instances from the registry.
     */
    public static void clearFunctions() {
        functionRegistry.clear();
        log.debug("functionRegistry.clear()");
    }

    /**
     * Clears all registered {@link WorkArea} instances from the registry.
     */
    public static void clearWorkAreas() {
        workAreaRegistry.clear();
        log.debug("workAreaRegistry.clear()");
    }

    /**
     * Checks if a {@link Filter} list with the given ID is registered in the registry.
     *
     * @param id the ID of the {@link Filter} list to check.
     * @return true if the {@link Filter} list is registered, false otherwise.
     */
    public static boolean containsFilterList(String id) {
        boolean contains = filterListRegistry.containsKey(id);
        log.debug("filterListRegistry.containsKey({})=={}", id, contains);
        return contains;
    }

    /**
     * Checks if a {@link Function} instance with the given ID is registered in the registry.
     *
     * @param id the ID of the {@link Function} instance to check.
     * @return true if the {@link Function} instance is registered, false otherwise.
     */
    public static boolean containsFunction(String id) {
        boolean contains = functionRegistry.containsKey(id);
        log.debug("functionRegistry.containsKey({})=={}", id, contains);
        return contains;
    }

    /**
     * Checks if a {@link WorkArea} instance with the given ID is registered in the registry.
     *
     * @param name the ID of the {@link WorkArea} instance to check.
     * @return true if the {@link WorkArea} instance is registered, false otherwise.
     */
    public static boolean containsWorkArea(String name) {
        boolean contains = workAreaRegistry.containsKey(name);
        log.debug("workAreaRegistry.containsKey({})=={}", name, contains);
        return contains;
    }

    /**
     * Retrieves the {@link Filter} list with the given ID from the registry.
     *
     * @param id the ID of the {@link Filter} list to retrieve.
     * @return the {@link Filter} list with the given ID, or null if not found.
     */
    public static List<Filter> getFilterList(String id) {
        List<Filter> filterList = filterListRegistry.get(id);
        log.debug("filterListRegistry.get({})=={}", id, filterList);
        return filterList;
    }

    /**
     * Retrieves the {@link Function} instance with the given ID from the registry.
     *
     * @param id the ID of the {@link Function} instance to retrieve.
     * @return the {@link Function} instance with the given ID, or null if not found.
     */
    public static Function getFunction(String id) {
        Function function = functionRegistry.get(id);
        log.debug("functionRegistry.get({})=={}", id, function);
        return function;
    }

    /**
     * Retrieves the {@link WorkArea} instance with the given ID from the registry.
     *
     * @param name the ID of the {@link WorkArea} instance to retrieve.
     * @return the {@link WorkArea} instance with the given ID, or null if not found.
     */
    public static WorkArea getWorkArea(String name) {
        WorkArea workArea = workAreaRegistry.get(name);
        log.debug("workAreaRegistry.get({})=={}", name, workArea);
        return workArea;
    }

}
