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
package gov.nyc.doitt.gis.geoclient.api;

/**
 * A street identified by a Geosupport street code, a name, or both. Names and
 * codes are compared without regard to case.
 *
 * @author mlipper
 */
public class Street extends CodeNamedValue {

    /**
     * Creates a street with both a code and a name.
     *
     * @param code street code
     * @param name street name
     */
    public Street(String code, String name) {
        super(code, name, false);
    }

    /**
     * Creates a street identified only by name.
     *
     * @param name street name
     */
    public Street(String name) {
        super(null, name, false);
    }

}
