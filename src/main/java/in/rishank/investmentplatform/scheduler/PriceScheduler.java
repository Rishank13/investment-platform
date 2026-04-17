package in.rishank.investmentplatform.scheduler;

import in.rishank.investmentplatform.ingestion.service.NavIngestionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceScheduler {

    private final NavIngestionService ingestionService;

    public PriceScheduler(NavIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void runDaily(){
        ingestionService.ingestDailyPrices();
    }
}
