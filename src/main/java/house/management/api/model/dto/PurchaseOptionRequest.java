package house.management.api.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseOptionRequest {

    private UUID itemId;
    private String url;
    private BigDecimal price;
    
    public PurchaseOptionRequest() {
    }

    public PurchaseOptionRequest(UUID itemId, String url, BigDecimal price) {
        
        this.itemId = itemId;
        this.url = url;
        this.price = price;
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

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
