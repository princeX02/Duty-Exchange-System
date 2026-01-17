package duty.exchange.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a duty exchange request between two faculty members
 */
@Entity
@Table(name = "duty_exchange_requests")
public class DutyExchangeRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String requestId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_faculty_id", nullable = false)
    private Faculty requester;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_faculty_id", nullable = false)
    private Faculty recipient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_duty_id", nullable = false)
    private DutyAssignment requesterDuty;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_duty_id", nullable = false)
    private DutyAssignment recipientDuty;
    
    @Column(nullable = false, length = 1000)
    private String reason;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private Faculty approver;
    
    @Column(length = 500)
    private String approvalComments;
    
    @Column(nullable = false)
    private LocalDateTime requestedAt;
    
    @Column
    private LocalDateTime approvedAt;
    
    @Column
    private LocalDateTime rejectedAt;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(nullable = false)
    private Boolean isActive;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        requestedAt = LocalDateTime.now();
        if (status == null) {
            status = ApprovalStatus.PENDING;
        }
        if (isActive == null) {
            isActive = true;
        }
        if (requestId == null) {
            requestId = "DER-" + System.currentTimeMillis();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public DutyExchangeRequest() {
    }
    
    public DutyExchangeRequest(Faculty requester, Faculty recipient, 
                              DutyAssignment requesterDuty, 
                              DutyAssignment recipientDuty, String reason) {
        this.requester = requester;
        this.recipient = recipient;
        this.requesterDuty = requesterDuty;
        this.recipientDuty = recipientDuty;
        this.reason = reason;
        this.status = ApprovalStatus.PENDING;
        this.isActive = true;
        this.requestId = "DER-" + System.currentTimeMillis();
    }
    
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
    
    public Faculty getRequester() {
        return requester;
    }
    
    public void setRequester(Faculty requester) {
        this.requester = requester;
    }
    
    public Faculty getRecipient() {
        return recipient;
    }
    
    public void setRecipient(Faculty recipient) {
        this.recipient = recipient;
    }
    
    public DutyAssignment getRequesterDuty() {
        return requesterDuty;
    }
    
    public void setRequesterDuty(DutyAssignment requesterDuty) {
        this.requesterDuty = requesterDuty;
    }
    
    public DutyAssignment getRecipientDuty() {
        return recipientDuty;
    }
    
    public void setRecipientDuty(DutyAssignment recipientDuty) {
        this.recipientDuty = recipientDuty;
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
    
    public Faculty getApprover() {
        return approver;
    }
    
    public void setApprover(Faculty approver) {
        this.approver = approver;
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
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
