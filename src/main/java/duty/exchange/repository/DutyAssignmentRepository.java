package duty.exchange.repository;

import duty.exchange.model.DutyAssignment;
import duty.exchange.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for DutyAssignment entity
 */
@Repository
public interface DutyAssignmentRepository extends JpaRepository<DutyAssignment, Long> {
    
    List<DutyAssignment> findByFaculty(Faculty faculty);
    
    List<DutyAssignment> findByFacultyAndDutyDate(Faculty faculty, LocalDate dutyDate);
    
    List<DutyAssignment> findByDutyDate(LocalDate dutyDate);
    
    List<DutyAssignment> findByStatus(DutyAssignment.AssignmentStatus status);
    
    @Query("SELECT da FROM DutyAssignment da WHERE da.faculty = :faculty " +
           "AND da.dutyDate BETWEEN :startDate AND :endDate " +
           "AND da.status = :status")
    List<DutyAssignment> findByFacultyAndDateRangeAndStatus(
            @Param("faculty") Faculty faculty,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("status") DutyAssignment.AssignmentStatus status
    );
    
    @Query("SELECT da FROM DutyAssignment da WHERE da.dutyDate = :date " +
           "AND da.venue = :venue " +
           "AND da.status = 'ASSIGNED'")
    List<DutyAssignment> findConflictingAssignments(
            @Param("date") LocalDate date,
            @Param("venue") String venue
    );
    
    @Query("SELECT da FROM DutyAssignment da WHERE da.faculty = :faculty " +
           "AND da.dutyDate >= :fromDate " +
           "ORDER BY da.dutyDate ASC, da.startTime ASC")
    List<DutyAssignment> findUpcomingDutiesByFaculty(
            @Param("faculty") Faculty faculty,
            @Param("fromDate") LocalDate fromDate
    );
}
