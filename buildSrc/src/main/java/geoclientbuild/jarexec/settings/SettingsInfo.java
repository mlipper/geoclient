package geoclientbuild.jarexec.settings;

import java.util.Map;

public class SettingsInfo {

    static final String ENV_SECTION_TITLE = "Environment variables";
    static final String WORKING_DIR_SECTION_TITLE = "Working directory";
    static final String COMMAND_LINE_SECTION_TITLE = "Command line";
    static final String HTTP_SHUTDOWN_SECTION_TITLE = "HTTP shutdown";

    interface MapSummary<K, V> {
        String execute(Map<String, String> map);
        default boolean isNullOrEmpty(Map<String, String> map) {
            return map == null || map.isEmpty();
        }
    }

    static MapSummary<String, String> DEFAULT_MAP_SUMMARY = new MapSummary<String, String>() {
        @Override
        public String execute(Map<String,String> map) {
            if (isNullOrEmpty(map)) {
                return " <none>";
            }
            StringBuilder sb = new StringBuilder();
            map.forEach((k, v) -> sb.append("  ").append(k).append("=").append(v).append(System.lineSeparator()));
            return sb.toString().trim();
        }
    };

    static void appendInfoSection(StringBuilder txt, String title, String content) {
        txt.append(System.lineSeparator());
        txt.append(title).append(":").append(System.lineSeparator());
        txt.append("----------------------").append(System.lineSeparator());
        txt.append("  ").append(content).append(System.lineSeparator());
    }

    static String httpShutdownInfo(Settings settings) {
        StringBuffer sb = new StringBuffer();
        sb.append("  status: %s%n");
        HttpShutdown httpShutdown = null;
        if (settings.supportsHttpShutdown()) {
            httpShutdown = settings.httpShutdown();
            sb.append("%s");
            return String.format(sb.toString(),
                    "enabled",
                    httpShutdown.settings());
        } else {
            String message = "disabled";
            if (settings.getHttpShutdownFile() != null) {
                message += " (file not found: " + settings.getHttpShutdownFile().getAbsolutePath() + ")";
            }
            return String.format(sb.toString(), message);
        }
    }

    private final Settings settings;

    public SettingsInfo(Settings settings) {
        this.settings = settings;
    }

    public String info() {
        StringBuilder txt = new StringBuilder();
        appendInfoSection(txt, ENV_SECTION_TITLE, DEFAULT_MAP_SUMMARY.execute(settings.getEnvironment()));
        appendInfoSection(txt, WORKING_DIR_SECTION_TITLE, settings.getWorkingDirectory() != null ?
        settings.getWorkingDirectory().getAbsolutePath() : "<default> (current directory)" );
        appendInfoSection(txt, COMMAND_LINE_SECTION_TITLE, settings.commandLine());
        appendInfoSection(txt, HTTP_SHUTDOWN_SECTION_TITLE, httpShutdownInfo(settings));
        return txt.toString();
    }

    @Override
    public String toString() {
        return info();
    }
}
