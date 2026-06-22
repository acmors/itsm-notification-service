package octa.ticket.notification.service.repository;

import octa.ticket.notification.service.entities.EmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {
    List<EmailLog> findByToEmailOrderBySentAtDesc(String email);
    List<EmailLog> findByTypeAndStatus(EmailLog.EmailType type, EmailLog.EmailStatus status);
}
