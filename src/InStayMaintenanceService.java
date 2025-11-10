package src;

import java.awt.print.Book;
import java.util.*;
import src.models.*;

public class InStayMaintenanceService {

    private int nextTicketId = 1;
    private final Map<Integer, MaintenanceTicket> tickets = new LinkedHashMap<>();
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<Integer, Guest> guests = new HashMap<>();

    public InStayMaintenanceService() {
        Guest g = new Guest("Siddhartha", "1234567890", "example@gmail.com", "");
        Room r = new Room(RoomType.SINGLE, 101);
        r.setStatus(Status.OCCUPIED);
        // making a filler booking for now
        Booking b = new Booking(g.getGuestID(), Calendar.getInstance(), Calendar.getInstance(), r, BookingStatus.CHECKEDIN, r.getCost());
        rooms.put(101, r);
        rooms.put(102, new Room(RoomType.SINGLE, 102));
        rooms.get(102).setStatus(Status.AVAILABLE);
        rooms.put(103, new Room(RoomType.SINGLE, 103));
        rooms.get(103).setStatus(Status.AVAILABLE);
        guests.put(1, g);
    }

    public Guest getGuest(int id) { return guests.get(id); }
    public Room getRoom(int number) { return rooms.get(number); }
    public Collection<MaintenanceTicket> getAllTickets() { return tickets.values(); }

    public MaintenanceTicket reportIssue(int guestId, String description, Severity severity, String visitWindow) {
        Guest g = requireGuestCheckedIn(guestId);
        Booking b = findCurrBooking(g);
        assert b != null;
        MaintenanceTicket t = new MaintenanceTicket(nextTicketId++, b.getRoom(), description, severity);
        t.setScheduledWindow(visitWindow);
        tickets.put(t.getID(), t);
        return t;
    }

    public void visitAndFix(int ticketId) {
        MaintenanceTicket t = requireTicketOpen(ticketId);
        t.setResolvedAt(Calendar.getInstance());
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
        Guest g = requireGuestCheckedIn(guestId);
        Booking b = findCurrBooking(g);
        assert b != null;
        Room current = rooms.get(b.getRoom().getRoomNumber());
        Room target = rooms.get(newRoomNumber);
        if (target == null || target.getStatus() != Status.AVAILABLE) {
            throw new IllegalStateException("Target room not available");
        }
        target.setStatus(Status.OCCUPIED);
        current.setStatus(Status.AWAITING);
        b.setRoom(target);
    }

    public void emergencyRelocate(int guestId, int newRoomNumber) {
        relocateGuestSameRate(guestId, newRoomNumber);
    }

    private Guest requireGuestCheckedIn(int guestId) {
        Guest g = guests.get(guestId);
        if (g == null) throw new IllegalArgumentException("Guest not found");
        Booking b = findCurrBooking(g);
        assert b != null;
        if (b.getRoom() == null || b.getRoom().getStatus() != Status.OCCUPIED) {
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

    private static Booking  findCurrBooking(Guest g) {
        for (Booking b : g.getBookingHistory()) {
            if (b.getStatus() == BookingStatus.CHECKEDIN)
                return b;
        }
        return null;
    }
}
