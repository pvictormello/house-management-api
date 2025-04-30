package house.management.api.dto;

import java.util.UUID;

import house.management.api.model.enums.Priority;
import jakarta.validation.constraints.NotBlank;

public class ItemsRequest {

    @NotBlank(message = "Room ID cannot be empty")
    private UUID roomId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
    @NotBlank(message = "Priority cannot be empty")
    private Priority priority;

    public ItemsRequest() {
    }

    public ItemsRequest(UUID roomId, String name, String description, Priority priority) {
        this.roomId = roomId;
        this.name = name;
        this.description = description;
        this.priority = priority;
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
