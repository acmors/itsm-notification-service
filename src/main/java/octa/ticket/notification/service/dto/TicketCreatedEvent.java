package octa.ticket.notification.service.dto;

import java.util.List;

public class TicketCreatedEvent {
    private String userEmail;
    private List<String> analystEmail;
    private Integer ticketNumber;
    private String ticketTitle;

    public TicketCreatedEvent() {
    }

    public TicketCreatedEvent(String userEmail, List<String> analystEmail, Integer ticketNumber, String ticketTitle) {
        this.userEmail = userEmail;
        this.analystEmail = analystEmail;
        this.ticketNumber = ticketNumber;
        this.ticketTitle = ticketTitle;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<String> getAnalystEmail() {
        return analystEmail;
    }

    public void setAnalystEmail(List<String> analystEmail) {
        this.analystEmail = analystEmail;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }
}
