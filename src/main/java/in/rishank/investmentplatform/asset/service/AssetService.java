package in.rishank.investmentplatform.asset.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.common.exception.ResourceNotFoundException;
import in.rishank.investmentplatform.asset.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AssetService {

    private final AssetRepository repo;
    private static final Logger log = LoggerFactory.getLogger(AssetService.class);

    public AssetService(AssetRepository repo) {
        this.repo = repo;
    }

    public Asset save(Asset asset){
        return repo.save(asset);
    }

    public List<Asset> getAll(){
        return repo.findAll();
    }

    public Asset getById(Long id){
        return repo.findById(id).orElseThrow(() -> {
            log.warn("Asset not found with id {}", id);
            return new ResourceNotFoundException("Asset not found");
        });
    }

    public Asset update(Long id, Asset updatedAsset){
        Asset existing = repo.findById(id).orElse(null);

        if(existing == null){
            log.warn("Cannot update. Asset not found with id {}", id);
            return null;
        }else{
            existing.setName(updatedAsset.getName());
            existing.setAssetType(updatedAsset.getAssetType());
            existing.setCategory(updatedAsset.getCategory());
            existing.setExpenseRatio(updatedAsset.getExpenseRatio());

            return repo.save(existing);
        }
    }

    public void delete(Long id){
        repo.deleteById(id);
    }
}
