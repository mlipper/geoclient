package geoclientbuild.jarexec.exec;

/**
 * Unchecked exception indicating a configuration error.
 */
public class ConfigurationException extends RuntimeException {
    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

}
