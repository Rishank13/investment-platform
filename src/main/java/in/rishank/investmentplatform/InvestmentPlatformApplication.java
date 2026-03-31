package in.rishank.investmentplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class InvestmentPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestmentPlatformApplication.class, args);
    }

}
