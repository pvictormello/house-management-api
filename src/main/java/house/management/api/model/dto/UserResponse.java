package house.management.api.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import house.management.api.model.User;

public class UserResponse {
    
    private UUID id;
    private String username;
    private String email;
    private String profileImageUrl;
    private LocalDateTime createdAt;
    
    public UserResponse(){
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.createdAt = user.getCreatedAt();
    }

    public UUID getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getProfileImageUrl() {
        return this.profileImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

}
