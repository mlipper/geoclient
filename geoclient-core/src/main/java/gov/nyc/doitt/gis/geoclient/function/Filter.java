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

import gov.nyc.doitt.gis.geoclient.util.Assert;

/**
 * Filter class that provides functionality to filter out fields returned by
 * by a {@link Function}. The filtering is done based on a pattern which is
 * matched against a {@link Field}'s ID.
 *
 * @author mlipper
 * @since 1.0
 */
public class Filter {
    private final String pattern;

    /**
     * Constructs a Filter with the specified pattern.
     *
     * @param pattern the pattern to use for filtering fields.
     */
    public Filter(String pattern) {
        super();
        Assert.notNull(pattern, "Pattern argument cannot be null");
        this.pattern = pattern;
    }

    /**
     * Checks if the given field's ID matches the filter's pattern both of which
     * are {@link String} objects:
     * <p>
     * {@code field.getId().matches(pattern)}
     *
     * @param field the field to check against the filter's pattern.
     * @return true if the field's ID matches the pattern, false otherwise.
     */
    public boolean matches(Field field) {
        return field.getId().matches(pattern);
    }

    @Override
    public String toString() {
        return "Filter [pattern=" + pattern + "]";
    }

}
