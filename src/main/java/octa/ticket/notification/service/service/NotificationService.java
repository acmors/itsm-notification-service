package octa.ticket.notification.service.service;

import octa.ticket.notification.service.dto.TicketCreatedRequest;
import octa.ticket.notification.service.entities.EmailLog;
import octa.ticket.notification.service.exception.EmailSendException;
import octa.ticket.notification.service.repository.EmailLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {
    private final EmailService emailService;
    private final EmailLogRepository emailLogRepository;

    public NotificationService(EmailService emailService, EmailLogRepository emailLogRepository) {
        this.emailService = emailService;
        this.emailLogRepository = emailLogRepository;
    }


    public void notifyCreatedTicket(TicketCreatedRequest request){
        String subject = "Chamado *#" + request.getTicketNumber() + "* criado.";
        String body = "Seu chamado *" + request.getTicketTitle() + "* foi criado com sucesso!\nIremos te notificar sobre cada ação realizada em seu chamado.";

        try{
            emailService.send(request.getUserEmail(), subject, body);

            EmailLog emailLog = new EmailLog(
                    request.getUserEmail(),
                    subject,
                    body,
                    EmailLog.EmailType.TICKET_CREATED,
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
                    EmailLog.EmailType.TICKET_CREATED,
                    EmailLog.EmailStatus.FAILED,
                    e.getMessage(),
                    LocalDateTime.now()
            );

            emailLogRepository.save(emailLog);
            throw new EmailSendException("Ocorreu um erro ao encaminhar email de Ticket criado" + e);
        }
    }
}
