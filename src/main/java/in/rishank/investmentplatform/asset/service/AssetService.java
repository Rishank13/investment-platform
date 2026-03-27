package in.rishank.investmentplatform.asset.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.common.exception.ResourceNotFoundException;
import in.rishank.investmentplatform.asset.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository repo;

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
        return repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Asset not found"));
    }

    public Asset update(Long id, Asset updatedAsset){
        Asset existing = repo.findById(id).orElse(null);

        if(existing == null){
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
