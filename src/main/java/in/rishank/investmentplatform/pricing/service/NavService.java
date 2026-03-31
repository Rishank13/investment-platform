package in.rishank.investmentplatform.pricing.service;

import org.springframework.stereotype.Service;

@Service
public class NavService {

    public double fetchNav(String assetName){
        return 100 + Math.random() * 50;
    }
}
