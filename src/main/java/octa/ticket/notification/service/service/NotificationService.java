package octa.ticket.notification.service.service;

import octa.ticket.notification.service.dto.TicketCreatedRequest;
import octa.ticket.notification.service.dto.TicketUpdatedRequest;
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
        String analystBody = "Novo chamado '#" + request.getTicketNumber() + "' - " + request.getTicketTitle() + ", foi criado e aguarda atendimento.";

        //Tentativa de notificação para o usuario
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

        //tentativa de notificação pra analista.
        try{
            emailService.send(request.getAnalystEmail(), subject, body);
            EmailLog emailLog = new EmailLog(
                    request.getAnalystEmail(),
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
                    request.getAnalystEmail(),
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

    public void notifyUpdatedTicket(TicketUpdatedRequest request){
        String subject = "Chamado *#" + request.getTicketNumber() + "* foi atualizado.";
        String body = "Seu chamado *" + request.getTicketTitle() + ", mudou de " + request.getOldStatus() + " para " + request.getNewStatus() + ".";

        try{
            emailService.send(request.getUserEmail(), subject, body);

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
}
