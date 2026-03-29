package in.rishank.investmentplatform.ranking.service;

import in.rishank.investmentplatform.analytics.dto.AnalyticsResponse;
import in.rishank.investmentplatform.analytics.service.AnalyticsService;
import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.asset.service.AssetService;
import in.rishank.investmentplatform.common.exception.InvalidMetricException;
import in.rishank.investmentplatform.ranking.dto.RankingResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class RankingService {

    private final AssetService assetService;
    private final AnalyticsService analyticsService;


    public RankingService(AssetService assetService,
                          AnalyticsService analyticsService) {
        this.assetService = assetService;
        this.analyticsService = analyticsService;
    }

    public List<RankingResponse> getTopAssets(int period, String metric){
        List<Asset> assets = assetService.getAll();
        List<RankingResponse> result = new ArrayList<>();

        for(Asset asset : assets){
            AnalyticsResponse analytics =
                    analyticsService.getAnalytics(asset.getId(), period);

            double value;

            if(metric.equalsIgnoreCase("cagr")){
                value = analytics.getCagr();
            }
            else if(metric.equalsIgnoreCase("return")){
                value = analytics.getReturnPercentage();
            }
            else if(metric.equalsIgnoreCase("volatility")){
                value = analytics.getVolatility();
            }
            else if(metric.equalsIgnoreCase("score")){
                value = calculateScore(analytics, asset);
            }
            else {
                throw new InvalidMetricException("Invalid metric");
            }

            RankingResponse r = new RankingResponse();
            r.setAssetId(asset.getId());
            r.setName(asset.getName());
            r.setVale(value);

            result.add(r);
        }

        if(metric.equalsIgnoreCase("volatility")){
            result.sort(Comparator.comparingDouble(RankingResponse::getVale));
        } else {
            result.sort((a, b)
                    -> Double.compare(b.getVale(), a.getVale()));
        }

        return result;
    }

    private double calculateScore(AnalyticsResponse a, Asset asset){

        return (0.5 * a.getCagr())
                - (0.3 * a.getVolatility())
                - (0.2 * asset.getExpenseRatio());
    }
}
