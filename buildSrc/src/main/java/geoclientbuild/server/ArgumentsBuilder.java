package geoclientbuild.server;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArgumentsBuilder {

    //        "-jar", apiServerJar.getAsFile().get().getAbsolutePath(),
    //        "--server.address=" + getHost(),
    //        "--server.port=" + getPort(),
    //        "--server.servlet.context-path=/" + getContextPath(),
    //        "--spring.profiles.active=docsamples");
    //    processBuilder.environment().put("GEOSUPPORT_HOME", "/opt/geosupport/current");

    public static class BootArgumentsBuilder {
        private String host = "localhost";
        private int port = 8080;
        private String contextPath = "geoclient/v2";
        private String profile = "docsamples";

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
                "--server.servlet.context-path=/" + contextPath,
                "--spring.profiles.active=" + profile
            });
        }
    }

    public static class JvmArgumentsBuilder {
        private List<String> jvmArgs = Collections.emptyList();

        public JvmArgumentsBuilder jvmArgs(List<String> jvmArgs) {
            this.jvmArgs = jvmArgs;
            return this;
        }

        public List<String> build() {
            return jvmArgs;
        }
    }
}
