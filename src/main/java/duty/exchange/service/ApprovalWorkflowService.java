package duty.exchange.service;

import duty.exchange.model.ApprovalStatus;
import duty.exchange.model.DutyAssignment;
import duty.exchange.model.DutyExchangeRequest;
import duty.exchange.model.Faculty;
import duty.exchange.repository.DutyAssignmentRepository;
import duty.exchange.repository.DutyExchangeRepository;
import duty.exchange.events.DutyExchangeApprovedEvent;
import duty.exchange.events.DutyExchangeRejectedEvent;
import duty.exchange.exception.ApprovalNotFoundException;
import duty.exchange.exception.InvalidDutyExchangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for handling approval workflow
 */
@Service
@Transactional
public class ApprovalWorkflowService {
    
    @Autowired
    private DutyExchangeRepository dutyExchangeRepository;
    
    @Autowired
    private DutyAssignmentRepository dutyAssignmentRepository;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    /**
     * Approve a duty exchange request
     */
    public DutyExchangeRequest approveRequest(Long requestId, Faculty approver, String comments) {
        DutyExchangeRequest request = dutyExchangeRepository.findById(requestId)
            .orElseThrow(() -> new ApprovalNotFoundException(
                "Duty exchange request not found with ID: " + requestId
            ));
        
        if (request.getStatus() != ApprovalStatus.PENDING) {
            throw new InvalidDutyExchangeException(
                "Only pending requests can be approved"
            );
        }
        
        if (!request.getIsActive()) {
            throw new InvalidDutyExchangeException(
                "Cannot approve an inactive request"
            );
        }
        
        // Update request status
        request.setStatus(ApprovalStatus.APPROVED);
        request.setApprover(approver);
        request.setApprovalComments(comments);
        request.setApprovedAt(LocalDateTime.now());
        
        // Exchange the duties
        exchangeDuties(request);
        
        // Save updated request
        DutyExchangeRequest savedRequest = dutyExchangeRepository.save(request);
        
        // Publish approval event
        eventPublisher.publishEvent(new DutyExchangeApprovedEvent(this, savedRequest));
        
        return savedRequest;
    }
    
    /**
     * Reject a duty exchange request
     */
    public DutyExchangeRequest rejectRequest(Long requestId, Faculty approver, String comments) {
        DutyExchangeRequest request = dutyExchangeRepository.findById(requestId)
            .orElseThrow(() -> new ApprovalNotFoundException(
                "Duty exchange request not found with ID: " + requestId
            ));
        
        if (request.getStatus() != ApprovalStatus.PENDING) {
            throw new InvalidDutyExchangeException(
                "Only pending requests can be rejected"
            );
        }
        
        // Update request status
        request.setStatus(ApprovalStatus.REJECTED);
        request.setApprover(approver);
        request.setApprovalComments(comments);
        request.setRejectedAt(LocalDateTime.now());
        
        // Save updated request
        DutyExchangeRequest savedRequest = dutyExchangeRepository.save(request);
        
        // Publish rejection event
        eventPublisher.publishEvent(new DutyExchangeRejectedEvent(this, savedRequest));
        
        return savedRequest;
    }
    
    /**
     * Exchange the duties between requester and recipient
     */
    private void exchangeDuties(DutyExchangeRequest request) {
        DutyAssignment requesterDuty = request.getRequesterDuty();
        DutyAssignment recipientDuty = request.getRecipientDuty();
        
        // Swap faculty assignments
        Faculty requester = request.getRequester();
        Faculty recipient = request.getRecipient();
        
        requesterDuty.setFaculty(recipient);
        requesterDuty.setStatus(DutyAssignment.AssignmentStatus.EXCHANGED);
        
        recipientDuty.setFaculty(requester);
        recipientDuty.setStatus(DutyAssignment.AssignmentStatus.EXCHANGED);
        
        // Save updated assignments
        dutyAssignmentRepository.save(requesterDuty);
        dutyAssignmentRepository.save(recipientDuty);
    }
}
