package in.rishank.investmentplatform.pricing.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.asset.service.AssetService;
import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceRepository;
    private final AssetService assetService;

    public PriceHistoryService(PriceHistoryRepository priceRepository,
                               AssetService assetService) {
        this.priceRepository = priceRepository;
        this.assetService = assetService;
    }

    public PriceHistory addPrice(Long assetId, Double price, LocalDate date){

        Asset asset = assetService.getById(assetId);//validation

        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setAsset(asset);
        priceHistory.setPrice(price);
        priceHistory.setDate(date);

        return priceRepository.save(priceHistory);
    }

    public List<PriceHistory> getPriceHistory(Long assetId){

        assetService.getById(assetId);//validation

        return priceRepository.findByAsset_IdOrderByDateAsc(assetId);
    }
}
