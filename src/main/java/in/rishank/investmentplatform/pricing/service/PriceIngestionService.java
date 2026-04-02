package in.rishank.investmentplatform.pricing.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.asset.service.AssetService;
import in.rishank.investmentplatform.common.exception.NavFetchException;
import in.rishank.investmentplatform.pricing.dto.NavData;
import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import in.rishank.investmentplatform.pricing.repository.PriceHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceIngestionService {

    private final AssetService assetService;
    private final PriceHistoryRepository priceHistoryRepository;
    private final NavService navService;
    private static final Logger log = LoggerFactory.getLogger(PriceIngestionService.class);

    public PriceIngestionService(AssetService assetService, PriceHistoryRepository priceHistoryRepository, NavService navService) {
        this.assetService = assetService;
        this.priceHistoryRepository = priceHistoryRepository;
        this.navService = navService;
    }

    public void ingestDailyPrices(){
        log.info("Starting daily price ingestion");

        List<Asset> assets = assetService.getAll();
        List<PriceHistory> prices = new ArrayList<>();

        int success = 0;
        int failed = 0;
        int exist = 0;

        for(Asset asset : assets){

            try {
                NavData navData = navService.fetchNav(asset);

                PriceHistory ph = new PriceHistory();
                ph.setAsset(asset);
                ph.setDate(navData.getDate());
                ph.setPrice(navData.getNav());

                boolean exists = priceHistoryRepository.existsByAssetAndDate(asset, navData.getDate());

                if (!exists) {
                    success++;
                    prices.add(ph);
                }else{
                    exist++;
                    log.debug("Price already exists for asset {} on {}",
                            asset.getName(), navData.getDate());
                }
            } catch (NavFetchException e) {
                failed++;
                log.warn("Failed to fetch NAV for asset {}", asset.getName(), e);
            }
        }
        priceHistoryRepository.saveAll(prices);
        log.info("Ingestion summary: success={}, exist={}, failed={}", success, exist, failed);
        log.info("Completed daily price ingestion");
    }
}
