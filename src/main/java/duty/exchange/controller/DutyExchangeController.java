package duty.exchange.controller;

import duty.exchange.dto.DutyExchangeRequestDTO;
import duty.exchange.dto.DutyExchangeResponseDTO;
import duty.exchange.service.BFormGenerationService;
import duty.exchange.service.DutyExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for duty exchange operations
 */
@RestController
@RequestMapping("/api/duty-exchange")
@CrossOrigin(origins = "*")
@Tag(name = "Duty Exchange", description = "APIs for managing examination duty exchange requests")
public class DutyExchangeController {
    
    @Autowired
    private DutyExchangeService dutyExchangeService;
    
    @Autowired
    private BFormGenerationService bFormGenerationService;
    
    /**
     * Root endpoint - API information
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Duty Exchange System API");
        info.put("version", "1.0.0");
        info.put("status", "running");
        info.put("endpoints", Map.of(
            "GET /api/duty-exchange/requests/pending", "Get all pending requests",
            "GET /api/duty-exchange/requests/{requestId}", "Get request by ID",
            "GET /api/duty-exchange/requests/faculty/{facultyId}", "Get requests by faculty",
            "POST /api/duty-exchange/requests", "Create new request",
            "PUT /api/duty-exchange/requests/{requestId}/cancel", "Cancel request",
            "GET /api/duty-exchange/requests/{requestId}/bform", "Download B-Form PDF",
            "POST /api/duty-exchange/approvals/approve", "Approve request",
            "POST /api/duty-exchange/approvals/reject", "Reject request",
            "GET /api/duty-exchange/notifications/user/{userId}", "Get notifications"
        ));
        return ResponseEntity.ok(info);
    }
    
    /**
     * Create a new duty exchange request
     */
    @Operation(summary = "Create duty exchange request", 
               description = "Faculty member creates a new duty exchange request")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Request created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/requests")
    public ResponseEntity<DutyExchangeResponseDTO> createRequest(
            @Valid @RequestBody DutyExchangeRequestDTO requestDTO,
            @Parameter(description = "ID of the requesting faculty", required = true)
            @RequestHeader("X-User-Id") Long requesterId) {
        
        DutyExchangeResponseDTO response = dutyExchangeService
            .createDutyExchangeRequest(requestDTO, requesterId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    /**
     * Get duty exchange request by ID
     */
    @GetMapping("/requests/{requestId}")
    public ResponseEntity<DutyExchangeResponseDTO> getRequest(
            @PathVariable Long requestId) {
        
        DutyExchangeResponseDTO response = dutyExchangeService
            .getDutyExchangeRequest(requestId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get all duty exchange requests for a faculty member
     */
    @GetMapping("/requests/faculty/{facultyId}")
    public ResponseEntity<List<DutyExchangeResponseDTO>> getRequestsByFaculty(
            @PathVariable Long facultyId) {
        
        List<DutyExchangeResponseDTO> responses = dutyExchangeService
            .getDutyExchangeRequestsByFaculty(facultyId);
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get all pending duty exchange requests with pagination
     */
    @Operation(summary = "Get pending requests", 
               description = "Retrieves all pending duty exchange requests with pagination support")
    @GetMapping("/requests/pending")
    public ResponseEntity<Map<String, Object>> getPendingRequests(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field")
            @RequestParam(defaultValue = "requestedAt") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<DutyExchangeResponseDTO> responsePage = dutyExchangeService.getPendingRequestsPaginated(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("requests", responsePage.getContent());
        response.put("currentPage", responsePage.getNumber());
        response.put("totalItems", responsePage.getTotalElements());
        response.put("totalPages", responsePage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Cancel a duty exchange request
     */
    @PutMapping("/requests/{requestId}/cancel")
    public ResponseEntity<DutyExchangeResponseDTO> cancelRequest(
            @PathVariable Long requestId,
            @RequestHeader("X-User-Id") Long facultyId) {
        
        DutyExchangeResponseDTO response = dutyExchangeService
            .cancelRequest(requestId, facultyId);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Download B-Form PDF for approved request
     */
    @GetMapping("/requests/{requestId}/bform")
    public ResponseEntity<byte[]> downloadBForm(@PathVariable Long requestId) {
        var request = dutyExchangeService.getDutyExchangeRequest(requestId);
        
        // Convert DTO back to entity (in production, use a proper mapper)
        // For now, we'll need to fetch the entity
        var requestEntity = dutyExchangeService.getDutyExchangeRequestEntity(requestId);
        byte[] pdfBytes = bFormGenerationService.generateBForm(requestEntity);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", 
            "B-Form-" + request.getRequestId() + ".pdf");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
