package in.rishank.investmentplatform.scheduler;

import in.rishank.investmentplatform.pricing.service.PriceIngestionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceScheduler {

    private final PriceIngestionService ingestionService;

    public PriceScheduler(PriceIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void runDaily(){
        ingestionService.ingestDailyPrices();
    }
}
