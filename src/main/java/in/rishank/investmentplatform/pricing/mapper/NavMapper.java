package in.rishank.investmentplatform.pricing.mapper;

import in.rishank.investmentplatform.pricing.dto.ExternalNavResponse;
import in.rishank.investmentplatform.pricing.dto.NavData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NavMapper {

    public static NavData map(ExternalNavResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ExternalNavResponse.ApiNavData apiData = response.getData().get(0);

        NavData navData = new NavData();
        navData.setNav(Double.parseDouble(apiData.getNav()));
        navData.setDate(LocalDate.parse(apiData.getDate(), formatter));

        return navData;
    }
}