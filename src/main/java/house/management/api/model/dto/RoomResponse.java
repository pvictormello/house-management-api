package house.management.api.model.dto;

import java.util.List;

import house.management.api.model.Item;
import house.management.api.model.Room;

public class RoomResponse {
    private String name;
    private String slug;
    private List<Item> items;

    public RoomResponse(String name, String slug, List<Item> items) {
        this.name = name;
        this.slug = slug;
        this.items = items;
    }

    public RoomResponse(Room room, List<Item> items) {
        this.name = room.getName();
        this.slug = room.getSlug();
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
