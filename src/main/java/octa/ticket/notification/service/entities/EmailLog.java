package octa.ticket.notification.service.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_email_log")
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toEmail;
    private String subject;
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailType type;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;

    private String errorMessage;
    private LocalDateTime sendAt;

    public EmailLog() {
    }

    public EmailLog(String toEmail, String subject, String body, EmailType type, EmailStatus status, String errorMessage, LocalDateTime sendAt) {
        this.toEmail = toEmail;
        this.subject = subject;
        this.body = body;
        this.type = type;
        this.status = status;
        this.errorMessage = errorMessage;
        this.sendAt = sendAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailType getType() {
        return type;
    }

    public void setType(EmailType type) {
        this.type = type;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }

    public enum EmailType{
        VERIFICATION_CODE,
        PASSWORD_RECOVERY,
        TICKET_CREATED,
        TICKET_UPDATED
    }

    public enum EmailStatus{
        SENT,
        FAILED
    }
}
