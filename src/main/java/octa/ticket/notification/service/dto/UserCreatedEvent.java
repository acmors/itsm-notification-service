package octa.ticket.notification.service.dto;

public class UserCreatedEvent {

    private String name;
    private String email;
    private String verificationCode;

    public UserCreatedEvent() {
    }

    public UserCreatedEvent(String name, String email, String verificationCode) {
        this.name = name;
        this.email = email;
        this.verificationCode = verificationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
