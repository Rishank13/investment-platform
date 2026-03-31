package in.rishank.investmentplatform.scheduler;

import in.rishank.investmentplatform.pricing.service.PriceIngestionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/ingest")
    public String ingest() {
        ingestionService.ingestDailyPrices();
        return "Ingestion done";
    }
}
