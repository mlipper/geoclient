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
package gov.nyc.doitt.gis.geoclient.util;

import java.util.Optional;

/**
 * String conversion utilities.
 *
 * @author mlipper
 */
public class StringUtils {

    private StringUtils() {
    }

    /**
     * Converts the given object to an {@link Optional} string.
     *
     * @param obj the object to convert, may be {@code null}
     * @return the object's string value, or an empty {@link Optional} if the object is {@code null}
     */
    public static Optional<String> optionalStringValue(Object obj) {
        if (obj != null) {
            return Optional.of(obj.toString());
        }
        return Optional.empty();
    }

    /**
     * Converts the given object to a string.
     *
     * @param obj the object to convert, may be {@code null}
     * @return the object's string value, or {@code null} if the object is {@code null}
     */
    public static String stringOrNullValueOf(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

}
