package in.rishank.investmentplatform.ingestion.strategy;

import in.rishank.investmentplatform.ingestion.dto.NavData;

import java.util.List;

public interface NavFetcher {
    List<NavData> fetch();
}
