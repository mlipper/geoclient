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
package geoclientbuild.jarexec;

import static geoclientbuild.jarexec.OptionSpecs.OPT_ARG;
import static geoclientbuild.jarexec.OptionSpecs.OPT_ENV;
import static geoclientbuild.jarexec.OptionSpecs.OPT_HELP;
import static geoclientbuild.jarexec.OptionSpecs.OPT_HTTPFILE;
import static geoclientbuild.jarexec.OptionSpecs.OPT_JARFILE;
import static geoclientbuild.jarexec.OptionSpecs.OPT_JAVACMD;
import static geoclientbuild.jarexec.OptionSpecs.OPT_START;
import static geoclientbuild.jarexec.OptionSpecs.OPT_STOP;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_ARG;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_ENV;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_HELP;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_HTTPFILE;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_JARFILE;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_JAVACMD;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_START;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_STOP;
import static geoclientbuild.jarexec.OptionSpecs.OPT_TXT_WORKDIR;
import static geoclientbuild.jarexec.OptionSpecs.OPT_WORKDIR;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import geoclientbuild.jarexec.exec.JarExecutionService;
import geoclientbuild.jarexec.settings.Settings;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private final JarExecutionService service;
    private final Settings settings;
    private Process process;

    public Main(Settings settings) {
        this.settings = settings;
        this.service = new JarExecutionService(settings);
    }

    public void showSettings() {
        logger.info("Current Settings: " + settings);
    }

    public Settings getSettings() {
        return settings;
    }

    public void startService() {
        try {
            this.process = this.service.start();
        }
        catch (Exception e) {
            logger.error("Error executing jar process.", e);
            throw new RuntimeException("Jar process failed to start.", e);
        }
    }

    public void stopService() {
        try {
            this.service.stop(this.process);
        }
        catch (Exception e) {
            logger.error("Error stopping jar process.", e);
            throw new RuntimeException("Jar process failed to stop.", e);
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = newInstance(args);
        if (main != null) {
            // Application started successfully
            System.out.println("[OK]");
        }
    }

    static Main newInstance(String[] args) throws IOException {
        OptionParser parser = new OptionParser() {
            {
                accepts(OPT_HELP, OPT_TXT_HELP).forHelp();
                accepts(OPT_ARG, OPT_TXT_ARG).withRequiredArg();
                accepts(OPT_ENV, OPT_TXT_ENV).withRequiredArg();
                accepts(OPT_HTTPFILE, OPT_TXT_HTTPFILE).withRequiredArg();
                accepts(OPT_JARFILE, OPT_TXT_JARFILE).withRequiredArg().required();
                accepts(OPT_JAVACMD, OPT_TXT_JAVACMD).withRequiredArg().defaultsTo("java");
                accepts(OPT_START, OPT_TXT_START);
                accepts(OPT_STOP, OPT_TXT_STOP);
                accepts(OPT_WORKDIR, OPT_TXT_WORKDIR).withRequiredArg();
            }
        };

        OptionSet options = parser.parse(args);
        if (options.has(OPT_HELP)) {
            parser.printHelpOn(System.out);
            return null;
        }
        Settings settings = createSettings(options);
        logger.info(String.format("%nStarting application with settings:%n%s", settings.settings()));
        Main main = new Main(settings);
        if (options.has(OPT_START)) {
            logger.info(String.format("%nStarting application with settings:%n%s", settings.settings()));
            main.startService();
        }
        return main;
    }

    static Settings createSettings(OptionSet options) {
        Settings settings = new Settings();
        if (options.has(OPT_ARG)) {
            List<String> args = options.valuesOf(OPT_ARG).stream().map(Object::toString).collect(Collectors.toList());
            settings.setArguments(args);
        }
        if (options.has(OPT_ENV)) {
            List<String> envs = options.valuesOf(OPT_ENV).stream().map(Object::toString).collect(Collectors.toList());
            Map<String, String> envMap = envs.stream().map(str -> str.split("=")).collect(
                Collectors.toMap(str -> str[0], str -> str[1]));
            settings.setEnvironment(envMap);
        }
        //if(options.has(OPT_HTTPFILE)) {
        //    settings.setSettingsFile(options.valueOf(OPT_HTTPFILE).toString());
        //}
        if (options.has(OPT_JARFILE)) {
            settings.setJarFile(options.valueOf(OPT_JARFILE).toString());
        }
        if (options.has(OPT_JAVACMD)) {
            settings.setJavaCommand(options.valueOf(OPT_JAVACMD).toString());
        }
        if (options.has(OPT_WORKDIR)) {
            settings.setWorkingDirectory(options.valueOf(OPT_WORKDIR).toString());
        }
        return settings;
    }
}
