package home.management.api.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import home.management.api.model.enums.Priority;
import jakarta.validation.constraints.NotBlank;

public class ItemsRequest {

    @JsonProperty("room_id")
    private UUID roomId;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    private String description;
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
