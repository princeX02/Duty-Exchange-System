package duty.exchange.service;

import duty.exchange.dto.DutyExchangeRequestDTO;
import duty.exchange.dto.DutyExchangeResponseDTO;
import duty.exchange.model.ApprovalStatus;
import duty.exchange.model.DutyAssignment;
import duty.exchange.model.DutyExchangeRequest;
import duty.exchange.model.Faculty;
import duty.exchange.repository.DutyExchangeRepository;
import duty.exchange.validator.DutyExchangeValidator;
import duty.exchange.events.DutyExchangeRequestedEvent;
import duty.exchange.exception.InvalidDutyExchangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Main service for duty exchange operations
 */
@Service
@Transactional
public class DutyExchangeService {
    
    @Autowired
    private DutyExchangeRepository dutyExchangeRepository;
    
    @Autowired
    private ValidationService validationService;
    
    @Autowired
    private DutyExchangeValidator validator;
    
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    
    /**
     * Create a new duty exchange request
     */
    public DutyExchangeResponseDTO createDutyExchangeRequest(
            DutyExchangeRequestDTO requestDTO, Long requesterId) {
        
        // Validate and fetch entities
        Faculty requester = validationService.validateAndGetFaculty(requesterId);
        Faculty recipient = validationService.validateAndGetFaculty(
            requestDTO.getRecipientFacultyId()
        );
        DutyAssignment requesterDuty = validationService.validateAndGetDutyAssignment(
            requestDTO.getRequesterDutyId()
        );
        DutyAssignment recipientDuty = validationService.validateAndGetDutyAssignment(
            requestDTO.getRecipientDutyId()
        );
        
        // Validate requester owns the requester duty
        validationService.validateFacultyOwnsDuty(requester, requesterDuty);
        
        // Validate the exchange
        validator.validateDutyExchange(requester, recipient, requesterDuty, recipientDuty);
        
        // Check for existing pending request
        List<DutyExchangeRequest> existingRequests = dutyExchangeRepository
            .findByRequesterAndStatus(requester, ApprovalStatus.PENDING);
        
        for (DutyExchangeRequest existing : existingRequests) {
            if (existing.getRequesterDuty().getId().equals(requesterDuty.getId()) ||
                existing.getRecipientDuty().getId().equals(recipientDuty.getId())) {
                throw new InvalidDutyExchangeException(
                    "A pending exchange request already exists for one of these duties"
                );
            }
        }
        
        // Create new request
        DutyExchangeRequest request = new DutyExchangeRequest(
            requester, recipient, requesterDuty, recipientDuty, requestDTO.getReason()
        );
        
        DutyExchangeRequest savedRequest = dutyExchangeRepository.save(request);
        
        // Publish event
        eventPublisher.publishEvent(new DutyExchangeRequestedEvent(this, savedRequest));
        
        return convertToResponseDTO(savedRequest);
    }
    
    /**
     * Get duty exchange request by ID
     */
    public DutyExchangeResponseDTO getDutyExchangeRequest(Long requestId) {
        DutyExchangeRequest request = dutyExchangeRepository.findById(requestId)
            .orElseThrow(() -> new InvalidDutyExchangeException(
                "Duty exchange request not found with ID: " + requestId
            ));
        return convertToResponseDTO(request);
    }
    
    /**
     * Get duty exchange request entity by ID
     */
    public DutyExchangeRequest getDutyExchangeRequestEntity(Long requestId) {
        return dutyExchangeRepository.findById(requestId)
            .orElseThrow(() -> new InvalidDutyExchangeException(
                "Duty exchange request not found with ID: " + requestId
            ));
    }
    
    /**
     * Get all duty exchange requests for a faculty member
     */
    public List<DutyExchangeResponseDTO> getDutyExchangeRequestsByFaculty(Long facultyId) {
        Faculty faculty = validationService.validateAndGetFaculty(facultyId);
        List<DutyExchangeRequest> requests = dutyExchangeRepository
            .findAllActiveRequestsByFaculty(faculty);
        return requests.stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Get all pending duty exchange requests
     */
    public List<DutyExchangeResponseDTO> getPendingRequests() {
        List<DutyExchangeRequest> requests = dutyExchangeRepository
            .findPendingApprovalRequests(ApprovalStatus.PENDING);
        return requests.stream()
            .map(this::convertToResponseDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Cancel a duty exchange request
     */
    public DutyExchangeResponseDTO cancelRequest(Long requestId, Long facultyId) {
        DutyExchangeRequest request = dutyExchangeRepository.findById(requestId)
            .orElseThrow(() -> new InvalidDutyExchangeException(
                "Duty exchange request not found with ID: " + requestId
            ));
        
        Faculty faculty = validationService.validateAndGetFaculty(facultyId);
        
        // Only requester can cancel
        if (!request.getRequester().getId().equals(faculty.getId())) {
            throw new InvalidDutyExchangeException(
                "Only the requester can cancel the request"
            );
        }
        
        if (request.getStatus() != ApprovalStatus.PENDING) {
            throw new InvalidDutyExchangeException(
                "Only pending requests can be cancelled"
            );
        }
        
        request.setStatus(ApprovalStatus.CANCELLED);
        request.setIsActive(false);
        
        DutyExchangeRequest savedRequest = dutyExchangeRepository.save(request);
        return convertToResponseDTO(savedRequest);
    }
    
    /**
     * Convert entity to DTO
     */
    private DutyExchangeResponseDTO convertToResponseDTO(DutyExchangeRequest request) {
        DutyExchangeResponseDTO dto = new DutyExchangeResponseDTO();
        dto.setId(request.getId());
        dto.setRequestId(request.getRequestId());
        
        // Requester details
        dto.setRequesterId(request.getRequester().getId());
        dto.setRequesterName(request.getRequester().getFullName());
        dto.setRequesterEmail(request.getRequester().getEmail());
        
        // Recipient details
        dto.setRecipientId(request.getRecipient().getId());
        dto.setRecipientName(request.getRecipient().getFullName());
        dto.setRecipientEmail(request.getRecipient().getEmail());
        
        // Requester duty details
        dto.setRequesterDutyId(request.getRequesterDuty().getId());
        dto.setRequesterDutyDate(request.getRequesterDuty().getDutyDate());
        dto.setRequesterDutyStartTime(request.getRequesterDuty().getStartTime());
        dto.setRequesterDutyEndTime(request.getRequesterDuty().getEndTime());
        dto.setRequesterDutyVenue(request.getRequesterDuty().getVenue());
        
        // Recipient duty details
        dto.setRecipientDutyId(request.getRecipientDuty().getId());
        dto.setRecipientDutyDate(request.getRecipientDuty().getDutyDate());
        dto.setRecipientDutyStartTime(request.getRecipientDuty().getStartTime());
        dto.setRecipientDutyEndTime(request.getRecipientDuty().getEndTime());
        dto.setRecipientDutyVenue(request.getRecipientDuty().getVenue());
        
        // Request details
        dto.setReason(request.getReason());
        dto.setStatus(request.getStatus());
        
        // Approval details
        if (request.getApprover() != null) {
            dto.setApproverId(request.getApprover().getId());
            dto.setApproverName(request.getApprover().getFullName());
        }
        dto.setApprovalComments(request.getApprovalComments());
        dto.setRequestedAt(request.getRequestedAt());
        dto.setApprovedAt(request.getApprovedAt());
        dto.setRejectedAt(request.getRejectedAt());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());
        
        return dto;
    }
}
