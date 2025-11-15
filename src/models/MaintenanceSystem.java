package models;

import helpers.Parser;

import java.util.*;

public class MaintenanceSystem {

    private List<MaintenanceTicket> tickets = new ArrayList<>();
    private final String ticketPath = "src/assets/MaintenanceTickets.json";

    private int nextTicketId = 1;

    /**
     * Creates a maintenance system, and more importantly loads the json directly.
     */
    public MaintenanceSystem() {
        Parser.parseTickets(ticketPath, tickets);
    }

    /**
     * Saves tickets to the MaintenanceTickets.json file.
     */
    public void saveTickets() {
        Parser.saveTickets(ticketPath, tickets);
    }

    /**
     *  Creates a Maintenance ticket for a room given a Room, Description, and Severity.
     * @param room Room that is to have maintenance
     * @param description Maintenance description
     * @param severity Severity (Priority) of the maintenance
     * @return A maintenance ticket with the given fields.
     */
    public MaintenanceTicket createTicket(Room room, String description, String severity) {
        MaintenanceTicket t = new MaintenanceTicket(nextTicketId++, room.getRoomNumber(), description, severity, MaintenanceStatus.OPEN);
        tickets.add(t);
        // if severe, mark room awaiting
        Room r = room;
        if (r != null && "HIGH".equalsIgnoreCase(severity)) {
            r.setStatus(Status.AWAITING);
        }
        saveTickets();
        return t;
    }

    /**
     * Marks a ticket as resolved. Ideally this is only called when maintenance is confirmed to be done.
     * @param ticketId A ticket ID
     * @return Whether the given ticketID could be marked resolved.
     */
    public boolean resolveTicket(int ticketId) {
        for (MaintenanceTicket t : tickets) {
            if (t.getTicketId() == ticketId) {
                t.setStatus(MaintenanceStatus.RESOLVED);
                // if room was awaiting, free or keep occupied depending on bookings
                Room r = findRoomByNumber(t.getRoomNumber());
                if (r != null && r.getStatus() == Status.AWAITING) {
                    // if someone is checked-in on this room, it should be OCCUPIED, else AVAILABLE
                    boolean occupied = bookings.stream()
                            .anyMatch(b -> b.getRoomNumber() == r.getRoomNumber() && b.getStatus() == BookingStatus.CHECKEDIN);
                    r.setStatus(occupied ? Status.OCCUPIED : Status.AVAILABLE);
                }
                saveTickets();
                return true;
            }
        }
        return false;
    }
}
