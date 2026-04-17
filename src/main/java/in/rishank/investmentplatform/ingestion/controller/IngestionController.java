package in.rishank.investmentplatform.ingestion.controller;

import in.rishank.investmentplatform.ingestion.service.NavIngestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class IngestionController {

    private final NavIngestionService ingestionService;

    public IngestionController(NavIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @PostMapping("/ingest")
    public String ingest() {
        ingestionService.ingestDailyPrices();
        return "Ingestion done";
    }
}