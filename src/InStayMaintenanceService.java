package src;

import java.util.*;
import models.*;

public class InStayMaintenanceService {

    public static class RoomRef {
        public int number;
        public Status status;
        public RoomRef(int number, Status status) {
            this.number = number;
            this.status = status;
        }
        @Override public String toString() { return "Room " + number + " (" + status + ")"; }
    }

    public static class GuestRef {
        public int guestId;
        public String name;
        public int roomNumber;
        public GuestRef(int guestId, String name, int roomNumber) {
            this.guestId = guestId;
            this.name = name;
            this.roomNumber = roomNumber;
        }
        @Override public String toString() { return "Guest#" + guestId + " " + name + " in room " + roomNumber; }
    }

    private int nextTicketId = 1;
    private final Map<Integer, MaintenanceTicket> tickets = new LinkedHashMap<>();
    private final Map<Integer, RoomRef> rooms = new HashMap<>();
    private final Map<Integer, GuestRef> guests = new HashMap<>();

    public InStayMaintenanceService() {
        rooms.put(101, new RoomRef(101, Status.OCCUPIED));
        rooms.put(102, new RoomRef(102, Status.AVAILABLE));
        rooms.put(103, new RoomRef(103, Status.AVAILABLE));
        guests.put(1, new GuestRef(1, "Siddhartha", 101));
    }

    public GuestRef getGuest(int id) { return guests.get(id); }
    public RoomRef getRoom(int number) { return rooms.get(number); }
    public Collection<MaintenanceTicket> getAllTickets() { return tickets.values(); }

    public MaintenanceTicket reportIssue(int guestId, String description, Severity severity, String visitWindow) {
        GuestRef g = requireGuestCheckedIn(guestId);
        MaintenanceTicket t = new MaintenanceTicket(nextTicketId++, g.roomNumber, description, severity);
        t.setScheduledWindow(visitWindow);
        tickets.put(t.getId(), t);
        return t;
    }

    public void visitAndFix(int ticketId) {
        MaintenanceTicket t = requireTicketOpen(ticketId);
        t.resolve();
    }

    public void guestConfirm(int ticketId, boolean accepted) {
        MaintenanceTicket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("Unknown ticket " + ticketId);
        t.setGuestConfirmedFix(accepted);
    }

    public void rescheduleVisit(int ticketId, String newWindow) {
        MaintenanceTicket t = requireTicketOpen(ticketId);
        t.setScheduledWindow(newWindow);
    }

    public void markNeedsParts(int ticketId, String followUpWindow) {
        MaintenanceTicket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("Unknown ticket " + ticketId);
        t.setScheduledWindow(followUpWindow);
    }
    
    public void relocateGuestSameRate(int guestId, int newRoomNumber) {
        GuestRef g = requireGuestCheckedIn(guestId);
        RoomRef current = rooms.get(g.roomNumber);
        RoomRef target = rooms.get(newRoomNumber);
        if (target == null || target.status != Status.AVAILABLE) {
            throw new IllegalStateException("Target room not available");
        }
        target.status = Status.OCCUPIED;
        current.status = Status.AWAITING;
        g.roomNumber = newRoomNumber;
    }

    public void emergencyRelocate(int guestId, int newRoomNumber) {
        relocateGuestSameRate(guestId, newRoomNumber);
    }

    private GuestRef requireGuestCheckedIn(int guestId) {
        GuestRef g = guests.get(guestId);
        if (g == null) throw new IllegalArgumentException("Guest not found");
        RoomRef r = rooms.get(g.roomNumber);
        if (r == null || r.status != Status.OCCUPIED) {
            throw new IllegalStateException("Guest is not currently checked-in / occupying a room");
        }
        return g;
    }

    private MaintenanceTicket requireTicketOpen(int ticketId) {
        MaintenanceTicket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("Unknown ticket " + ticketId);
        if (t.getStatus() != MaintenanceStatus.OPEN) throw new IllegalStateException("Ticket not OPEN");
        return t;
    }
}
