package in.rishank.investmentplatform.pricing.entity;

import in.rishank.investmentplatform.asset.entity.Asset;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "price_history",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_asset_date",
                        columnNames = {"asset_id", "date"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_asset_date",
                        columnList = "asset_id, date"
                )
        }
)
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Column(nullable = false)
    private Double price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
