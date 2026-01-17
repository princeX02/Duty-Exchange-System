package duty.exchange.notification;

import duty.exchange.model.DutyExchangeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for sending email notifications
 */
@Service
public class EmailNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    /**
     * Send notification when a duty exchange request is created
     */
    public void sendRequestCreatedNotification(DutyExchangeRequest request) {
        try {
            // Send to recipient
            sendEmail(
                request.getRecipient().getEmail(),
                "New Duty Exchange Request",
                buildRequestCreatedMessage(request, true)
            );
            
            // Send to requester
            sendEmail(
                request.getRequester().getEmail(),
                "Duty Exchange Request Submitted",
                buildRequestCreatedMessage(request, false)
            );
            
            logger.info("Email notifications sent for request: {}", request.getRequestId());
        } catch (Exception e) {
            logger.error("Error sending email notifications for request: {}", 
                        request.getRequestId(), e);
        }
    }
    
    /**
     * Send notification when a duty exchange request is approved
     */
    public void sendRequestApprovedNotification(DutyExchangeRequest request) {
        try {
            // Send to requester
            sendEmail(
                request.getRequester().getEmail(),
                "Duty Exchange Request Approved",
                buildRequestApprovedMessage(request, true)
            );
            
            // Send to recipient
            sendEmail(
                request.getRecipient().getEmail(),
                "Duty Exchange Request Approved",
                buildRequestApprovedMessage(request, false)
            );
            
            logger.info("Approval email notifications sent for request: {}", 
                       request.getRequestId());
        } catch (Exception e) {
            logger.error("Error sending approval email notifications for request: {}", 
                        request.getRequestId(), e);
        }
    }
    
    /**
     * Send notification when a duty exchange request is rejected
     */
    public void sendRequestRejectedNotification(DutyExchangeRequest request) {
        try {
            // Send to requester
            sendEmail(
                request.getRequester().getEmail(),
                "Duty Exchange Request Rejected",
                buildRequestRejectedMessage(request)
            );
            
            // Send to recipient
            sendEmail(
                request.getRecipient().getEmail(),
                "Duty Exchange Request Rejected",
                buildRequestRejectedMessage(request)
            );
            
            logger.info("Rejection email notifications sent for request: {}", 
                       request.getRequestId());
        } catch (Exception e) {
            logger.error("Error sending rejection email notifications for request: {}", 
                        request.getRequestId(), e);
        }
    }
    
    private void sendEmail(String to, String subject, String body) {
        if (mailSender == null) {
            logger.warn("JavaMailSender not configured. Email not sent to: {}", to);
            return;
        }
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("princechaudhary4366@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
    
    private String buildRequestCreatedMessage(DutyExchangeRequest request, boolean isRecipient) {
        StringBuilder message = new StringBuilder();
        if (isRecipient) {
            message.append("Dear ").append(request.getRecipient().getFirstName()).append(",\n\n");
            message.append("You have received a new duty exchange request from ");
            message.append(request.getRequester().getFullName()).append(".\n\n");
        } else {
            message.append("Dear ").append(request.getRequester().getFirstName()).append(",\n\n");
            message.append("Your duty exchange request has been submitted successfully.\n\n");
        }
        
        message.append("Request ID: ").append(request.getRequestId()).append("\n");
        message.append("Requester: ").append(request.getRequester().getFullName()).append("\n");
        message.append("Recipient: ").append(request.getRecipient().getFullName()).append("\n");
        message.append("Reason: ").append(request.getReason()).append("\n\n");
        message.append("Please log in to the system to view details and take action.\n\n");
        message.append("Best regards,\nDuty Exchange System");
        
        return message.toString();
    }
    
    private String buildRequestApprovedMessage(DutyExchangeRequest request, boolean isRequester) {
        StringBuilder message = new StringBuilder();
        String facultyName = isRequester ? 
            request.getRequester().getFirstName() : 
            request.getRecipient().getFirstName();
        
        message.append("Dear ").append(facultyName).append(",\n\n");
        message.append("Your duty exchange request has been approved.\n\n");
        message.append("Request ID: ").append(request.getRequestId()).append("\n");
        message.append("Approved by: ").append(request.getApprover().getFullName()).append("\n");
        if (request.getApprovalComments() != null) {
            message.append("Comments: ").append(request.getApprovalComments()).append("\n");
        }
        message.append("\nThe duty exchange has been processed and is now active.\n\n");
        message.append("Best regards,\nDuty Exchange System");
        
        return message.toString();
    }
    
    private String buildRequestRejectedMessage(DutyExchangeRequest request) {
        StringBuilder message = new StringBuilder();
        message.append("Dear ").append(request.getRequester().getFirstName()).append(",\n\n");
        message.append("Your duty exchange request has been rejected.\n\n");
        message.append("Request ID: ").append(request.getRequestId()).append("\n");
        if (request.getApprover() != null) {
            message.append("Rejected by: ").append(request.getApprover().getFullName()).append("\n");
        }
        if (request.getApprovalComments() != null) {
            message.append("Comments: ").append(request.getApprovalComments()).append("\n");
        }
        message.append("\nBest regards,\nDuty Exchange System");
        
        return message.toString();
    }
}
