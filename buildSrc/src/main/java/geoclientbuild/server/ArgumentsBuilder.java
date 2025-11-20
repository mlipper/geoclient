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
            return Arrays.asList(new String[] {
                "--server.address=" + host,
                "--server.port=" + port,
                "--server.servlet.context-path=" + contextPath,
                "--spring.profiles.active=" + profile
            });
        }
    }
}
