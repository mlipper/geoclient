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
package geoclientbuild.jarexec.settings;

import java.io.File;
import java.util.List;
import java.util.Map;

import geoclientbuild.base.AbstractSettingsInfo;

public abstract class BaseSettingsTest {

    protected AbstractSettingsInfo abstractInfo = new AbstractSettingsInfo() {
        @Override
        public String info() {
            return "";
        }
    };

    protected Map<String, String> environment = Map.of("KEY1", "VALUE1", "KEY2", "VALUE2");

    protected List<String> arguments = List.of("arg1", "arg2", "arg3");

    protected File jarFile = new File("/app/example.jar");
    protected String javaCommand = "lava";
    protected long sleepSecondsAfterStart = 6L;
    protected long sleepSecondsAfterStop = 4L;

    protected Settings settingsFixture() {
        Settings settings = new Settings();
        settings.setArguments(arguments);
        settings.setEnvironment(environment);
        settings.setJarFile(jarFile);
        settings.setJavaCommand(javaCommand);
        settings.setSleepSecondsAfterStart(sleepSecondsAfterStart);
        settings.setSleepSecondsAfterStop(sleepSecondsAfterStop);
        settings.setWorkingDirectory(jarFile);
        return settings;
    }
}
