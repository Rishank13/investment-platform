package in.rishank.investmentplatform.ingestion.service;

import in.rishank.investmentplatform.asset.entity.Asset;
import in.rishank.investmentplatform.common.exception.NavFetchException;
import in.rishank.investmentplatform.config.NavApiConfig;
import in.rishank.investmentplatform.ingestion.dto.ExternalNavResponse;
import in.rishank.investmentplatform.ingestion.dto.NavData;
import in.rishank.investmentplatform.ingestion.parser.MfapiParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NavFetchService {

    private final RestTemplate restTemplate;
    private final NavApiConfig config;
    private static final Logger log = LoggerFactory.getLogger(NavFetchService.class);

    public NavFetchService(RestTemplate restTemplate, NavApiConfig config) {
        this.restTemplate = restTemplate;
        this.config = config;
    }

    public NavData fetchNav(Asset asset) {

        String url = config.getBaseUrl() + "/mf/" + asset.getSchemeCode() + "/latest";

        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            try {
                ExternalNavResponse response = restTemplate.getForObject(url, ExternalNavResponse.class);

                if (response == null) {
                    log.error("NAV API returned null for asset {}", asset.getName());
                    throw new NavFetchException("NAV API returned null for " + asset.getName());
                }

                ExternalNavResponse.ApiNavData apiData = response.getData().get(0);

                if (apiData.getNav() == null) {
                    log.error("NAV API returned NAV value = null for asset {}", asset.getName());
                    throw new NavFetchException("NAV API returned NAV value = null for " + asset.getName());
                }
                if (apiData.getDate() == null) {
                    log.warn("Null date for asset {}", asset.getName());
                    throw new NavFetchException("Null date for asset = " + asset.getName());
                }

                NavData navData = MfapiParser.map(response);

                return navData;

            } catch (RestClientException ex) {
                attempts++;

                if (attempts == maxAttempts) {
                    log.error("Failed to fetch NAV after {} attempts for asset {}",
                            maxAttempts, asset.getName(), ex);
                    throw new NavFetchException(
                            "Failed after " + maxAttempts + " attempts for " + asset.getName(),
                            ex
                    );
                }
            }
        }
        log.error("Unexpected error while fetching NAV for asset {}", asset.getName());
        throw new NavFetchException("Unexpected error");
    }
}