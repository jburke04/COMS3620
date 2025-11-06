package models;

import java.util.Calendar;

public class MaintenanceTicket {
    private int id;
    private int roomNumber;
    private String description;
    private Severity severity;
    private MaintenanceStatus status = MaintenanceStatus.OPEN;
    private Calendar createdAt = Calendar.getInstance();
    private Calendar resolvedAt;

    public MaintenanceTicket(int id, int roomNumber, String description, Severity severity) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.description = description;
        this.severity = severity;
    }
}
