package src;

import java.util.Calendar;

public class MaintenanceTicket {
    int id;
    int roomNumber;
    String description;
    Severity severity;
    MaintenanceStatus status = MaintenanceStatus.OPEN;
    Calendar createdAt = Calendar.getInstance();
    Calendar resolvedAt;

    public MaintenanceTicket(int id, int roomNumber, String description, Severity severity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.description = description;
        this.severity = severity;
    }
}
