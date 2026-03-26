package in.rishank.investmentplatform.repository;

import in.rishank.investmentplatform.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {

}
