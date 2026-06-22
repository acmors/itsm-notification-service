package octa.ticket.notification.service.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String email;
    private LocalDateTime timestamp;

    public ErrorResponse(String email, LocalDateTime timestamp) {
        this.email = email;
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
