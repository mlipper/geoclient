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

import java.util.Collections;
import java.util.Map;

/**
 * Default implementation of the {@link Configuration} interface.
 *
 * @author mlipper
 * @since 1.0
 */
public class DefaultConfiguration implements Configuration {
    private Map<String, Object> requiredArguments;

    /**
     * Constructs a DefaultConfiguration with no required arguments.
     */
    public DefaultConfiguration() {
        this.requiredArguments = null;
    }

    /**
     * Constructs a DefaultConfiguration with the specified required arguments.
     *
     * @param requiredArguments a map of required argument names to their default values or settings
     * @see Configuration#requiredArguments()
     */
    public DefaultConfiguration(Map<String, Object> requiredArguments) {
        this.requiredArguments = requiredArguments;
    }

    /**
     * Returns the required arguments for this configuration.
     *
     * @return a map of required argument names to their default values or settings
     * @see Configuration#requiredArguments()
     */
    @Override
    public Map<String, Object> requiredArguments() {
        if (requiredArguments == null) {
            return null;
        }
        return Collections.unmodifiableMap(this.requiredArguments);
    }

    /**
     * Sets the required arguments for this configuration.
     *
     * @param requiredArguments a map of required argument names to their default values or settings
     */
    public void setRequiredArguments(Map<String, Object> requiredArguments) {
        this.requiredArguments = requiredArguments;
    }

}
