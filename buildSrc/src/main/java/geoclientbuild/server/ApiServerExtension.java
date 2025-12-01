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
package geoclientbuild.server;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public interface ApiServerExtension {

    public static final String EXTENSION_NAME = "apiserver";

    RegularFileProperty getServerJar();

    RegularFileProperty getPidFile();

    Property<String> getContextPath();

    Property<String> getJavaCommand();

    Property<String> getHost();

    Property<Integer> getPort();

    Property<String> getScheme();

    MapProperty<String, String> getEnvironment();

    ListProperty<String> getArguments();

    Property<Long> getSleepSecondsAfterStart();
}
