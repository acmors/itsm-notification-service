package octa.ticket.notification.service.service;

import octa.ticket.notification.service.dto.TicketCreatedEvent;
import octa.ticket.notification.service.dto.TicketUpdatedEvent;
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


    public void notifyCreatedTicket(TicketCreatedEvent request){
        String subject = "Chamado *#" + request.getTicketNumber() + "* criado.";
        String body = "Seu chamado *" + request.getTicketTitle() + "* foi criado com sucesso!\nIremos te notificar sobre cada ação realizada em seu chamado.";
        String analystBody = "Novo chamado '#" + request.getTicketNumber() + "' - " + request.getTicketTitle() + ", foi criado e aguarda atendimento.";

        sendEmail(request.getUserEmail(), subject, body, EmailLog.EmailType.TICKET_CREATED);

        for(String analystEmail : request.getAnalystEmail()){
            sendEmail(analystEmail, subject, analystBody, EmailLog.EmailType.TICKET_CREATED);
        }
    }

    public void notifyUpdatedTicket(TicketUpdatedEvent request){
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
