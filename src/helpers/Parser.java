package src.helpers;

import src.models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Parser {

    private static final JSONParser parser = new JSONParser();

    /**
     * 
     * @param path
     * @return
     */
    private static Object parseOrEmptyArray(String path) {
        try {
            if (!Files.exists(Paths.get(path))) return new JSONArray();
            try (FileReader reader = new FileReader(path)) {
                Object parsed = parser.parse(reader);
                return (parsed instanceof JSONArray) ? parsed : new JSONArray();
            }
        } catch (Exception e) {
            return new JSONArray(); // be permissive
        }
    }

    /**
     * Parses through a file to populate the Rooms list of the Booking System.
     * @param filepath String representation of the filepath to parse.
     * @param rooms List of Rooms to populate.
     */
    public static void parseRooms(String filepath, List<Room> rooms) {
        rooms.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject r = (JSONObject) o;
                int num = ((Long) r.get("roomNumber")).intValue();
                String status = (String) r.get("status");
                JSONObject desc = (JSONObject) r.get("description");
                String type = (String) desc.get("type");
                double cost = ((Number) desc.get("cost")).doubleValue();

                rooms.add(new Room(
                        num,
                        Status.valueOf(status),
                        RoomType.valueOf(type),
                        cost
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Rooms.json: " + e.getMessage(), e);
        }
    }

    /**
     * Parses through a file to populate the Booking System's list of guests.
     * @param filepath String representation of the filepath to parse.
     * @param guests List of Guests to populate.
     */
    public static void parseGuests(String filepath, List<Guest> guests) {
        guests.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject g = (JSONObject) o;
                int guestId = ((Long) g.get("guestId")).intValue();
                String name = (String) g.get("name");
                String phone = (String) g.get("phoneNumber");
                String email = (String) g.get("email");

                guests.add(new Guest(guestId, name, phone, email));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Guests.json: " + e.getMessage(), e);
        }
    }

    /**
     * Parses through a file to populate the Booking System's list of Bookings.
     * @param filepath String representation of the filepath to parse.
     * @param bookings List of Bookings to populate.
     */
    public static void parseBookings(String filepath, List<Booking> bookings) {
        bookings.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject b = (JSONObject) o;
                int conf = ((Long) b.get("confirmationNumber")).intValue();
                int guestId = ((Long) b.get("guestID")).intValue();
                String startStr = (String) b.get("startTime");
                String endStr   = (String) b.get("endTime");
                int roomNum = ((Long) b.get("roomNumber")).intValue();
                String status = (String) b.get("status");
                double cost = ((Number) b.get("cost")).doubleValue();

                Calendar start = gregFromISO(startStr);
                Calendar end   = gregFromISO(endStr);

                bookings.add(new Booking(conf, guestId, start, end, roomNum,
                        BookingStatus.valueOf(status), cost));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Bookings.json: " + e.getMessage(), e);
        }
    }

    /**
     * Parses through a file to populate the Booking System's list of Maintenance Tickets.
     * @param filepath String representation of the filepath to parse.
     * @param tickets List of Maintenance Tickets to populate.
     */
    public static void parseTickets(String filepath, List<MaintenanceTicket> tickets) {
        tickets.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject t = (JSONObject) o;
                int id = ((Long) t.get("ticketId")).intValue();
                int room = ((Long) t.get("roomNumber")).intValue();
                String desc = (String) t.get("description");
                String severity = (String) t.get("severity");
                String status = (String) t.get("status");
                tickets.add(new MaintenanceTicket(id, room, desc, severity, MaintenanceStatus.valueOf(status)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse MaintenanceTickets.json: " + e.getMessage(), e);
        }
    }

    // ---- Saves ----
    /**
     * Saves the Bookings to the desired file.
     * @param filepath file to modify/update.
     * @param bookings List of Bookings to update the file with.
     */
    public static void saveBookings(String filepath, List<Booking> bookings) {
        JSONArray arr = new JSONArray();
        for (Booking b : bookings) {
            JSONObject o = new JSONObject();
            o.put("confirmationNumber", b.getConfirmationNumber());
            o.put("guestID", b.getGuestID());
            o.put("startTime", isoFromCalendar(b.getStartTime()));
            o.put("endTime", isoFromCalendar(b.getEndTime()));
            o.put("roomNumber", b.getRoomNumber());
            o.put("status", b.getStatus().name());
            o.put("cost", b.getCost());
            arr.add(o);
        }
        try (FileWriter w = new FileWriter(filepath)) {
            w.write(arr.toJSONString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save Bookings.json: " + e.getMessage(), e);
        }
    }

    /**
     * Saves the Rooms to the desired file.
     * @param filepath file to modify/update.
     * @param rooms List of Rooms to update the file with.
     */
    public static void saveRooms(String filepath, List<Room> rooms) {
        JSONArray arr = new JSONArray();
        for (Room r : rooms) {
            JSONObject desc = new JSONObject();
            desc.put("type", r.getRoomType().name());
            desc.put("cost", r.getCost());

            JSONObject o = new JSONObject();
            o.put("roomNumber", r.getRoomNumber());
            o.put("status", r.getStatus().name());
            o.put("description", desc);
            arr.add(o);
        }
        try (FileWriter w = new FileWriter(filepath)) {
            w.write(arr.toJSONString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save Rooms.json: " + e.getMessage(), e);
        }
    }

    /**
     * Saves the Maintenance Tickets to the desired file.
     * @param filepath file to modify/update.
     * @param bookings List of Maintenance Tickets to update the file with.
     */
    public static void saveTickets(String filepath, List<MaintenanceTicket> tickets) {
        JSONArray arr = new JSONArray();
        for (MaintenanceTicket t : tickets) {
            JSONObject o = new JSONObject();
            o.put("ticketId", t.getTicketId());
            o.put("roomNumber", t.getRoomNumber());
            o.put("description", t.getDescription());
            o.put("severity", t.getSeverity());
            o.put("status", t.getStatus().name());
            arr.add(o);
        }
        try (FileWriter w = new FileWriter(filepath)) {
            w.write(arr.toJSONString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save MaintenanceTickets.json: " + e.getMessage(), e);
        }
    }

    // ---- helpers ----
    private static Calendar gregFromISO(String iso) {
        LocalDateTime ldt = LocalDateTime.parse(iso);
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
        return cal;
    }

    private static String isoFromCalendar(Calendar cal) {
        LocalDateTime ldt = LocalDateTime.of(
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                0);
        return ldt.toString();
    }
}
