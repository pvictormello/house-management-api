package house.management.api.model.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private int status;
    private String message;
    private String details;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String message, String details, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
}
