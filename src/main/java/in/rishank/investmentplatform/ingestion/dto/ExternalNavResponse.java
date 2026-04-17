package in.rishank.investmentplatform.ingestion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ExternalNavResponse {

    private Meta meta;
    private List<ApiNavData> data;
    private String status;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ApiNavData> getData() {
        return data;
    }

    public void setData(List<ApiNavData> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class Meta {

        @JsonProperty("scheme_name")
        private String schemeName;

        @JsonProperty("scheme_category")
        private String schemeCategory;

        @JsonProperty("scheme_type")
        private String schemeType;

        @JsonProperty("scheme_code")
        private String schemeCode;

        @JsonProperty("fund_house")
        private String fundHouse;

        public String getSchemeName() {
            return schemeName;
        }

        public void setSchemeName(String schemeName) {
            this.schemeName = schemeName;
        }

        public String getSchemeCategory() {
            return schemeCategory;
        }

        public void setSchemeCategory(String schemeCategory) {
            this.schemeCategory = schemeCategory;
        }

        public String getSchemeType() {
            return schemeType;
        }

        public void setSchemeType(String schemeType) {
            this.schemeType = schemeType;
        }

        public String getSchemeCode() {
            return schemeCode;
        }

        public void setSchemeCode(String schemeCode) {
            this.schemeCode = schemeCode;
        }

        public String getFundHouse() {
            return fundHouse;
        }

        public void setFundHouse(String fundHouse) {
            this.fundHouse = fundHouse;
        }
    }

    public static class ApiNavData {

        private String date;
        private String nav;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNav() {
            return nav;
        }

        public void setNav(String nav) {
            this.nav = nav;
        }
    }
}
