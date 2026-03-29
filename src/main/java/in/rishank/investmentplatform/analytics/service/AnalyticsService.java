package in.rishank.investmentplatform.analytics.service;

import in.rishank.investmentplatform.analytics.dto.AnalyticsResponse;
import in.rishank.investmentplatform.common.exception.InsufficientDataException;
import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.service.PriceHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AnalyticsService {

    private final PriceHistoryService priceService;

    public AnalyticsService(PriceHistoryService priceService) {
        this.priceService = priceService;
    }

    public AnalyticsResponse getAnalytics(Long assetId, int years){

        List<PriceHistory> prices = priceService.getPriceHistory(assetId);

        return calculate(prices, years);
    }

    public AnalyticsResponse calculate(List<PriceHistory> prices, int years){
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(years);

        //Filtered price history according to period
        List<PriceHistory> filtered = prices.stream()
                .filter(p -> !p.getDate().isBefore(startDate) &&
                        !p.getDate().isAfter(endDate))
                .toList();

        if(filtered.size() < 2){
            throw new InsufficientDataException("Not enough data");
        }

        //Generating Returns list
        List<Double> returns = new ArrayList<>();

        for(int i=1; i < filtered.size(); i++){
            double prev = filtered.get(i-1).getPrice();
            double curr = filtered.get(i).getPrice();

            double r = (curr - prev) / prev;
            returns.add(r);
        }

        //Edge case
        if(returns.size() < 2){
            throw new InsufficientDataException("Not enough data");
        }

        //Mean return
        double mean = returns.stream()
                .mapToDouble(Double::doubleValue)
                .average().orElse(0.0);

        //Variance
        double variance = 0.0;

        for(double r : returns){
            variance += Math.pow(r-mean, 2);
        }

        variance = variance / returns.size();

        //Standard Deviation (Volatility)
        double volatility = Math.sqrt(variance);

        volatility = volatility * 100;
        volatility = Math.round(volatility * 100.0) / 100.0;

        //calculating overall return for the given period
        PriceHistory start = filtered.get(0);
        PriceHistory end = filtered.get(filtered.size() - 1);

        double startPrice = start.getPrice();
        double endPrice = end.getPrice();

        double returnPercentage =
                Math.round(((endPrice - startPrice) / startPrice) * 100 * 100.0) / 100.0;

        //CAGR
        double cagr =
                Math.pow((endPrice / startPrice), (1.0 / years)) - 1;

        cagr = cagr * 100;
        cagr = Math.round(cagr * 100.0) / 100.0;

        //Setting response
        AnalyticsResponse response = new AnalyticsResponse();
        response.setStartPrice(startPrice);
        response.setEndPrice(endPrice);
        response.setReturnPercentage(returnPercentage);
        response.setCagr(cagr);
        response.setVolatility(volatility);

        return response;
    }
}
