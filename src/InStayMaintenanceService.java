package src;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import src.models.*;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class InStayMaintenanceService {

    private int nextTicketId = 1;
    private final Map<Integer, MaintenanceTicket> tickets = new LinkedHashMap<>();
    private final Map<Integer, Room> rooms = loadRoomsFromJson("src/assets/Rooms.json");
    private final Map<Integer, Guest> guests = new HashMap<>();
    private final Map<Integer, Booking> currentBookings = new HashMap<>();

    public InStayMaintenanceService() {
        int guestId = 1;
        Guest g = new Guest(guestId, "Siddhartha", "1234567890", "example@gmail.com", "");
        guests.put(guestId, g);
        Room first = rooms.values().stream().findFirst().orElse(null);
        if (first != null) {
            first.setStatus(Status.OCCUPIED);
            Booking b = new Booking(guestId, Calendar.getInstance(), Calendar.getInstance(), first.getRoomNumber(), BookingStatus.CHECKEDIN, first.getCost());
            currentBookings.put(guestId, b);
        }
    }

    public MaintenanceTicket createTicket(int roomNumber, String description, Severity severity) {
        if (!rooms.containsKey(roomNumber)) {
            return null;
        }
        Room room = rooms.get(roomNumber);
        if (room.getStatus() != Status.OCCUPIED) {
            return null;
        }
        int ticketId = nextTicketId++;
        String createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        MaintenanceTicket t = new MaintenanceTicket(ticketId, roomNumber, description, createdAt, MaintenanceStatus.OPEN);
        tickets.put(ticketId, t);
        return t;
    }

    public boolean resolveTicket(int ticketId) {
        MaintenanceTicket t = tickets.get(ticketId);
        if (t == null) return false;
        if (t.getStatus() != MaintenanceStatus.OPEN) return false;
        t.setStatus(MaintenanceStatus.RESOLVED);
        return true;
    }

    public Collection<MaintenanceTicket> getAllTickets() {
        return tickets.values();
    }

    public Room getRoom(int number) {
        return rooms.get(number);
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
}
