package house.management.api.model.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseOptionRequest(UUID itemId, String url, BigDecimal price) {
    
    public PurchaseOptionRequest {
        if(itemId == null) {
            throw new IllegalArgumentException("Item ID cannot be null");
        }

        if(url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or blank");
        }

        if(price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
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
}
