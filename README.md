# Duty Exchange System

## Overview

The Duty Exchange System is Module 5 of the Automated Examinations Management System. This module enables faculty members to request and exchange their examination duties with other faculty members, with an approval workflow and automated notifications.

## Features

- **Duty Exchange Requests**: Faculty members can create requests to exchange duties with other faculty
- **Approval Workflow**: Requests go through an approval process before being finalized
- **Validation**: Comprehensive validation ensures data integrity and prevents invalid exchanges
- **Notifications**: Email and in-app notifications for all request status changes
- **B-Form Generation**: Automatic PDF generation for approved exchanges
- **Event-Driven Architecture**: Asynchronous event processing for better performance

## Technology Stack

- **Java**: Core programming language
- **Spring Boot**: Framework for building the application
- **Spring Data JPA**: Data persistence layer
- **Jakarta Persistence**: JPA implementation
- **iText PDF**: PDF generation library
- **Spring Mail**: Email notification support

## Project Structure

```
duty-exchange/
│
├── controller/          # REST API controllers
│   ├── DutyExchangeController.java
│   ├── ApprovalController.java
│   └── NotificationController.java
│
├── service/            # Business logic layer
│   ├── DutyExchangeService.java
│   ├── ValidationService.java
│   ├── ApprovalWorkflowService.java
│   └── BFormGenerationService.java
│
├── repository/         # Data access layer
│   ├── DutyExchangeRepository.java
│   ├── FacultyRepository.java
│   └── DutyAssignmentRepository.java
│
├── model/             # Entity classes
│   ├── DutyExchangeRequest.java
│   ├── DutyAssignment.java
│   ├── Faculty.java
│   └── ApprovalStatus.java
│
├── dto/               # Data Transfer Objects
│   ├── DutyExchangeRequestDTO.java
│   ├── ApprovalRequestDTO.java
│   └── DutyExchangeResponseDTO.java
│
├── workflow/          # Workflow handlers
│   └── DutyExchangeWorkflow.java
│
├── validator/         # Validation logic
│   └── DutyExchangeValidator.java
│
├── notification/      # Notification services
│   ├── EmailNotificationService.java
│   └── InAppNotificationService.java
│
├── events/            # Event classes
│   ├── DutyExchangeRequestedEvent.java
│   ├── DutyExchangeApprovedEvent.java
│   └── DutyExchangeRejectedEvent.java
│
├── exception/         # Custom exceptions
│   ├── InvalidDutyExchangeException.java
│   ├── UnauthorizedRequestException.java
│   └── ApprovalNotFoundException.java
│
├── config/            # Configuration classes
│   └── DutyExchangeConfig.java
│
└── util/              # Utility classes
    ├── DateUtils.java
    └── PdfGeneratorUtil.java
```

## API Endpoints

### Duty Exchange Endpoints

- `POST /api/duty-exchange/requests` - Create a new duty exchange request
- `GET /api/duty-exchange/requests/{requestId}` - Get request by ID
- `GET /api/duty-exchange/requests/faculty/{facultyId}` - Get all requests for a faculty
- `GET /api/duty-exchange/requests/pending` - Get all pending requests
- `PUT /api/duty-exchange/requests/{requestId}/cancel` - Cancel a request

### Approval Endpoints

- `POST /api/duty-exchange/approvals/approve` - Approve a request
- `POST /api/duty-exchange/approvals/reject` - Reject a request

### Notification Endpoints

- `GET /api/duty-exchange/notifications/user/{userId}` - Get user notifications
- `GET /api/duty-exchange/notifications/user/{userId}/unread-count` - Get unread count
- `PUT /api/duty-exchange/notifications/user/{userId}/read/{notificationId}` - Mark as read
- `DELETE /api/duty-exchange/notifications/user/{userId}` - Clear notifications

## Request Headers

All endpoints require the following header for authentication:
- `X-User-Id`: The ID of the current user (faculty member)

## Usage Examples

### Create a Duty Exchange Request

```json
POST /api/duty-exchange/requests
Headers: X-User-Id: 1
Body:
{
  "requesterDutyId": 10,
  "recipientFacultyId": 2,
  "recipientDutyId": 20,
  "reason": "Personal emergency"
}
```

### Approve a Request

```json
POST /api/duty-exchange/approvals/approve
Headers: X-User-Id: 3
Body:
{
  "requestId": 1,
  "status": "APPROVED",
  "comments": "Approved as per policy"
}
```

## Business Rules

1. **Duty Exchange Validation**:
   - Requester and recipient must be different
   - Both duties must be owned by respective faculty members
   - Duties cannot occur at the same time
   - Duty dates must be at least 24 hours in the future
   - Both faculty members must be active
   - Duties must be in ASSIGNED status

2. **Approval Process**:
   - Only pending requests can be approved or rejected
   - Upon approval, duties are automatically exchanged
   - B-Form PDF can be generated for approved requests

3. **Notifications**:
   - Email and in-app notifications sent for all status changes
   - Recipient notified when request is created
   - Both parties notified on approval/rejection

## Database Schema

### Faculty Table
- id (Primary Key)
- employee_id (Unique)
- first_name
- last_name
- email (Unique)
- department
- designation
- phone_number
- status
- created_at
- updated_at

### Duty Assignment Table
- id (Primary Key)
- faculty_id (Foreign Key)
- duty_date
- start_time
- end_time
- venue
- duty_type
- subject
- course_code
- semester
- status
- created_at
- updated_at

### Duty Exchange Request Table
- id (Primary Key)
- request_id (Unique)
- requester_faculty_id (Foreign Key)
- recipient_faculty_id (Foreign Key)
- requester_duty_id (Foreign Key)
- recipient_duty_id (Foreign Key)
- reason
- status
- approver_id (Foreign Key)
- approval_comments
- requested_at
- approved_at
- rejected_at
- created_at
- updated_at
- is_active

## Configuration

### Email Configuration

To enable email notifications, configure Spring Mail in `application.properties`:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Async Processing

The system uses async processing for events. Configure thread pool in `DutyExchangeConfig.java`.

## Dependencies

Add the following dependencies to your `pom.xml` or `build.gradle`:

### Maven (pom.xml)

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>com.itextpdf</groupId>
        <artifactId>itextpdf</artifactId>
        <version>5.5.13.3</version>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### Gradle (build.gradle)

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-mail'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'com.itextpdf:itextpdf:5.5.13.3'
    runtimeOnly 'com.h2database:h2'
}
```

## Error Handling

The system includes custom exceptions:
- `InvalidDutyExchangeException`: Thrown when exchange request is invalid
- `UnauthorizedRequestException`: Thrown for unauthorized actions
- `ApprovalNotFoundException`: Thrown when approval request not found

## Future Enhancements

1. Database-backed notification system
2. Role-based access control
3. Advanced reporting and analytics
4. Integration with calendar systems
5. Mobile app support
6. Real-time notifications using WebSocket

## Contributing

When contributing to this module:
1. Follow the existing code structure
2. Maintain test coverage
3. Update documentation
4. Follow Java coding conventions

## License

This module is part of the Automated Examinations Management System.

## Contact

For questions or issues, please contact the development team.
