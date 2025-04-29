package home.management.api.model;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import home.management.api.dto.RoomRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "rooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "room")
    @JsonIgnore
    private List<Item> items;

    public Room() {
    }
    
    public Room(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Room(UUID id) {
        this.id = id;
    }

    public Room(RoomRequest request) {
        this.name = request.getName();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
