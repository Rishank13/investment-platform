package in.rishank.investmentplatform.ranking.service;

import in.rishank.investmentplatform.analytics.dto.AnalyticsResponse;
import in.rishank.investmentplatform.analytics.service.AnalyticsService;
import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.asset.service.AssetService;
import in.rishank.investmentplatform.common.exception.InsufficientDataException;
import in.rishank.investmentplatform.common.exception.InvalidMetricException;
import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.repository.PriceHistoryRepository;
import in.rishank.investmentplatform.ranking.dto.RankingResponse;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RankingService {

    private final AssetService assetService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final AnalyticsService analyticsService;


    public RankingService(AssetService assetService, PriceHistoryRepository priceHistoryRepository,
                          AnalyticsService analyticsService) {
        this.assetService = assetService;
        this.priceHistoryRepository = priceHistoryRepository;
        this.analyticsService = analyticsService;
    }

    public List<RankingResponse> getTopAssets(int period, String metric,
                                              String category, int page, int limit) {
        List<Asset> assets = assetService.getAll();
        if(category != null){
            assets = assets.stream().filter(a ->
                            category.equalsIgnoreCase(a.getCategory()))
                            .toList();
        }

        if (page < 1) {
            throw new IllegalArgumentException("Page must be >= 1");
        }

        if (limit <= 0) {
            throw new IllegalArgumentException("Limit must be > 0");
        }

        List<RankingResponse> result = new ArrayList<>();

        if (assets.isEmpty()) return Collections.emptyList();

        //Extracting IDs
        List<Long> assetIds = assets.stream().map(Asset::getId).toList();

        //Single DB call
        List<PriceHistory> allPrices =
                priceHistoryRepository.findByAsset_IdInOrderByAsset_IdAscDateAsc(assetIds);

        Map<Long, List<PriceHistory>> priceMap =
                allPrices.stream()
                        .collect(Collectors.groupingBy(
                                p -> p.getAsset().getId(),
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        for (Asset asset : assets) {

            List<PriceHistory> prices = priceMap.get(asset.getId());

            //handle null case
            if (prices == null || prices.isEmpty()) continue;

            //NO DB CALL HERE
            try {
                AnalyticsResponse analytics =
                        analyticsService.calculate(prices, period);


                double value;

                switch (metric.toLowerCase()) {
                    case "cagr" -> value = analytics.getCagr();
                    case "return" -> value = analytics.getReturnPercentage();
                    case "volatility" -> value = analytics.getVolatility();
                    case "score" -> value = calculateScore(analytics, asset);
                    default -> throw new InvalidMetricException("Invalid metric");
                }

                RankingResponse r = new RankingResponse();
                r.setAssetId(asset.getId());
                r.setName(asset.getName());
                r.setValue(value);

                result.add(r);

            } catch (Exception e) {
                continue; //skip bad asset (if no enough data for particular asset)
            }
        }

        if (result.isEmpty()) {
            throw new InsufficientDataException("Not enough data for any asset");
        }

        if (metric.equals("volatility")) {
            result.sort(Comparator.comparingDouble(RankingResponse::getValue));
        } else {
            result.sort((a, b)
                    -> Double.compare(b.getValue(), a.getValue()));
        }

        int start = (page-1) * limit;
        //where a particular page starts, e.g. pg=3: start = 2*10 = 20th asset is first on page 3

        int end = Math.min(start+limit, result.size());
        //where page end, e.g. 20+10 = 30th asset is last on page 3

        if (start >= result.size()) {
            return Collections.emptyList();
        }

        return result.subList(start, end);
    }

    private double calculateScore(AnalyticsResponse a, Asset asset) {

        return (0.5 * a.getCagr())
                - (0.3 * a.getVolatility())
                - (0.2 * asset.getExpenseRatio());
    }
}
