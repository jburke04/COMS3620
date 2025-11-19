package services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import models.*;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Service for In-Stay Maintenance.
 */
public class InStayMaintenanceService {

    private final Map<Integer, MaintenanceTicket> tickets = new LinkedHashMap<>();
    private final Map<Integer, Room> rooms = loadRoomsFromJson("src/assets/Rooms.json");
    private final Map<Integer, Booking> currentBookings = new HashMap<>();
    private final Map<Integer, String> scheduledWindows = new HashMap<>();
    private final Map<Integer, Boolean> guestConfirmations = new HashMap<>();

    public InStayMaintenanceService() {
        int guestId = 1;
        Room first = rooms.values().stream().findFirst().orElse(null);
        if (first != null) {
            first.setStatus(Status.OCCUPIED);
            Booking b = new Booking(guestId, Calendar.getInstance(), Calendar.getInstance(), first, BookingStatus.CHECKEDIN, first.getCost());
            currentBookings.put(guestId, b);
        }
    }

    public void registerTicket(MaintenanceTicket t) {
        tickets.put(t.getTicketId(), t);
    }

    public MaintenanceTicket getTicket(int id) {
        return tickets.get(id);
    }

    public Collection<MaintenanceTicket> getAllTickets() {
        return tickets.values();
    }

    public void rescheduleVisit(int ticketId, String newWindow) {
        if (!tickets.containsKey(ticketId)) throw new IllegalArgumentException("Unknown ticket");
        scheduledWindows.put(ticketId, newWindow);
    }

    public void visitAndFix(int ticketId) {
        MaintenanceTicket t = tickets.get(ticketId);
        if (t == null) throw new IllegalArgumentException("Unknown ticket");
        if (t.getStatus() != MaintenanceStatus.OPEN) throw new IllegalStateException("Ticket not OPEN");
        t.setStatus(MaintenanceStatus.RESOLVED);
    }

    public void guestConfirm(int ticketId, boolean accepted) {
        if (!tickets.containsKey(ticketId)) throw new IllegalArgumentException("Unknown ticket");
        guestConfirmations.put(ticketId, accepted);
    }

    public String describeMeta(int ticketId) {
        StringBuilder sb = new StringBuilder();
        String win = scheduledWindows.get(ticketId);
        Boolean ok = guestConfirmations.get(ticketId);
        if (win != null && !win.isEmpty()) sb.append("[visit=").append(win).append("]");
        if (ok != null) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(ok ? "[guest=accepted]" : "[guest=not-accepted]");
        }
        return sb.toString();
    }

    private static Map<Integer, Room> loadRoomsFromJson(String path) {
        Map<Integer, Room> map = new HashMap<>();
        try (FileReader r = new FileReader(path)) {
            Object parsed = new JSONParser().parse(r);
            JSONArray arr = (JSONArray) parsed;
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                int roomNumber = ((Long) obj.get("roomNumber")).intValue();
                JSONObject desc = (JSONObject) obj.get("description");
                double cost = ((Number) desc.get("cost")).doubleValue();
                RoomType type = RoomType.valueOf(((String) desc.get("type")).toUpperCase());
                Status status = Status.valueOf(((String) obj.get("status")).toUpperCase());
                map.put(roomNumber, new Room(roomNumber, status, type, cost));
            }
        } catch (Exception ignored) {}
        return map;
    }

    @SuppressWarnings("unused")
    private static String nowIso() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
    }
}
