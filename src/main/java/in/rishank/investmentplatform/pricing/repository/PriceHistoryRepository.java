package in.rishank.investmentplatform.pricing.repository;

import in.rishank.investmentplatform.pricing.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    List<PriceHistory> findByAsset_IdOrderByDateAsc(Long assetId);

    List<PriceHistory> findByAsset_IdInOrderByAsset_IdAscDateAsc(List<Long> assetIds);
}
