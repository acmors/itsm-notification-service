package octa.ticket.notification.service.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import octa.ticket.notification.service.dto.TicketCreatedEvent;
import octa.ticket.notification.service.dto.TicketUpdatedEvent;
import octa.ticket.notification.service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TicketNotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public TicketNotificationConsumer(NotificationService notificationService,
                                      ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "ticket-created",
            groupId = "notification-service"
    )
    public void receiveTicketCreated(String message) throws Exception {

        TicketCreatedEvent event =
                objectMapper.readValue(message, TicketCreatedEvent.class);

        notificationService.notifyCreatedTicket(event);
    }

    @KafkaListener(
            topics = "ticket-updated",
            groupId = "notification-service"
    )
    public void receiveTicketUpdated(String message) throws Exception {

        TicketUpdatedEvent event =
                objectMapper.readValue(message, TicketUpdatedEvent.class);

        notificationService.notifyUpdatedTicket(event);
    }

}
