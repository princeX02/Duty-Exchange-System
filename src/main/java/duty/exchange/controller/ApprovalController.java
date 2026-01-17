package duty.exchange.controller;

import duty.exchange.dto.ApprovalRequestDTO;
import duty.exchange.dto.DutyExchangeResponseDTO;
import duty.exchange.model.Faculty;
import duty.exchange.service.ApprovalWorkflowService;
import duty.exchange.service.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for approval operations
 */
@RestController
@RequestMapping("/api/duty-exchange/approvals")
@CrossOrigin(origins = "*")
@Tag(name = "Approvals", description = "APIs for approving or rejecting duty exchange requests")
public class ApprovalController {
    
    @Autowired
    private ApprovalWorkflowService approvalWorkflowService;
    
    @Autowired
    private ValidationService validationService;
    
    /**
     * Approve a duty exchange request
     */
    @Operation(summary = "Approve request", 
               description = "Approver (Dean/HOD) approves a pending duty exchange request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Request approved successfully"),
        @ApiResponse(responseCode = "400", description = "Request not in pending state"),
        @ApiResponse(responseCode = "404", description = "Request not found")
    })
    @PostMapping("/approve")
    public ResponseEntity<DutyExchangeResponseDTO> approveRequest(
            @Valid @RequestBody ApprovalRequestDTO approvalDTO,
            @Parameter(description = "ID of the approving faculty", required = true)
            @RequestHeader("X-User-Id") Long approverId) {
        
        Faculty approver = validationService.validateAndGetFaculty(approverId);
        var request = approvalWorkflowService.approveRequest(
            approvalDTO.getRequestId(), approver, approvalDTO.getComments()
        );
        
        DutyExchangeResponseDTO response = convertToResponseDTO(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Reject a duty exchange request
     */
    @PostMapping("/reject")
    public ResponseEntity<DutyExchangeResponseDTO> rejectRequest(
            @Valid @RequestBody ApprovalRequestDTO approvalDTO,
            @RequestHeader("X-User-Id") Long approverId) {
        
        Faculty approver = validationService.validateAndGetFaculty(approverId);
        var request = approvalWorkflowService.rejectRequest(
            approvalDTO.getRequestId(), approver, approvalDTO.getComments()
        );
        
        DutyExchangeResponseDTO response = convertToResponseDTO(request);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Convert entity to DTO
     */
    private DutyExchangeResponseDTO convertToResponseDTO(
            duty.exchange.model.DutyExchangeRequest request) {
        DutyExchangeResponseDTO dto = new DutyExchangeResponseDTO();
        dto.setId(request.getId());
        dto.setRequestId(request.getRequestId());
        dto.setRequesterId(request.getRequester().getId());
        dto.setRequesterName(request.getRequester().getFullName());
        dto.setRequesterEmail(request.getRequester().getEmail());
        dto.setRecipientId(request.getRecipient().getId());
        dto.setRecipientName(request.getRecipient().getFullName());
        dto.setRecipientEmail(request.getRecipient().getEmail());
        dto.setRequesterDutyId(request.getRequesterDuty().getId());
        dto.setRequesterDutyDate(request.getRequesterDuty().getDutyDate());
        dto.setRequesterDutyStartTime(request.getRequesterDuty().getStartTime());
        dto.setRequesterDutyEndTime(request.getRequesterDuty().getEndTime());
        dto.setRequesterDutyVenue(request.getRequesterDuty().getVenue());
        dto.setRecipientDutyId(request.getRecipientDuty().getId());
        dto.setRecipientDutyDate(request.getRecipientDuty().getDutyDate());
        dto.setRecipientDutyStartTime(request.getRecipientDuty().getStartTime());
        dto.setRecipientDutyEndTime(request.getRecipientDuty().getEndTime());
        dto.setRecipientDutyVenue(request.getRecipientDuty().getVenue());
        dto.setReason(request.getReason());
        dto.setStatus(request.getStatus());
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
