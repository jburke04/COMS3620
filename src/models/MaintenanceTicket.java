package models;

public class MaintenanceTicket {
    private int ticketId;
    //private int roomNumber;
    private Room room;
    private String description;
    private String severity; // e.g., LOW, MEDIUM, HIGH (free text for now)
    private MaintenanceStatus status;

    public MaintenanceTicket(int ticketId, Room room, String description, String severity, MaintenanceStatus status) {
        this.ticketId = ticketId;
        this.room = room;
        this.description = description;
        this.severity = severity;
        this.status = status;
    }

    public int getTicketId() { return ticketId; }
    public Room getRoom() { return room; }
    public String getDescription() { return description; }
    public String getSeverity() { return severity; }
    public MaintenanceStatus getStatus() { return status; }
    public void setStatus(MaintenanceStatus status) { this.status = status; }

    @Override public String toString() {
        return "#" + ticketId + " room " + room.getRoomNumber() + " [" + status + "] " + severity + " - " + description;
    }
}
