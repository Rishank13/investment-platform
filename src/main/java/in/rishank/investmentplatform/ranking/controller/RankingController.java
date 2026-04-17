package in.rishank.investmentplatform.ranking.controller;

import in.rishank.investmentplatform.ranking.dto.RankingResponse;
import in.rishank.investmentplatform.ranking.service.RankingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rankings")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public List<RankingResponse> getRanking(@RequestParam int period,
                                            @RequestParam String metric,
                                            @RequestParam(required = false) String category,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int limit){
        return rankingService.getTopAssets(period, metric, category, page, limit);
    }

    @GetMapping("/top-assets")
    public List<RankingResponse> getTopAssetsSimple(
            @RequestParam int duration
    ){
        return rankingService.getTopAssets(duration, "return", null, 1, 10);
    }

    @GetMapping("/low-volatility")
    public List<RankingResponse> getLowVolatilitySimple(
            @RequestParam int duration
    ){
        return rankingService.getTopAssets(duration, "volatility", null, 1, 10);
    }

    @GetMapping("/high-cagr")
    public List<RankingResponse> getHighCagrSimple(
            @RequestParam int duration
    ){
        return rankingService.getTopAssets(duration, "cagr", null, 1, 10);
    }

    @GetMapping("/consistent")
    public List<RankingResponse> getConsistentSimple(){
        return rankingService.getTopAssets(5, "score", null, 1, 10);
    }
}
