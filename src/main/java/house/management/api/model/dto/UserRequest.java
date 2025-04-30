package house.management.api.model.dto;

public record UserRequest(String username, String email, String name, String password) {
    public UserRequest {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }
    }
    public String getUsername() {
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getPassword(){
        return password;
    }
}
