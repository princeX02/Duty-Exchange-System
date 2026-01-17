package duty.exchange.service;

import duty.exchange.model.DutyAssignment;
import duty.exchange.model.Faculty;
import duty.exchange.repository.DutyAssignmentRepository;
import duty.exchange.repository.FacultyRepository;
import duty.exchange.exception.InvalidDutyExchangeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for validation operations
 */
@Service
@Transactional
public class ValidationService {
    
    @Autowired
    private FacultyRepository facultyRepository;
    
    @Autowired
    private DutyAssignmentRepository dutyAssignmentRepository;
    
    /**
     * Validate and fetch faculty by ID
     */
    public Faculty validateAndGetFaculty(Long facultyId) {
        return facultyRepository.findById(facultyId)
            .orElseThrow(() -> new InvalidDutyExchangeException(
                "Faculty not found with ID: " + facultyId
            ));
    }
    
    /**
     * Validate and fetch duty assignment by ID
     */
    public DutyAssignment validateAndGetDutyAssignment(Long dutyId) {
        return dutyAssignmentRepository.findById(dutyId)
            .orElseThrow(() -> new InvalidDutyExchangeException(
                "Duty assignment not found with ID: " + dutyId
            ));
    }
    
    /**
     * Validate that faculty owns the duty assignment
     */
    public void validateFacultyOwnsDuty(Faculty faculty, DutyAssignment duty) {
        if (!duty.getFaculty().getId().equals(faculty.getId())) {
            throw new InvalidDutyExchangeException(
                "Faculty does not own this duty assignment"
            );
        }
    }
}
