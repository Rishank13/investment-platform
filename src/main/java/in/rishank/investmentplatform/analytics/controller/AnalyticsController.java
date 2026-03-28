package in.rishank.investmentplatform.analytics.controller;

import in.rishank.investmentplatform.analytics.dto.AnalyticsResponse;
import in.rishank.investmentplatform.analytics.service.AnalyticsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(AnalyticsService service) {
        this.service = service;
    }

    @GetMapping("/{assetId}")
    public AnalyticsResponse getAnalytics(@PathVariable Long assetId,
                                          @RequestParam int period){
        return service.getAnalytics(assetId, period);
    }
}
