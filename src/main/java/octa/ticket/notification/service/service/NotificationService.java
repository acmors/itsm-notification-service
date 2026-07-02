package octa.ticket.notification.service.service;

import jakarta.mail.MessagingException;
import octa.ticket.notification.service.dto.TicketCreatedEvent;
import octa.ticket.notification.service.dto.TicketUpdatedEvent;
import octa.ticket.notification.service.dto.UserCreatedEvent;
import octa.ticket.notification.service.entities.EmailLog;
import octa.ticket.notification.service.exception.EmailSendException;
import octa.ticket.notification.service.repository.EmailLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class NotificationService {
    private final EmailService emailService;
    private final EmailLogRepository emailLogRepository;

    public NotificationService(EmailService emailService, EmailLogRepository emailLogRepository) {
        this.emailService = emailService;
        this.emailLogRepository = emailLogRepository;
    }


    public void notifyCreatedTicket(TicketCreatedEvent request) throws MessagingException {
        emailService.sendHtml(
                request.getUserEmail(),
                "Chamado criado com sucesso!",
                "ticket-created.html",
                Map.of(
                        "ticketTitle", request.getTicketTitle(),
                        "ticketNumber", request.getTicketNumber()
                        ));

        for(String analystEmail : request.getAnalystEmail()){
            emailService.sendHtml(
                    analystEmail,
                    "Novo ticket criado no sistema.",
                    "ticket-created-analyst.html",
                    Map.of(
                            "ticketTitle", request.getTicketTitle(),
                            "ticketNumber", request.getTicketNumber()
                    ));
        }
    }

    public void notifyUpdatedTicket(TicketUpdatedEvent request){
        String subject = "Chamado #" + request.getTicketNumber() + " foi atualizado!";
        String body = "Seu chamado *" + request.getTicketTitle() + ", mudou de " + request.getOldStatus() + " para " + request.getNewStatus() + ".";

        try{
            emailService.sendHtml(
                    request.getUserEmail(),
                    subject,
                    "ticket-updated.html",
                    Map.of(
                            "ticketTitle", request.getTicketTitle(),
                            "oldStatus", request.getOldStatus(),
                            "newStatus", request.getNewStatus()
                    ));

            EmailLog emailLog = new EmailLog(
                    request.getUserEmail(),
                    subject,
                    body,
                    EmailLog.EmailType.TICKET_UPDATED,
                    EmailLog.EmailStatus.SENT,
                    null,
                    LocalDateTime.now()
            );
            emailLogRepository.save(emailLog);
        } catch (Exception e) {
            EmailLog emailLog = new EmailLog(
                    request.getUserEmail(),
                    subject,
                    body,
                    EmailLog.EmailType.TICKET_UPDATED,
                    EmailLog.EmailStatus.FAILED,
                    e.getMessage(),
                    LocalDateTime.now()
            );
        }
    }

    public void notifyUserCreated(UserCreatedEvent event) throws MessagingException {
        String subject = "Confirme sua conta";

        emailService.sendHtml(
                event.getEmail(),
                subject,
                "user-created.html",
                Map.of(
                        "name", event.getName(),
                        "code", event.getVerificationCode()
                )
        );
    }
    private void sendEmail(String to, String subject, String body, EmailLog.EmailType emailType){
        try{
            emailService.send(to, subject, body);
            emailLogRepository.save(new EmailLog(to, subject, body, emailType, EmailLog.EmailStatus.SENT, null, LocalDateTime.now()));
        } catch (Exception e) {
            emailLogRepository.save(new EmailLog(to, subject, body, emailType, EmailLog.EmailStatus.FAILED, e.getMessage(), LocalDateTime.now()));
            throw new EmailSendException("Ocorreu um erro ao encaminhar o email");
        }
    }


}
