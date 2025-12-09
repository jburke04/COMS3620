package helpers;

import models.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Parser Helper class for reading and updating JSON files.
 */
public class Parser {

    private static final JSONParser parser = new JSONParser();

    private static List<Room> roomsList = new ArrayList<>();

    /**
     * Checks if the file provided is empty or not, providing a parsed JSONArray
     * of data from the JSON file if it's not empty.
     * @param path Filepath to check.
     * @return JSONArray of the data within an existing file, or an empty
     *          JSONArray.
     */
    private static Object parseOrEmptyArray(String path) {
        try {
            if (!Files.exists(Paths.get(path))) return new JSONArray();
            try (FileReader reader = new FileReader(path)) {
                Object parsed = parser.parse(reader);
                return (parsed instanceof JSONArray) ? parsed : new JSONArray();
            }
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    /**
     * Parses through a file to populate the Rooms list of the Booking System.
     * @param filepath String representation of the filepath to parse.
     * @param rooms List of Rooms to populate.
     */
    @SuppressWarnings("unchecked")
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

                Room room = new Room(num, Status.valueOf(status), RoomType.valueOf(type), cost);

                rooms.add(room);
                Parser.roomsList.add(room);
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
    @SuppressWarnings("unchecked")
    public static void parseGuests(String filepath, List<Guest> guests) {
        guests.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject g = (JSONObject) o;
                int guestId = ((Long) g.get("guestID")).intValue();
                String name = (String) g.get("name");
                String phone = (String) g.get("phone");
                String email = (String) g.get("email");
                String plate = (String) g.get("plate");
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
    @SuppressWarnings("unchecked")
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
                JSONObject obj = (JSONObject) b.get("room");
                String status = (String) b.get("status");
                double cost = ((Number) b.get("cost")).doubleValue();

                Calendar start = gregFromISO(startStr);
                Calendar end   = gregFromISO(endStr);

                // make sure the actual object (not a copy) is linked to the Booking:
                Room r = null;
                for (Room room : Parser.roomsList) {
                    if (((Long) obj.get("roomNumber")).intValue() == room.getRoomNumber())
                        r = room;
                }

                bookings.add(new Booking(conf, guestId, start, end, r,
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
    @SuppressWarnings("unchecked")
    public static void parseTickets(String filepath, List<MaintenanceTicket> tickets, RoomUtils utils) {
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
                tickets.add(new MaintenanceTicket(id, utils.findRoom(room), desc, severity, MaintenanceStatus.valueOf(status)));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse MaintenanceTickets.json: " + e.getMessage(), e);
        }
    }

    /**
     * Parses a file to populate the Employees list of the EmployeeSystem.
     * Supports both "id" and legacy "employeeId" keys.
     * @param filepath Path to Employees.json
     * @param employees List of Employees
     */
    @SuppressWarnings("unchecked")
    public static void parseEmployees(String filepath, List<Employee> employees) {
        employees.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject e = (JSONObject) o;

                Long idVal = (Long) e.get("id");
                if (idVal == null && e.get("employeeId") != null) {
                    idVal = (Long) e.get("employeeId");
                }
                int id = (idVal == null) ? 0 : idVal.intValue();

                String roleStr = (String) e.get("role");
                EmployeeType type = EmployeeType.valueOf(roleStr);

                String name  = (String) e.getOrDefault("name", "");
                String phone = (String) e.getOrDefault("phone", "");
                String email = (String) e.getOrDefault("email", "");

                Employee emp = EmployeeFactory.makeEmployee(type, id, name, phone, email);
                if (emp != null) {
                    employees.add(emp);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse Employees.json: " + ex.getMessage(), ex);
        }
    }

    /**
     * Parses Lost & Found items from JSON.
     *
     * @param filepath path to LostAndFoundItems.json
     * @param items    list of LostAndFoundItem to populate
     */
    @SuppressWarnings("unchecked")
    public static void parseLostAndFoundItems(String filepath, List<LostAndFoundItem> items) {
        items.clear();
        try {
            JSONArray arr = (JSONArray) parseOrEmptyArray(filepath);
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                int id = ((Long) obj.get("id")).intValue();
                String desc = (String) obj.getOrDefault("description", "");
                String location = (String) obj.getOrDefault("locationFound", "");
                Long guestIdLong = (Long) obj.get("guestId");
                Integer guestId = (guestIdLong == null) ? null : guestIdLong.intValue();
                String statusStr = (String) obj.getOrDefault("status", "UNCLAIMED");
                String dateFound = (String) obj.getOrDefault("dateFound", "");

                LostItemStatus status = LostItemStatus.valueOf(statusStr);
                items.add(new LostAndFoundItem(id, desc, location, guestId, status, dateFound));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse LostAndFoundItems.json: " + e.getMessage(), e);
        }
    }


    // ---- Saves ----
    /**
     * Saves the Bookings to the desired file.
     * @param filepath file to modify/update.
     * @param bookings List of Bookings to update the file with.
     */
    @SuppressWarnings("unchecked")
    public static void saveBookings(String filepath, List<Booking> bookings) {
        JSONArray arr = new JSONArray();
        for (Booking b : bookings) {
            JSONObject o = new JSONObject();
            o.put("confirmationNumber", b.getConfirmationNumber());
            o.put("guestID", b.getGuestID());
            o.put("startTime", isoFromCalendar(b.getStartTime()));
            o.put("endTime", isoFromCalendar(b.getEndTime()));
            
            JSONObject r = new JSONObject();
            JSONObject desc = new JSONObject();
            desc.put("type", b.getRoom().getRoomType().name());
            desc.put("cost", b.getRoom().getCost());

            r.put("roomNumber", b.getRoom().getRoomNumber());
            r.put("status", b.getRoom().getStatus().name());
            r.put("description", desc);

            o.put("room", r);
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
    @SuppressWarnings("unchecked")
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
     * @param tickets List of Maintenance Tickets to update the file with.
     */
    @SuppressWarnings("unchecked")
    public static void saveTickets(String filepath, List<MaintenanceTicket> tickets) {
        JSONArray arr = new JSONArray();
        for (MaintenanceTicket t : tickets) {
            JSONObject o = new JSONObject();
            o.put("ticketId", t.getTicketId());
            o.put("roomNumber", t.getRoom().getRoomNumber());
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

    /**
     * Saves the Guests to the Guests.json file
     * @param filepath path to Guests.json
     * @param guests list of Guests
     */
    @SuppressWarnings("unchecked")
    public static void saveGuests(String filepath, List<Guest> guests) {
        JSONArray arr = new JSONArray();
        for (Guest g : guests) {
            JSONObject o = new JSONObject();
            o.put("guestID", g.getGuestId());
            o.put("name", g.getName());
            o.put("phone", g.getPhoneNumber());
            o.put("email", g.getEmail());
            o.put("plate", g.getLicensePlate());
            arr.add(o);
        }
        try (FileWriter w = new FileWriter(filepath)){
            w.write(arr.toJSONString());
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to save Guests.json " + e.getMessage(), e);
        }
    }

    /**
     * Saves the Employees to Employees.json file
     * @param filepath path to Employees.json
     * @param employees List of Employees
     */
    @SuppressWarnings("unchecked")
    public static void saveEmployees(String filepath, List<Employee> employees) {
        JSONArray arr = new JSONArray();
        for (Employee e : employees) {
            JSONObject o = new JSONObject();
            o.put("id", e.getId());
            o.put("role", e.type().name());
            o.put("name", e.getName());
            o.put("phone", e.getPhone());
            o.put("email", e.getEmail());
            arr.add(o);
        }

        try (FileWriter w = new FileWriter(filepath)) {
            w.write(arr.toJSONString());
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save Employees.json: " + ex.getMessage(), ex);
        }
    }

    /**
     * Saves Lost & Found items to LostAndFoundItems.json
     *
     * @param filepath path to LostAndFoundItems.json
     * @param items    list of LostAndFoundItem
     */
    @SuppressWarnings("unchecked")
    public static void saveLostAndFoundItems(String filepath, List<LostAndFoundItem> items) {
        JSONArray arr = new JSONArray();
        for (LostAndFoundItem i : items) {
            JSONObject o = new JSONObject();
            o.put("id", i.getId());
            o.put("description", i.getDescription());
            o.put("locationFound", i.getLocationFound());
            if (i.getGuestId() != null) {
                o.put("guestId", i.getGuestId());
            }
            o.put("status", i.getStatus().name());
            o.put("dateFound", i.getDateFound());
            arr.add(o);
        }

        try (FileWriter w = new FileWriter(filepath)) {
            w.write(arr.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save LostAndFoundItems.json: " + e.getMessage(), e);
        }
    }

    // ---- helpers ----
    private static Calendar gregFromISO(String iso) {
        LocalDateTime ldt = LocalDateTime.parse(iso);
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
        return cal;
    }

    public static String isoFromCalendar(Calendar cal) {
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
