package duty.exchange.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Entity representing a duty assignment for a faculty member
 */
@Entity
@Table(name = "duty_assignments")
public class DutyAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", nullable = false)
    private Faculty faculty;
    
    @Column(nullable = false)
    private LocalDate dutyDate;
    
    @Column(nullable = false)
    private LocalTime startTime;
    
    @Column(nullable = false)
    private LocalTime endTime;
    
    @Column(nullable = false)
    private String venue;
    
    @Column(nullable = false)
    private String dutyType; // EXAM, INVIGILATION, EVALUATION, etc.
    
    @Column(nullable = false)
    private String subject;
    
    @Column(nullable = false)
    private String courseCode;
    
    @Column(nullable = false)
    private String semester;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = AssignmentStatus.ASSIGNED;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public DutyAssignment() {
    }
    
    public DutyAssignment(Faculty faculty, LocalDate dutyDate, LocalTime startTime, 
                         LocalTime endTime, String venue, String dutyType, 
                         String subject, String courseCode, String semester) {
        this.faculty = faculty;
        this.dutyDate = dutyDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.dutyType = dutyType;
        this.subject = subject;
        this.courseCode = courseCode;
        this.semester = semester;
        this.status = AssignmentStatus.ASSIGNED;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Faculty getFaculty() {
        return faculty;
    }
    
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
    
    public LocalDate getDutyDate() {
        return dutyDate;
    }
    
    public void setDutyDate(LocalDate dutyDate) {
        this.dutyDate = dutyDate;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    
    public String getVenue() {
        return venue;
    }
    
    public void setVenue(String venue) {
        this.venue = venue;
    }
    
    public String getDutyType() {
        return dutyType;
    }
    
    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getSemester() {
        return semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }
    
    public AssignmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AssignmentStatus status) {
        this.status = status;
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
    
    /**
     * Enum for assignment status
     */
    public enum AssignmentStatus {
        ASSIGNED,
        EXCHANGED,
        COMPLETED,
        CANCELLED
    }
}
