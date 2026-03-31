package in.rishank.investmentplatform.pricing.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.asset.service.AssetService;
import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceIngestionService {

    private final AssetService assetService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final NavService navService;

    public PriceIngestionService(AssetService assetService, PriceHistoryRepository priceHistoryRepository, NavService navService) {
        this.assetService = assetService;
        this.priceHistoryRepository = priceHistoryRepository;
        this.navService = navService;
    }

    public void ingestDailyPrices(){

        List<Asset> assets = assetService.getAll();
        List<PriceHistory> prices = new ArrayList<>();

        for(Asset asset : assets){

            double nav = navService.fetchNav(asset.getName());

            PriceHistory ph = new PriceHistory();
            ph.setAsset(asset);
            ph.setDate(LocalDate.now());
            ph.setPrice(nav);

            boolean exists = priceHistoryRepository.existsByAssetAndDate(asset, LocalDate.now());

            if (!exists) {
                prices.add(ph);
            }
        }
        priceHistoryRepository.saveAll(prices);
    }
}
