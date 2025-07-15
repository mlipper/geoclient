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
package gov.nyc.doitt.gis.geoclient.json;

/**
 * Exception thrown to indicate a configuration error specific to JSON processing
 * within the application.
 *
 * <p>This exception is a runtime exception and can be used to signal issues
 * such as invalid or missing configuration parameters related to JSON handling.</p>
 *
 * @see RuntimeException
 */
public class JsonConfigurationException extends RuntimeException {

    /**
     * Constructs a new {@code JsonConfigurationException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public JsonConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code JsonConfigurationException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param t the cause of the exception (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public JsonConfigurationException(String message, Throwable t) {
        super(message, t);
    }
}
