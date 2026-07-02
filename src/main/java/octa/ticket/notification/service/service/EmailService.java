package octa.ticket.notification.service.service;

import jakarta.mail.MessagingException;
import octa.ticket.notification.service.exception.EmailSendException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;


@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String from;
    private final TemplateEngine engine;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String from, TemplateEngine engine) {
        this.mailSender = mailSender;
        this.from = from;
        this.engine = engine;
    }

    public void send(String to, String subject, String body){
        try {
            var message = new SimpleMailMessage();

            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);

        } catch (EmailSendException e) {
            e.printStackTrace();
            throw new EmailSendException("Erro ao enviar email: " + e.getMessage());
        }
    }

    public void sendHtml(String to, String subject, String html, Map<String, Object> props) throws MessagingException {

        try{
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message);

            var context = new Context();
            context.setVariables(props);

            var htmlContent = engine.process(html, context);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);

        }catch (EmailSendException e){
            throw new EmailSendException("Erro ao enviar email: " + e.getMessage());
        }
    }

}
