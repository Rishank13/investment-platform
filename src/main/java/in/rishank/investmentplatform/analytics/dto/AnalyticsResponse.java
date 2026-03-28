package in.rishank.investmentplatform.analytics.dto;

public class AnalyticsResponse {

    private Double startPrice;
    private Double endPrice;
    private Double returnPercentage;
    private Double cagr;
    private Double volatility;

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Double endPrice) {
        this.endPrice = endPrice;
    }

    public Double getReturnPercentage() {
        return returnPercentage;
    }

    public void setReturnPercentage(Double returnPercentage) {
        this.returnPercentage = returnPercentage;
    }

    public Double getCagr() {
        return cagr;
    }

    public void setCagr(Double cagr) {
        this.cagr = cagr;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }
}
