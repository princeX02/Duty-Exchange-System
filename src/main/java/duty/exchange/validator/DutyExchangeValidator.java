package duty.exchange.validator;

import duty.exchange.exception.InvalidDutyExchangeException;
import duty.exchange.model.DutyAssignment;
import duty.exchange.model.Faculty;
import duty.exchange.util.DateUtils;
import org.springframework.stereotype.Component;

/**
 * Validator for duty exchange requests
 */
@Component
public class DutyExchangeValidator {
    
    /**
     * Validate that a duty exchange request is valid
     */
    public void validateDutyExchange(Faculty requester, Faculty recipient,
                                     DutyAssignment requesterDuty,
                                     DutyAssignment recipientDuty) {
        
        // Validate requester and recipient are different
        if (requester.getId().equals(recipient.getId())) {
            throw new InvalidDutyExchangeException(
                "Requester and recipient cannot be the same person"
            );
        }
        
        // Validate requester owns the requester duty
        if (!requesterDuty.getFaculty().getId().equals(requester.getId())) {
            throw new InvalidDutyExchangeException(
                "Requester does not own the specified duty assignment"
            );
        }
        
        // Validate recipient owns the recipient duty
        if (!recipientDuty.getFaculty().getId().equals(recipient.getId())) {
            throw new InvalidDutyExchangeException(
                "Recipient does not own the specified duty assignment"
            );
        }
        
        // Validate duties are not on the same date and time (would be pointless)
        if (requesterDuty.getDutyDate().equals(recipientDuty.getDutyDate()) &&
            DateUtils.isTimeOverlapping(
                requesterDuty.getStartTime(), requesterDuty.getEndTime(),
                recipientDuty.getStartTime(), recipientDuty.getEndTime()
            )) {
            throw new InvalidDutyExchangeException(
                "Cannot exchange duties that occur at the same time"
            );
        }
        
        // Validate duty dates are in the future
        if (!DateUtils.isDutyDateValidForExchange(requesterDuty.getDutyDate())) {
            throw new InvalidDutyExchangeException(
                "Requester duty date must be at least 24 hours in the future"
            );
        }
        
        if (!DateUtils.isDutyDateValidForExchange(recipientDuty.getDutyDate())) {
            throw new InvalidDutyExchangeException(
                "Recipient duty date must be at least 24 hours in the future"
            );
        }
        
        // Validate both faculties are active
        if (requester.getStatus() != Faculty.FacultyStatus.ACTIVE) {
            throw new InvalidDutyExchangeException(
                "Requester faculty is not active"
            );
        }
        
        if (recipient.getStatus() != Faculty.FacultyStatus.ACTIVE) {
            throw new InvalidDutyExchangeException(
                "Recipient faculty is not active"
            );
        }
        
        // Validate duty assignments are in ASSIGNED status
        if (requesterDuty.getStatus() != DutyAssignment.AssignmentStatus.ASSIGNED) {
            throw new InvalidDutyExchangeException(
                "Requester duty assignment is not in ASSIGNED status"
            );
        }
        
        if (recipientDuty.getStatus() != DutyAssignment.AssignmentStatus.ASSIGNED) {
            throw new InvalidDutyExchangeException(
                "Recipient duty assignment is not in ASSIGNED status"
            );
        }
    }
}
