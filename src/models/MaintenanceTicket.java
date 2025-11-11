package src.models;

import java.util.Calendar;

//public class MaintenanceTicket {
//    private int id;
//    private Room room;
//    private String description;
//    private Severity severity;
//    private MaintenanceStatus status = MaintenanceStatus.OPEN;
//    private Calendar createdAt = Calendar.getInstance();
//    private Calendar resolvedAt;
//    private boolean confirmed = false;
//    private String scheduledWindow = "";
//
//    public MaintenanceTicket(int id, Room room, String description, Severity severity) {
//        this.id = id;
//        this.room = room;
//        this.description = description;
//        this.severity = severity;
//    }
//
//    public int getID() {
//        return this.id;
//    }
//
//    public Room getRoom() {
//        return this.room;
//    }
//
//    public String getDescription() {
//        return this.description;
//    }
//
//    public Severity getSeverity() {
//        return this.severity;
//    }
//
//    public MaintenanceStatus getStatus() {
//        return this.status;
//    }
//
//    public Calendar getCreatedAt() {
//        return this.createdAt;
//    }
//
//    public Calendar getResolvedAt() {
//        return this.resolvedAt;
//    }
//
//    public void setStatus(MaintenanceStatus status) {
//        this.status = status;
//    }
//
//    public void setSeverity(Severity severity) {
//        this.severity = severity;
//    }
//
//    public void setResolvedAt(Calendar resolvedAt) {
//        this.resolvedAt = resolvedAt;
//    }
//
//    public void setScheduledWindow(String window) {
//        this.scheduledWindow = window;
//    }
//
//    public void setGuestConfirmedFix(boolean bool) {
//        this.confirmed = bool;
//    }
//}

/**
 * For iteration 1 demo
 */

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
