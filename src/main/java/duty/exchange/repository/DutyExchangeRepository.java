package duty.exchange.repository;

import duty.exchange.model.ApprovalStatus;
import duty.exchange.model.DutyExchangeRequest;
import duty.exchange.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for DutyExchangeRequest entity
 */
@Repository
public interface DutyExchangeRepository extends JpaRepository<DutyExchangeRequest, Long> {
    
    Optional<DutyExchangeRequest> findByRequestId(String requestId);
    
    List<DutyExchangeRequest> findByRequester(Faculty requester);
    
    List<DutyExchangeRequest> findByRecipient(Faculty recipient);
    
    List<DutyExchangeRequest> findByStatus(ApprovalStatus status);
    
    Page<DutyExchangeRequest> findByStatus(ApprovalStatus status, Pageable pageable);
    
    List<DutyExchangeRequest> findByRequesterAndStatus(Faculty requester, ApprovalStatus status);
    
    List<DutyExchangeRequest> findByRecipientAndStatus(Faculty recipient, ApprovalStatus status);
    
    @Query("SELECT der FROM DutyExchangeRequest der WHERE " +
           "(der.requester = :faculty OR der.recipient = :faculty) " +
           "AND der.isActive = true " +
           "ORDER BY der.requestedAt DESC")
    List<DutyExchangeRequest> findAllActiveRequestsByFaculty(@Param("faculty") Faculty faculty);
    
    @Query("SELECT der FROM DutyExchangeRequest der WHERE " +
           "der.status = :status " +
           "AND der.isActive = true " +
           "ORDER BY der.requestedAt DESC")
    List<DutyExchangeRequest> findPendingApprovalRequests(@Param("status") ApprovalStatus status);
    
    @Query("SELECT der FROM DutyExchangeRequest der WHERE " +
           "der.requestedAt BETWEEN :startDate AND :endDate " +
           "AND der.isActive = true")
    List<DutyExchangeRequest> findRequestsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
    
    @Query("SELECT COUNT(der) FROM DutyExchangeRequest der WHERE " +
           "der.requester = :faculty " +
           "AND der.status = :status " +
           "AND der.isActive = true")
    Long countByRequesterAndStatus(@Param("faculty") Faculty faculty, 
                                   @Param("status") ApprovalStatus status);
}
