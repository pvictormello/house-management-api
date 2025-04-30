package house.management.api.model.dto;

public record AuthResponse(String token) {
    
        public AuthResponse {
            if (token == null || token.isBlank()) {
                throw new IllegalArgumentException("Token cannot be null or blank");
            }
        }
    
        public String getToken() {
            return token;
        }
}

