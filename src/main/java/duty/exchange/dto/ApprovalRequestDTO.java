package duty.exchange.dto;

import duty.exchange.model.ApprovalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for approval/rejection of duty exchange requests
 */
public class ApprovalRequestDTO {
    
    @NotNull(message = "Request ID is required")
    private Long requestId;
    
    @NotNull(message = "Approval status is required")
    private ApprovalStatus status;
    
    @NotBlank(message = "Comments are required")
    private String comments;
    
    // Getters and Setters
    public Long getRequestId() {
        return requestId;
    }
    
    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }
    
    public ApprovalStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
}
