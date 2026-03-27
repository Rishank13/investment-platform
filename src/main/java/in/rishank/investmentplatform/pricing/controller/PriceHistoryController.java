package in.rishank.investmentplatform.pricing.controller;

import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.service.PriceHistoryService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceHistoryController {
    private final PriceHistoryService service;

    public PriceHistoryController(PriceHistoryService service){
        this.service = service;
    }

    @PostMapping
    public PriceHistory addPrice(
            @RequestParam Long assetId,
            @RequestParam Double price,
            @RequestParam String date) {

        return service.addPrice(assetId, price, LocalDate.parse(date));
    }

    @GetMapping("/{assetId}")
    public List<PriceHistory> getPrices(@PathVariable Long assetId){
        return service.getPriceHistory(assetId);
    }
}
