package house.management.api.model.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import house.management.api.model.Item;
import house.management.api.model.Room;

@JsonPropertyOrder({
    "totalItems",
    "boughtItems",
    "favoriteCost",
    "cheapestCost",
    "completedRooms",
    "rooms"
})
public class RoomHome {
    
    private Integer totalItems;
    private Integer boughtItems;
    private BigDecimal favoriteCost;
    private BigDecimal cheapestCost;
    private Integer completedRooms;
    private List<RoomResponse> rooms;

    public RoomHome(List<Room> rooms) {
        this.totalItems = calculateTotalItems(rooms);
        this.boughtItems = calculateBoughtItems(rooms);
        this.favoriteCost = calculateFavoriteCost(rooms);
        this.cheapestCost = calculateCheapestCost(rooms);
        this.completedRooms = calculateCompletedRooms(rooms);
        this.rooms = rooms.stream()
            .map(RoomResponse::new)
            .sorted((r1, r2) -> {
                // Check if room has no items or all items are purchased (should be last)
                boolean r1AllPurchasedOrEmpty = r1.getTotalItems() == 0 || r1.getBoughtItems().equals(r1.getTotalItems());
                boolean r2AllPurchasedOrEmpty = r2.getTotalItems() == 0 || r2.getBoughtItems().equals(r2.getTotalItems());
                
                // If both rooms have all items purchased or are empty, keep original order
                if (r1AllPurchasedOrEmpty && r2AllPurchasedOrEmpty) {
                    return 0;
                }
                
                // If only r1 has all items purchased or is empty, it comes after
                if (r1AllPurchasedOrEmpty) {
                    return 1;
                }
                
                // If only r2 has all items purchased or is empty, it comes after
                if (r2AllPurchasedOrEmpty) {
                    return -1;
                }
                
                // Otherwise, sort by the ratio of purchased items to total items (descending)
                double r1Ratio = (double) r1.getBoughtItems() / r1.getTotalItems();
                double r2Ratio = (double) r2.getBoughtItems() / r2.getTotalItems();
                
                // Reverse the comparison for descending order (higher ratio first)
                return Double.compare(r2Ratio, r1Ratio);
            })
            .toList();
    }

    private Integer calculateTotalItems(List<Room> rooms){
        return rooms.stream().flatMap(room -> room.getItems().stream()).toList().size();
    }

    private Integer calculateBoughtItems(List<Room> rooms){
        return rooms.stream().flatMap(room -> room.getItems().stream()).filter(Item::getIsPurchased).toList().size();
    }

    private BigDecimal calculateFavoriteCost(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return rooms.stream().flatMap(room -> room.getItems().stream()).toList().stream().filter(item -> !item.getIsPurchased())
            .flatMap(item -> item.getPurchaseOptions().stream().filter(po -> po.getIsFavorite()))
            .map(purchaseOption -> purchaseOption.getMetadata() != null ? 
                 purchaseOption.getMetadata().getPrice() : BigDecimal.ZERO)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateCheapestCost(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        return rooms.stream()
            .flatMap(room -> room.getItems().stream())
            .filter(item -> !item.getIsPurchased())
            .map(item -> item.getPurchaseOptions().stream()
                .map(po -> po.getMetadata() != null ? po.getMetadata().getPrice() : null)
                .filter(Objects::nonNull)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Integer calculateCompletedRooms(List<Room> rooms) {
        if (rooms == null || rooms.isEmpty()) {
            return 0;
        }
        
        return (int) rooms.stream().filter(room -> room.getItems().stream().allMatch(Item::getIsPurchased)).count();
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public Integer getBoughtItems() {
        return boughtItems;
    }

    public BigDecimal getFavoriteCost() {
        return favoriteCost;
    }

    public BigDecimal getCheapestCost() {
        return cheapestCost;
    }

    public Integer getCompletedRooms() {
        return completedRooms;
    }

    public List<RoomResponse> getRooms() {
        return rooms;
    }
}
