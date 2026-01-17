package duty.exchange.events;

import duty.exchange.model.DutyExchangeRequest;
import org.springframework.context.ApplicationEvent;

/**
 * Event published when a duty exchange request is rejected
 */
public class DutyExchangeRejectedEvent extends ApplicationEvent {
    
    private final DutyExchangeRequest request;
    
    public DutyExchangeRejectedEvent(Object source, DutyExchangeRequest request) {
        super(source);
        this.request = request;
    }
    
    public DutyExchangeRequest getRequest() {
        return request;
    }
}
