package octa.ticket.notification.service.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import octa.ticket.notification.service.dto.UserCreatedEvent;
import octa.ticket.notification.service.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserNotificationConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public UserNotificationConsumer(NotificationService notificationService,
                                    ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "user-created",
            groupId = "notification-service"
    )
    public void receiveUserCreated(String message) throws Exception {

        UserCreatedEvent event =
                objectMapper.readValue(message, UserCreatedEvent.class);

        notificationService.notifyUserCreated(event);

    }
}
