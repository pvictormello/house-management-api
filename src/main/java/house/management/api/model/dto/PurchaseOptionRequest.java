package house.management.api.model.dto;

import java.util.UUID;

public record PurchaseOptionRequest(UUID itemId, String url, MetadataRequest metadata) {
    
    public PurchaseOptionRequest {
        if(itemId == null) {
            throw new IllegalArgumentException("Item ID cannot be null");
        }

        if(url == null || url.isBlank()) {
            throw new IllegalArgumentException("URL cannot be null or blank");
        }

    }

    public UUID getItemId() {
        return itemId;
    }

    public String getUrl() {
        return url;
    }
    
    public MetadataRequest getMetadata() {
        return metadata;
    }

}
