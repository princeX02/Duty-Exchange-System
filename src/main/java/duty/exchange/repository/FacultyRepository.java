package duty.exchange.repository;

import duty.exchange.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Faculty entity
 */
@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    
    Optional<Faculty> findByEmployeeId(String employeeId);
    
    Optional<Faculty> findByEmail(String email);
    
    List<Faculty> findByDepartment(String department);
    
    List<Faculty> findByStatus(Faculty.FacultyStatus status);
    
    @Query("SELECT f FROM Faculty f WHERE f.department = :department AND f.status = :status")
    List<Faculty> findByDepartmentAndStatus(@Param("department") String department, 
                                             @Param("status") Faculty.FacultyStatus status);
    
    boolean existsByEmployeeId(String employeeId);
    
    boolean existsByEmail(String email);
}
