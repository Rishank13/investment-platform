package in.rishank.investmentplatform.service;

import in.rishank.investmentplatform.entity.Asset;
import in.rishank.investmentplatform.exception.ResourceNotFoundException;
import in.rishank.investmentplatform.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository repo;

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
