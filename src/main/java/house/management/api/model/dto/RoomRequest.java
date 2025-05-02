package house.management.api.model.dto;

public record RoomRequest(String name) {

    public RoomRequest {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
    }

    public String getName() {
        return name;
    }

}
