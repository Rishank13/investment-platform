package in.rishank.investmentplatform.ranking.dto;

public class RankingResponse {

    private Long assetId;
    private String name;
    private Double vale;

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVale() {
        return vale;
    }

    public void setVale(Double vale) {
        this.vale = vale;
    }
}
