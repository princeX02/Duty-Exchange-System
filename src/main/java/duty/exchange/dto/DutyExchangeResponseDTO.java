package duty.exchange.dto;

import duty.exchange.model.ApprovalStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO for duty exchange request response
 */
public class DutyExchangeResponseDTO {
    
    private Long id;
    private String requestId;
    private Long requesterId;
    private String requesterName;
    private String requesterEmail;
    private Long recipientId;
    private String recipientName;
    private String recipientEmail;
    private Long requesterDutyId;
    private LocalDate requesterDutyDate;
    private LocalTime requesterDutyStartTime;
    private LocalTime requesterDutyEndTime;
    private String requesterDutyVenue;
    private Long recipientDutyId;
    private LocalDate recipientDutyDate;
    private LocalTime recipientDutyStartTime;
    private LocalTime recipientDutyEndTime;
    private String recipientDutyVenue;
    private String reason;
    private ApprovalStatus status;
    private Long approverId;
    private String approverName;
    private String approvalComments;
    private LocalDateTime requestedAt;
    private LocalDateTime approvedAt;
    private LocalDateTime rejectedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    public Long getRequesterId() {
        return requesterId;
    }
    
    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }
    
    public String getRequesterName() {
        return requesterName;
    }
    
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }
    
    public String getRequesterEmail() {
        return requesterEmail;
    }
    
    public void setRequesterEmail(String requesterEmail) {
        this.requesterEmail = requesterEmail;
    }
    
    public Long getRecipientId() {
        return recipientId;
    }
    
    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
    
    public String getRecipientName() {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }
    
    public String getRecipientEmail() {
        return recipientEmail;
    }
    
    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }
    
    public Long getRequesterDutyId() {
        return requesterDutyId;
    }
    
    public void setRequesterDutyId(Long requesterDutyId) {
        this.requesterDutyId = requesterDutyId;
    }
    
    public LocalDate getRequesterDutyDate() {
        return requesterDutyDate;
    }
    
    public void setRequesterDutyDate(LocalDate requesterDutyDate) {
        this.requesterDutyDate = requesterDutyDate;
    }
    
    public LocalTime getRequesterDutyStartTime() {
        return requesterDutyStartTime;
    }
    
    public void setRequesterDutyStartTime(LocalTime requesterDutyStartTime) {
        this.requesterDutyStartTime = requesterDutyStartTime;
    }
    
    public LocalTime getRequesterDutyEndTime() {
        return requesterDutyEndTime;
    }
    
    public void setRequesterDutyEndTime(LocalTime requesterDutyEndTime) {
        this.requesterDutyEndTime = requesterDutyEndTime;
    }
    
    public String getRequesterDutyVenue() {
        return requesterDutyVenue;
    }
    
    public void setRequesterDutyVenue(String requesterDutyVenue) {
        this.requesterDutyVenue = requesterDutyVenue;
    }
    
    public Long getRecipientDutyId() {
        return recipientDutyId;
    }
    
    public void setRecipientDutyId(Long recipientDutyId) {
        this.recipientDutyId = recipientDutyId;
    }
    
    public LocalDate getRecipientDutyDate() {
        return recipientDutyDate;
    }
    
    public void setRecipientDutyDate(LocalDate recipientDutyDate) {
        this.recipientDutyDate = recipientDutyDate;
    }
    
    public LocalTime getRecipientDutyStartTime() {
        return recipientDutyStartTime;
    }
    
    public void setRecipientDutyStartTime(LocalTime recipientDutyStartTime) {
        this.recipientDutyStartTime = recipientDutyStartTime;
    }
    
    public LocalTime getRecipientDutyEndTime() {
        return recipientDutyEndTime;
    }
    
    public void setRecipientDutyEndTime(LocalTime recipientDutyEndTime) {
        this.recipientDutyEndTime = recipientDutyEndTime;
    }
    
    public String getRecipientDutyVenue() {
        return recipientDutyVenue;
    }
    
    public void setRecipientDutyVenue(String recipientDutyVenue) {
        this.recipientDutyVenue = recipientDutyVenue;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public ApprovalStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }
    
    public Long getApproverId() {
        return approverId;
    }
    
    public void setApproverId(Long approverId) {
        this.approverId = approverId;
    }
    
    public String getApproverName() {
        return approverName;
    }
    
    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }
    
    public String getApprovalComments() {
        return approvalComments;
    }
    
    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }
    
    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }
    
    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }
    
    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }
    
    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }
    
    public LocalDateTime getRejectedAt() {
        return rejectedAt;
    }
    
    public void setRejectedAt(LocalDateTime rejectedAt) {
        this.rejectedAt = rejectedAt;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
