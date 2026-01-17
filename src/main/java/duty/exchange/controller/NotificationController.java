package duty.exchange.controller;

import duty.exchange.notification.InAppNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for notification operations
 */
@RestController
@RequestMapping("/api/duty-exchange/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    
    @Autowired
    private InAppNotificationService notificationService;
    
    /**
     * Get all notifications for a user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InAppNotificationService.Notification>> getNotifications(
            @PathVariable Long userId) {
        
        List<InAppNotificationService.Notification> notifications = 
            notificationService.getNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    
    /**
     * Get unread notifications count for a user
     */
    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        long count = notificationService.getUnreadCount(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("unreadCount", count);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Mark notification as read
     */
    @PutMapping("/user/{userId}/read/{notificationId}")
    public ResponseEntity<Map<String, String>> markAsRead(
            @PathVariable Long userId,
            @PathVariable Long notificationId) {
        
        notificationService.markAsRead(userId, notificationId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Notification marked as read");
        return ResponseEntity.ok(response);
    }
    
    /**
     * Clear all notifications for a user
     */
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, String>> clearNotifications(@PathVariable Long userId) {
        notificationService.clearNotifications(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "All notifications cleared");
        return ResponseEntity.ok(response);
    }
}
