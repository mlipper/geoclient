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
package geoclientbuild.base;

import java.util.Map;

public abstract class AbstractSettingsInfo {

    public interface MapSummary<K, V> {

        String execute(Map<String, String> map);

        default boolean isNullOrEmpty(Map<String, String> map) {
            return map == null || map.isEmpty();
        }
    }

    public abstract String info();

    public MapSummary<String, String> DEFAULT_MAP_SUMMARY = new MapSummary<String, String>() {
        @Override
        public String execute(Map<String, String> map) {
            if (isNullOrEmpty(map)) {
                return " <none>";
            }
            StringBuilder sb = new StringBuilder();
            map.forEach((k, v) -> sb.append("  ").append(k).append("=").append(v).append(System.lineSeparator()));
            return sb.toString().trim();
        }
    };

    public void appendInfoSection(StringBuilder txt, String title, String content) {
        txt.append(System.lineSeparator());
        txt.append(title).append(":").append(System.lineSeparator());
        txt.append("----------------------").append(System.lineSeparator());
        txt.append("  ").append(content).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return info();
    }
}
