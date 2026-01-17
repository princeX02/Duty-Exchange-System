package duty.exchange.notification;

import duty.exchange.model.DutyExchangeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for in-app notifications
 * Note: In a production system, this would typically use a database or message queue
 */
@Service
public class InAppNotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(InAppNotificationService.class);
    
    // In-memory storage for notifications (replace with database in production)
    private final Map<Long, List<Notification>> notifications = new ConcurrentHashMap<>();
    
    /**
     * Send notification when a duty exchange request is created
     */
    public void sendRequestCreatedNotification(DutyExchangeRequest request) {
        // Notify recipient
        addNotification(
            request.getRecipient().getId(),
            "New Duty Exchange Request",
            "You have received a duty exchange request from " + 
            request.getRequester().getFullName(),
            NotificationType.INFO,
            request.getId()
        );
        
        // Notify requester
        addNotification(
            request.getRequester().getId(),
            "Request Submitted",
            "Your duty exchange request has been submitted successfully",
            NotificationType.SUCCESS,
            request.getId()
        );
        
        logger.info("In-app notifications sent for request: {}", request.getRequestId());
    }
    
    /**
     * Send notification when a duty exchange request is approved
     */
    public void sendRequestApprovedNotification(DutyExchangeRequest request) {
        // Notify requester
        addNotification(
            request.getRequester().getId(),
            "Request Approved",
            "Your duty exchange request has been approved by " + 
            request.getApprover().getFullName(),
            NotificationType.SUCCESS,
            request.getId()
        );
        
        // Notify recipient
        addNotification(
            request.getRecipient().getId(),
            "Request Approved",
            "The duty exchange request has been approved",
            NotificationType.SUCCESS,
            request.getId()
        );
        
        logger.info("Approval in-app notifications sent for request: {}", 
                   request.getRequestId());
    }
    
    /**
     * Send notification when a duty exchange request is rejected
     */
    public void sendRequestRejectedNotification(DutyExchangeRequest request) {
        // Notify requester
        addNotification(
            request.getRequester().getId(),
            "Request Rejected",
            "Your duty exchange request has been rejected. " +
            (request.getApprovalComments() != null ? 
             "Reason: " + request.getApprovalComments() : ""),
            NotificationType.WARNING,
            request.getId()
        );
        
        // Notify recipient
        addNotification(
            request.getRecipient().getId(),
            "Request Rejected",
            "The duty exchange request has been rejected",
            NotificationType.INFO,
            request.getId()
        );
        
        logger.info("Rejection in-app notifications sent for request: {}", 
                   request.getRequestId());
    }
    
    /**
     * Get all notifications for a user
     */
    public List<Notification> getNotifications(Long userId) {
        return notifications.getOrDefault(userId, new ArrayList<>());
    }
    
    /**
     * Get unread notifications count for a user
     */
    public long getUnreadCount(Long userId) {
        return notifications.getOrDefault(userId, new ArrayList<>())
            .stream()
            .filter(n -> !n.isRead())
            .count();
    }
    
    /**
     * Mark notification as read
     */
    public void markAsRead(Long userId, Long notificationId) {
        List<Notification> userNotifications = notifications.get(userId);
        if (userNotifications != null) {
            userNotifications.stream()
                .filter(n -> n.getId().equals(notificationId))
                .findFirst()
                .ifPresent(n -> n.setRead(true));
        }
    }
    
    /**
     * Clear all notifications for a user
     */
    public void clearNotifications(Long userId) {
        notifications.remove(userId);
    }
    
    private void addNotification(Long userId, String title, String message, 
                                NotificationType type, Long requestId) {
        notifications.computeIfAbsent(userId, k -> new ArrayList<>())
            .add(new Notification(title, message, type, requestId));
    }
    
    /**
     * Notification model
     */
    public static class Notification {
        private static long nextId = 1;
        private Long id;
        private String title;
        private String message;
        private NotificationType type;
        private Long requestId;
        private boolean read;
        private long timestamp;
        
        public Notification(String title, String message, NotificationType type, Long requestId) {
            this.id = nextId++;
            this.title = title;
            this.message = message;
            this.type = type;
            this.requestId = requestId;
            this.read = false;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Getters and Setters
        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public NotificationType getType() { return type; }
        public Long getRequestId() { return requestId; }
        public boolean isRead() { return read; }
        public void setRead(boolean read) { this.read = read; }
        public long getTimestamp() { return timestamp; }
    }
    
    /**
     * Notification type enum
     */
    public enum NotificationType {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }
}
