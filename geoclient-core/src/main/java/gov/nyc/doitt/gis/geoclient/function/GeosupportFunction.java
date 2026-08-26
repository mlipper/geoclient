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
package gov.nyc.doitt.gis.geoclient.function;

import java.nio.ByteBuffer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.nyc.doitt.gis.geoclient.jni.Geoclient;

/**
 * Default implementation of the {@link Function} interface that represents a Geosupport function.
 * It manages the work areas, configuration, and interaction with the Geoclient JNI layer.
 * <p>
 * This class provides methods to execute the function with one or two work areas and to retrieve the results.
 * It also includes logging for function calls and buffer states.
 *
 * @author mlipper
 * @since 1.0
 */
public class GeosupportFunction implements Function {
    private static final Logger log = LoggerFactory.getLogger(GeosupportFunction.class);
    //private static final Logger JNI_BUFFER_LOGGER = LoggerFactory.getLogger("GC_JNI_BUFFER_LOGGER");
    private final String id;
    private final WorkArea workAreaOne;
    private final WorkArea workAreaTwo;
    private final Geoclient geoclient;
    private final Configuration configuration;

    /**
     * Constructs a GeosupportFunction with the specified parameters.
     *
     * @param id the function identifier
     * @param workAreaOne the first work area
     * @param workAreaTwo the second work area
     * @param geoclient the Geoclient JNI instance
     * @param configuration the configuration for this function
     */
    public GeosupportFunction(String id, WorkArea workAreaOne, WorkArea workAreaTwo, Geoclient geoclient,
            Configuration configuration) {
        super();
        this.id = id;
        this.workAreaOne = workAreaOne;
        this.workAreaTwo = workAreaTwo;
        this.geoclient = geoclient;
        this.configuration = configuration;
    }

    /**
     * Constructs a GeosupportFunction without a configuration.
     *
     * @param id the function identifier
     * @param workAreaOne the first work area
     * @param workAreaTwo the second work area
     * @param geoclient the Geoclient JNI instance
     */
    public GeosupportFunction(String id, WorkArea workAreaOne, WorkArea workAreaTwo, Geoclient geoclient) {
        this(id, workAreaOne, workAreaTwo, geoclient, null);
    }

    /**
     * Constructs a GeosupportFunction with only the first work area.
     *
     * @param id the function identifier
     * @param workAreaOne the first work area
     * @param geoclient the Geoclient JNI instance
     */
    public GeosupportFunction(String id, WorkArea workAreaOne, Geoclient geoclient) {
        this(id, workAreaOne, null, geoclient);
    }

    /**
     * Executes the function with the given parameters.
     *
     * @param parameters a map of parameter names to values
     * @return a map of result names to values
     * @throws IllegalStateException if the function cannot be executed with the given parameters
     * @see {@link Function#call(java.util.Map)}
     */
    @Override
    public Map<String, Object> call(Map<String, Object> parameters) {
        if (isTwoWorkAreas()) {
            return doTwoWorkAreaCall(parameters);
        }
        return doOneWorkAreaCall(parameters);
    }

    /**
     * Returns the identifier of this function.
     *
     * @return the function identifier
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the first work area associated with this function.
     *
     * @return the first work area
     */
    public WorkArea getWorkAreaOne() {
        return workAreaOne;
    }

    /**
     * Returns the second work area associated with this function.
     *
     * @return the second work area
     */
    public WorkArea getWorkAreaTwo() {
        return workAreaTwo;
    }

    /**
     * Returns a string representation of this function.
     *
     * @return a string representation of this function
     */
    @Override
    public String toString() {
        return "Function [id=" + id + "]";
    }

    private Map<String, Object> doOneWorkAreaCall(Map<String, Object> parameters) {
        ByteBuffer wa1 = workAreaOne.createBuffer(parameters);
        logFunctionCall();
        logBuffer("WA1", "input", wa1);
        this.geoclient.callgeo(wa1, null);
        logBuffer("WA1", "output", wa1);
        return this.workAreaOne.parseResults(wa1);
    }

    private Map<String, Object> doTwoWorkAreaCall(Map<String, Object> parameters) {
        ByteBuffer wa1 = workAreaOne.createBuffer(parameters);
        ByteBuffer wa2 = workAreaTwo.createBuffer();
        logFunctionCall();
        logBuffer("WA1", "input", wa1);
        this.geoclient.callgeo(wa1, wa2);
        logBuffer("WA1", "output", wa1);
        logBuffer("WA2", "output", wa2);
        Map<String, Object> result = this.workAreaOne.parseResults(wa1);
        result.putAll(workAreaTwo.parseResults(wa2));
        return result;
    }

    /**
     * Indicates whether this function uses two work areas.
     *
     * @return true if the function uses two work areas, false otherwise
     */
    public boolean isTwoWorkAreas() {
        return this.workAreaTwo != null;
    }

    /**
     * Returns the configuration associated with this function.
     *
     * @return the configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Logs the function call at the debug level.
     */
    private void logFunctionCall() {
        log.debug("Calling {}", this);
    }

    /**
     * Logs the contents of a work area buffer at the trace level.
     *
     * @param workArea the work area identifier
     * @param inputOutput indicates whether the buffer is for input or output
     * @param buffer the ByteBuffer containing the work area data
     */
    private void logBuffer(String workArea, String inputOutput, ByteBuffer buffer) {
        if (log.isTraceEnabled()) {
            final String bufferString = new String(buffer.array());
            log.trace("{}[{}]:'{}'", String.format("F%6s", this.id + "." + workArea), String.format("%6s", inputOutput),
                bufferString);
            //JNI_BUFFER_LOGGER.trace("{}[{}]:'{}'", String.format("F%6s", this.id + "." + workArea), String.format("%6s", inputOutput), bufferString);
        }
    }

}
