package src;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import src.models.*;

public class InStayMaintenance {
    private static final String ROOMS_JSON_PATH = "src/assets/Rooms.json";

    public static void start(Scanner sc, BookingSystem system) {
        System.out.println("\n=== IN-STAY MAINTENANCE ===");
        System.out.println("1) Create ticket");
        System.out.println("2) Resolve ticket");
        System.out.print("Choose: ");
        String c = sc.nextLine().trim();

        if ("1".equals(c)) {
            System.out.print("Room number: ");
            int roomNum = Integer.parseInt(sc.nextLine().trim());

            Map<Integer, Status> roomStatus = loadRoomStatuses();
            Status status = roomStatus.get(roomNum);
            if (status == null) {
                System.out.println("Room does not exist.");
                return;
            }
            if (status != Status.OCCUPIED) {
                System.out.println("Ticket can only be created for an OCCUPIED room.");
                return;
            }

            System.out.print("Severity (LOW/MEDIUM/HIGH): ");
            String sevInput = sc.nextLine().trim();
            if (sevInput.isEmpty()) {
                System.out.println("Severity is required.");
                return;
            }
            String severity = sevInput.toUpperCase();

            System.out.print("Description: ");
            String desc = sc.nextLine().trim();

            MaintenanceTicket t = system.createTicket(roomNum, desc, severity);
            if (t == null) {
                System.out.println("Could not create ticket.");
            } else {
                System.out.println("Created ticket: " + t);
            }

        } else if ("2".equals(c)) {
            System.out.print("Ticket id: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = system.resolveTicket(id);
            if (ok) {
                System.out.println("Ticket resolved.");
            } else {
                System.out.println("Ticket not found or already resolved.");
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private static Map<Integer, Status> loadRoomStatuses() {
        Map<Integer, Status> map = new HashMap<>();
        try (FileReader r = new FileReader(ROOMS_JSON_PATH)) {
            Object parsed = new JSONParser().parse(r);
            JSONArray arr = (JSONArray) parsed;
            for (Object o : arr) {
                JSONObject obj = (JSONObject) o;
                int roomNumber = ((Long) obj.get("roomNumber")).intValue();
                String statusStr = ((String) obj.get("status")).toUpperCase();
                map.put(roomNumber, Status.valueOf(statusStr));
            }
        } catch (Exception e) {
            System.out.println("Failed to read Rooms.json");
        }
        return map;
    }
}
