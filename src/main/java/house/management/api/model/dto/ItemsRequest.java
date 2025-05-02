package house.management.api.model.dto;

import java.util.UUID;

import house.management.api.model.enums.Priority;

public record ItemsRequest(UUID roomId, String name, String description, Priority priority) {

    public ItemsRequest {
        if (roomId == null) {
            throw new IllegalArgumentException("Room ID cannot be null");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null");
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
}
