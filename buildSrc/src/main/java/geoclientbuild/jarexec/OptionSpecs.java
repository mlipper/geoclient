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

import java.util.Map;

public class OptionSpecs {

    public static final String OPT_ARG = "arg";
    public static final String OPT_ENV = "env";
    public static final String OPT_HELP = "help";
    public static final String OPT_HTTPFILE = "httpfile";
    public static final String OPT_JARFILE = "jarfile";
    public static final String OPT_JAVACMD = "javacmd";
    public static final String OPT_START = "start";
    public static final String OPT_STOP = "stop";
    public static final String OPT_WORKDIR = "workdir";

    public static final String OPT_TXT_ARG = "Argument to pass to the java command. Can be given more than once using the order they should appear on the command line.";
    public static final String OPT_TXT_ENV = "Environment variable to set before running the java command. The variable name and value should be separated by '=' without whitespace and then quoted (--env='FOO=bar'). Can be given more than once to set multiple variables.";
    public static final String OPT_TXT_HELP = "Shows this help message.";
    public static final String OPT_TXT_HTTPFILE = "Configuration file enabling shutdown via HTTP.";
    public static final String OPT_TXT_HTTPSTOP = "Configuration file enabling shutdown via HTTP.";
    public static final String OPT_TXT_JARFILE = "Executable jar file.";
    public static final String OPT_TXT_JAVACMD = "Java command to use for executing the jar file.";
    public static final String OPT_TXT_START = "Executes the jar in a new process.";
    public static final String OPT_TXT_STOP = "Stops the running jar process.";
    public static final String OPT_TXT_WORKDIR = "Sets the current working directory in which the java process will run.";

    public static final Map<String, String> OPTIONS_INFO = Map.of(OPT_ARG, OPT_TXT_ARG, OPT_ENV, OPT_TXT_ENV, OPT_HELP,
        OPT_TXT_HELP, OPT_HTTPFILE, OPT_TXT_HTTPFILE, OPT_JARFILE, OPT_TXT_JARFILE, OPT_JAVACMD, OPT_TXT_JAVACMD,
        OPT_STOP, OPT_TXT_STOP, OPT_WORKDIR, OPT_TXT_WORKDIR);
}
