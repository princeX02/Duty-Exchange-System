package duty.exchange.exception;

/**
 * Exception thrown when a duty exchange request is invalid
 */
public class InvalidDutyExchangeException extends RuntimeException {
    
    public InvalidDutyExchangeException(String message) {
        super(message);
    }
    
    public InvalidDutyExchangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
