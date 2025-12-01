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

import java.util.Arrays;
import java.util.List;

public class ArgumentsBuilder {

    public static class BootArgumentsBuilder {
        private String host;
        private int port;
        private String contextPath;
        private String profile;

        public BootArgumentsBuilder host(String host) {
            this.host = host;
            return this;
        }

        public BootArgumentsBuilder port(int port) {
            this.port = port;
            return this;
        }

        public BootArgumentsBuilder contextPath(String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public BootArgumentsBuilder profile(String profile) {
            this.profile = profile;
            return this;
        }

        public List<String> build() {
            return Arrays.asList(new String[] { "--server.address=" + host, "--server.port=" + port,
                    "--server.servlet.context-path=" + contextPath, "--spring.profiles.active=" + profile });
        }
    }
}
