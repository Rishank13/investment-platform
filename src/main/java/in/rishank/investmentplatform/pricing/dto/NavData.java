package in.rishank.investmentplatform.pricing.dto;

import java.time.LocalDate;

public class NavData {

    private double nav;
    private LocalDate date;

    public double getNav() {
        return nav;
    }

    public void setNav(double nav) {
        this.nav = nav;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
