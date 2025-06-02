package house.management.api.model.dto;

import java.util.UUID;

import house.management.api.model.enums.Priority;

public record ItemRequest(UUID roomId, String name, String description, Priority priority, String category) {

    public ItemRequest {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
        }
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Category cannot be null or blank");
        }
    }

    public UUID getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }
}
