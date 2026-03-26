package in.rishank.investmentplatform.controller;

import in.rishank.investmentplatform.entity.Asset;
import in.rishank.investmentplatform.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
public class AssetController {

    @Autowired
    private AssetService service;

    @PostMapping
    public Asset create(@RequestBody Asset asset){
        return service.save(asset);
    }

    @GetMapping
    public List<Asset> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Asset getById(@PathVariable Long id){
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Asset update(@PathVariable Long id, @RequestBody Asset updatedAsset){
        return service.update(id, updatedAsset);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
