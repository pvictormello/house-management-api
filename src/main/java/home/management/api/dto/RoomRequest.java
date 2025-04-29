package home.management.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequest {

    @NotBlank(message = "Name is obrigatory")
    @NotNull(message = "Name is obrigatory")
    private String name;

    public RoomRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
