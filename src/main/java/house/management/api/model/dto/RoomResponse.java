package house.management.api.model.dto;

import java.math.BigDecimal;
import java.util.Objects;

import house.management.api.model.Item;
import house.management.api.model.Room;

public class RoomResponse {

    private String name;
    private String slug;
    private BigDecimal favoriteCost;
    private BigDecimal cheapestCost;
    private Integer totalItems;
    private Integer boughtItems;
    private ItemsPriority itemsByPriority;

    public RoomResponse(Room room){
        this.name = room.getName();
        this.slug = room.getSlug();
        this.favoriteCost = calculateFavoriteCost(room);
        this.cheapestCost = calculateCheapestCost(room);
        this.totalItems = calculateTotalItems(room);
        this.boughtItems = calculateBoughtItems(room);
        this.itemsByPriority = new ItemsPriority(room.getItems());
    }

    private BigDecimal calculateFavoriteCost(Room room) {
        if (room == null || room.getItems() == null || room.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return room.getItems().stream()
            .filter(item -> !item.getIsPurchased())
            .flatMap(item -> item.getPurchaseOptions().stream().filter(po -> po.getIsFavorite()))
            .map(purchaseOption -> purchaseOption.getMetadata() != null ? 
                 purchaseOption.getMetadata().getPrice() : BigDecimal.ZERO)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCheapestCost(Room room) {
        if (room == null || room.getItems() == null || room.getItems().isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return room.getItems().stream()
            .filter(item -> !item.getIsPurchased())
            .map(item -> item.getPurchaseOptions().stream()
                .map(po -> po.getMetadata() != null ? po.getMetadata().getPrice() : null)
                .filter(Objects::nonNull)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateTotalItems(Room room) {
    
        if (room == null || room.getItems() == null) {
            return 0;
        }
        return room.getItems().size();
    }

    private Integer calculateBoughtItems(Room room) {
        if (room == null || room.getItems() == null) {
            return 0;
        }
        return room.getItems().stream()
            .filter(Item::getIsPurchased)
            .toList().size();
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public BigDecimal getFavoriteCost() {
        return favoriteCost;
    }

    public BigDecimal getCheapestCost() {
        return cheapestCost;
    }
    
    public Integer getTotalItems() {
        return totalItems;
    }

    public Integer getBoughtItems() {
        return boughtItems;
    }

    public ItemsPriority getItemsByPriority() {
        return itemsByPriority;
    }
}
