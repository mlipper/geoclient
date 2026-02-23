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
package gov.nyc.doitt.gis.geoclient.service.web.filter;

import java.util.Map;

public class QueryParamUtils {

    public static boolean containsParam(Map<String, String[]> params, String key, String value) {
        if (!params.containsKey(key)) {
            return false;
        }
        String[] strings = params.get(key);
        if (strings == null || strings.length != 1) {
            return false;
        }
        String actualValue = strings[0];
        if (actualValue == null && value == null) {
            // Null value is expected
            return true;
        }
        if (actualValue == null && value != null) {
            // Null value is not expected
            return true;
        }
        return value.equals(actualValue);
    }
}
