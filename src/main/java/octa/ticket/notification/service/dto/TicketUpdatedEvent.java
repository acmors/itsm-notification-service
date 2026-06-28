package octa.ticket.notification.service.dto;

public class TicketUpdatedEvent {
    private String userEmail;
    private String ticketTitle;
    private int ticketNumber;
    private String oldStatus;
    private String newStatus;

    public TicketUpdatedEvent() {
    }

    public TicketUpdatedEvent(String userEmail, String ticketTitle, int ticketNumber, String oldStatus, String newStatus) {
        this.userEmail = userEmail;
        this.ticketTitle = ticketTitle;
        this.ticketNumber = ticketNumber;
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public void setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }
}
