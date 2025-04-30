package house.management.api.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseOptionRequest {

    @JsonProperty("item_id")
    private UUID itemId;
    private String url;
    private BigDecimal price;
    private String label;
    
    public PurchaseOptionRequest() {
    }

    public PurchaseOptionRequest(UUID itemId, String url, BigDecimal price, String label) {
        
        this.itemId = itemId;
        this.url = url;
        this.price = price;
        this.label = label;
    }

    public UUID getItemId() {
        return itemId;
    }

    public String getUrl() {
        return url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getLabel() {
        return label;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
