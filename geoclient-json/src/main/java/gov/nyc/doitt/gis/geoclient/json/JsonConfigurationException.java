package gov.nyc.doitt.gis.geoclient.json;

/**
 * Exception thrown to indicate a configuration error specific to JSON processing
 * within the application.
 *
 * <p>This exception is a runtime exception and can be used to signal issues
 * such as invalid or missing configuration parameters related to JSON handling.</p>
 *
 * @see RuntimeException
 */
public class JsonConfigurationException extends RuntimeException {

    /**
     * Constructs a new {@code JsonConfigurationException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public JsonConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code JsonConfigurationException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param t the cause of the exception (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public JsonConfigurationException(String message, Throwable t) {
        super(message, t);
    }
}
