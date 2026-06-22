package octa.ticket.notification.service.dto;

public class TicketCreatedRequest {
    private String userEmail;
    private String analystEmail;
    private Integer ticketNumber;
    private String ticketTitle;

    public TicketCreatedRequest() {
    }

    public TicketCreatedRequest(String userEmail, String analystEmail, Integer ticketNumber, String ticketTitle) {
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

    public String getAnalystEmail() {
        return analystEmail;
    }

    public void setAnalystEmail(String analystEmail) {
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
