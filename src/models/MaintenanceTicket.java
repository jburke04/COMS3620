package models;

public class MaintenanceTicket {
    private int ticketId;
    private int roomNumber;
    private String description;
    private String severity; // e.g., LOW, MEDIUM, HIGH (free text for now)
    private MaintenanceStatus status;

    public MaintenanceTicket(int ticketId, int roomNumber, String description, String severity, MaintenanceStatus status) {
        this.ticketId = ticketId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    public int getTicketId() { return ticketId; }
    public int getRoomNumber() { return roomNumber; }
    public String getDescription() { return description; }
    public String getSeverity() { return severity; }
    public MaintenanceStatus getStatus() { return status; }
    public void setStatus(MaintenanceStatus status) { this.status = status; }

    @Override public String toString() {
        return "#" + ticketId + " room " + roomNumber + " [" + status + "] " + severity + " - " + description;
    }
}
