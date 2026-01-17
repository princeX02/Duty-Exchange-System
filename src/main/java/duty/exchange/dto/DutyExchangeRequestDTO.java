package duty.exchange.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating a duty exchange request
 */
public class DutyExchangeRequestDTO {
    
    @NotNull(message = "Requester duty ID is required")
    private Long requesterDutyId;
    
    @NotNull(message = "Recipient faculty ID is required")
    private Long recipientFacultyId;
    
    @NotNull(message = "Recipient duty ID is required")
    private Long recipientDutyId;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    // Getters and Setters
    public Long getRequesterDutyId() {
        return requesterDutyId;
    }
    
    public void setRequesterDutyId(Long requesterDutyId) {
        this.requesterDutyId = requesterDutyId;
    }
    
    public Long getRecipientFacultyId() {
        return recipientFacultyId;
    }
    
    public void setRecipientFacultyId(Long recipientFacultyId) {
        this.recipientFacultyId = recipientFacultyId;
    }
    
    public Long getRecipientDutyId() {
        return recipientDutyId;
    }
    
    public void setRecipientDutyId(Long recipientDutyId) {
        this.recipientDutyId = recipientDutyId;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
}
