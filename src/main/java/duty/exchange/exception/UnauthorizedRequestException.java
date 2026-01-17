package duty.exchange.exception;

/**
 * Exception thrown when a user tries to perform an unauthorized action
 */
public class UnauthorizedRequestException extends RuntimeException {
    
    public UnauthorizedRequestException(String message) {
        super(message);
    }
    
    public UnauthorizedRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
