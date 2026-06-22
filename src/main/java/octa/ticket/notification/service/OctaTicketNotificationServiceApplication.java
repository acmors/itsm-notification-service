package octa.ticket.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class OctaTicketNotificationServiceApplication {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public static void main(String[] args) {
        SpringApplication.run(OctaTicketNotificationServiceApplication.class, args);


    }


    public void run(String... args) throws Exception {
        System.out.println("Tentando enviar email...");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("octaticketservice@gmail.com");  // manda pra si mesmo
        message.setSubject("Teste Manual");
        message.setText("Se chegou aqui, o email funciona!");

        try {
            mailSender.send(message);
            System.out.println("✅ Email enviado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Falha: " + e.getMessage());
        }
    }

}
