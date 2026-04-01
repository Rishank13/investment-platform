package in.rishank.investmentplatform.pricing.service;

import in.rishank.investmentplatform.common.exception.NavFetchException;
import in.rishank.investmentplatform.pricing.dto.ExternalNavResponse;
import in.rishank.investmentplatform.pricing.dto.NavData;
import in.rishank.investmentplatform.pricing.mapper.NavMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NavService {

    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(NavService.class);

    public NavService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NavData fetchNav(String assetName) {

        String url = "https://api.example.com/nav?asset=" + assetName;

        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                ExternalNavResponse response = restTemplate.getForObject(url, ExternalNavResponse.class);

                if (response == null) {
                    log.error("NAV API returned null for asset {}", assetName);
                    throw new NavFetchException("NAV API returned null for " + assetName);
                }
                if (response.getNav() == null) {
                    log.error("NAV API returned NAV value = null for asset {}", assetName);
                    throw new NavFetchException("NAV API returned NAV value = null for " + assetName);
                }
                if (response.getDate() == null) {
                    log.warn("Null date for asset {}", assetName);
                    throw new NavFetchException("Null date for asset = " + assetName);
                }

                NavData navData = NavMapper.map(response);

                return navData;

            } catch (RestClientException ex) {
                attempts++;

                if (attempts == maxAttempts) {
                    log.error("Failed to fetch NAV after {} attempts for asset {}",
                            maxAttempts, assetName, ex);
                    throw new NavFetchException(
                            "Failed after " + maxAttempts + " attempts for " + assetName,
                            ex
                    );
                }
            }
        }
        log.error("Unexpected error while fetching NAV for asset {}", assetName);
        throw new NavFetchException("Unexpected error");
    }
}