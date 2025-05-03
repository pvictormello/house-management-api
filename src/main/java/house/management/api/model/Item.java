package house.management.api.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import house.management.api.model.dto.ItemRequest;
import house.management.api.model.enums.Priority;
import jakarta.persistence.CascadeType;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Room room;

    @NotNull
    private String name;
    
    @NotNull
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Priority priority;

    @Column(name = "is_purchased")
    private Boolean isPurchased;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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

    public Item(ItemRequest request) {
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
