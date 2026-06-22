package octa.ticket.notification.service.controller;

import octa.ticket.notification.service.dto.TicketCreatedRequest;
import octa.ticket.notification.service.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/ticket-created")
    public ResponseEntity<Void> notifyTicketCreated(@RequestBody TicketCreatedRequest request) {
        notificationService.notifyCreatedTicket(request);
        return ResponseEntity.ok().build();
    }
}
