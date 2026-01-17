package duty.exchange.exception;

/**
 * Exception thrown when an approval request is not found
 */
public class ApprovalNotFoundException extends RuntimeException {
    
    public ApprovalNotFoundException(String message) {
        super(message);
    }
    
    public ApprovalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
