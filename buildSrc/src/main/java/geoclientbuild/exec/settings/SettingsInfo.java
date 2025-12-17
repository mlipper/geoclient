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
package geoclientbuild.exec.settings;

import geoclientbuild.base.AbstractSettingsInfo;

public class SettingsInfo extends AbstractSettingsInfo {

    static final String ENV_SECTION_TITLE = "Environment variables";
    static final String WORKING_DIR_SECTION_TITLE = "Working directory";
    static final String COMMAND_LINE_SECTION_TITLE = "Command line";

    private final Settings settings;

    public SettingsInfo(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String info() {
        StringBuilder txt = new StringBuilder();
        appendInfoSection(txt, ENV_SECTION_TITLE, DEFAULT_MAP_SUMMARY.execute(settings.getEnvironment()));
        appendInfoSection(txt, WORKING_DIR_SECTION_TITLE,
            settings.getWorkingDirectory() != null ? settings.getWorkingDirectory().getAbsolutePath()
                    : "<default> (current directory)");
        appendInfoSection(txt, COMMAND_LINE_SECTION_TITLE, settings.commandLine());
        return txt.toString();
    }
}
