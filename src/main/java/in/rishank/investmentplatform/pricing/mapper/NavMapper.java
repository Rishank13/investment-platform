package in.rishank.investmentplatform.pricing.mapper;

import in.rishank.investmentplatform.pricing.dto.ExternalNavResponse;
import in.rishank.investmentplatform.pricing.dto.NavData;

import java.time.LocalDate;

public class NavMapper {

    public static NavData map(ExternalNavResponse response) {
        NavData data = new NavData();
        data.setNav(Double.parseDouble(response.getNav()));
        data.setDate(LocalDate.parse(response.getDate()));

        return data;
    }
}
