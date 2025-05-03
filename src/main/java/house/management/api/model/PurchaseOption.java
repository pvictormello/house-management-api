package house.management.api.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import house.management.api.model.dto.PurchaseOptionRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "purchase_options")
public class PurchaseOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "url", columnDefinition = "TEXT", nullable = false)
    private String url;

    @Column(name = "price", columnDefinition = "NUMERIC(10,2)", nullable = false)
    private BigDecimal price;
    
    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @JsonIgnore
    private Item item;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public PurchaseOption() {
    }

    public PurchaseOption(UUID id, Item item, String url, BigDecimal price, Boolean isFavorite, String imageUrl) {
        this.id = id;
        this.item = item;
        this.url = url;
        this.price = price;
        this.isFavorite = isFavorite;
        this.imageUrl = imageUrl;
    }

    public PurchaseOption(PurchaseOptionRequest request, Item item) {
        this.item = item;
        this.url = request.getUrl();
        this.price = request.getPrice();
        this.isFavorite = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
