package in.rishank.investmentplatform.pricing.controller;

import in.rishank.investmentplatform.pricing.service.PriceIngestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class IngestionController {

    private final PriceIngestionService ingestionService;

    public IngestionController(PriceIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public String ingest() {
        ingestionService.ingestDailyPrices();
        return "Ingestion done";
    }
}