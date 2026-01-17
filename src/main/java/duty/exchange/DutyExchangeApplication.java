package duty.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main Spring Boot Application class for Duty Exchange System
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "duty.exchange.repository")
public class DutyExchangeApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DutyExchangeApplication.class, args);
    }
}
