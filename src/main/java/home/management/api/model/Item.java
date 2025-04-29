package home.management.api.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import home.management.api.dto.ItemsRequest;
import home.management.api.model.enums.Priority;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @NotNull
    private Room room;

    @NotNull
    private String name;
    
    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority;

    @Column(name = "is_purchased")
    @JsonProperty("is_purchased")
    private Boolean isPurchased;

    @JsonProperty("purchase_options")
    @OneToMany(mappedBy = "item" ,fetch = FetchType.LAZY)
    private List<PurchaseOption> purchaseOptions;

    public Item() {
    }

    public Item(UUID id, Room room, String name, String description, Priority priority, Boolean isPurchased) {
        this.id = id;
        this.room = room;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.isPurchased = isPurchased;
    }

    public Item(ItemsRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.priority = request.getPriority();
        this.isPurchased = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean isPurchased) {
        this.isPurchased = isPurchased;
    }

    public List<PurchaseOption> getPurchaseOptions() {
        return purchaseOptions;
    }

    public void setPurchaseOptions(List<PurchaseOption> purchaseOptions) {
        this.purchaseOptions = purchaseOptions;
    }
}
