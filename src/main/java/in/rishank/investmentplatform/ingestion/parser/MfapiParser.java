package in.rishank.investmentplatform.ingestion.parser;

import in.rishank.investmentplatform.ingestion.dto.ExternalNavResponse;
import in.rishank.investmentplatform.ingestion.dto.NavData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MfapiParser {

    public static NavData map(ExternalNavResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ExternalNavResponse.ApiNavData apiData = response.getData().get(0);

        NavData navData = new NavData();
        navData.setNav(Double.parseDouble(apiData.getNav()));
        navData.setDate(LocalDate.parse(apiData.getDate(), formatter));

        return navData;
    }
}