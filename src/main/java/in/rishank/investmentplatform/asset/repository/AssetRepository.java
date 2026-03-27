package in.rishank.investmentplatform.asset.repository;

import in.rishank.investmentplatform.asset.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

}
