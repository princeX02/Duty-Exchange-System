package duty.exchange.workflow;

import duty.exchange.events.DutyExchangeApprovedEvent;
import duty.exchange.events.DutyExchangeRejectedEvent;
import duty.exchange.events.DutyExchangeRequestedEvent;
import duty.exchange.notification.EmailNotificationService;
import duty.exchange.notification.InAppNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Workflow handler for duty exchange events
 */
@Component
public class DutyExchangeWorkflow {
    
    private static final Logger logger = LoggerFactory.getLogger(DutyExchangeWorkflow.class);
    
    @Autowired
    private EmailNotificationService emailNotificationService;
    
    @Autowired
    private InAppNotificationService inAppNotificationService;
    
    /**
     * Handle duty exchange request created event
     */
    @EventListener
    @Async
    public void handleDutyExchangeRequested(DutyExchangeRequestedEvent event) {
        logger.info("Processing duty exchange requested event for request: {}", 
                   event.getRequest().getRequestId());
        
        try {
            // Send email notifications
            emailNotificationService.sendRequestCreatedNotification(event.getRequest());
            
            // Send in-app notifications
            inAppNotificationService.sendRequestCreatedNotification(event.getRequest());
            
            logger.info("Successfully processed duty exchange requested event");
        } catch (Exception e) {
            logger.error("Error processing duty exchange requested event", e);
        }
    }
    
    /**
     * Handle duty exchange approved event
     */
    @EventListener
    @Async
    public void handleDutyExchangeApproved(DutyExchangeApprovedEvent event) {
        logger.info("Processing duty exchange approved event for request: {}", 
                   event.getRequest().getRequestId());
        
        try {
            // Send email notifications
            emailNotificationService.sendRequestApprovedNotification(event.getRequest());
            
            // Send in-app notifications
            inAppNotificationService.sendRequestApprovedNotification(event.getRequest());
            
            logger.info("Successfully processed duty exchange approved event");
        } catch (Exception e) {
            logger.error("Error processing duty exchange approved event", e);
        }
    }
    
    /**
     * Handle duty exchange rejected event
     */
    @EventListener
    @Async
    public void handleDutyExchangeRejected(DutyExchangeRejectedEvent event) {
        logger.info("Processing duty exchange rejected event for request: {}", 
                   event.getRequest().getRequestId());
        
        try {
            // Send email notifications
            emailNotificationService.sendRequestRejectedNotification(event.getRequest());
            
            // Send in-app notifications
            inAppNotificationService.sendRequestRejectedNotification(event.getRequest());
            
            logger.info("Successfully processed duty exchange rejected event");
        } catch (Exception e) {
            logger.error("Error processing duty exchange rejected event", e);
        }
    }
}
