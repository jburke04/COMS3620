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
    private static final InStayMaintenanceService svc = new InStayMaintenanceService();

    public static void start(Scanner sc, BookingSystem system) {
        while (true) {
            System.out.println("\n=== IN-STAY MAINTENANCE ===");
            System.out.println("1) Create ticket");
            System.out.println("2) View ticket");
            System.out.println("3) Resolve ticket");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            if ("1".equals(c)) {
                System.out.print("Room number: ");
                Integer roomNum = parseIntOrNull(sc.nextLine().trim());
                if (roomNum == null) { System.out.println("Invalid room number."); continue; }

                Map<Integer, Status> roomStatus = loadRoomStatuses();
                Status status = roomStatus.get(roomNum);
                if (status == null) { System.out.println("Room does not exist."); continue; }
                if (status != Status.OCCUPIED) { System.out.println("Ticket can only be created for an OCCUPIED room."); continue; }

                System.out.print("Problem description: ");
                String desc = sc.nextLine().trim();
                if (desc.isEmpty()) { System.out.println("Description is required."); continue; }

                System.out.print("Severity (LOW/MEDIUM/HIGH): ");
                String sevInput = sc.nextLine().trim();
                String severity = normalizeSeverity(sevInput);
                if (severity == null) { System.out.println("Invalid severity."); continue; }

                System.out.print("Visit window (e.g., 2-4 PM): ");
                String win = sc.nextLine().trim();

                MaintenanceTicket t = system.createTicket(roomNum, desc, severity);
                if (t == null) { System.out.println("Could not create ticket."); continue; }

                svc.registerTicket(t);
                if (!win.isEmpty()) svc.rescheduleVisit(t.getTicketId(), win);

                System.out.println("Created: " + t + (win.isEmpty() ? "" : " [visit=" + win + "]"));
                continue;
            }

            if ("2".equals(c)) {
                if (svc.getAllTickets().isEmpty()) { System.out.println("No tickets."); continue; }
                for (MaintenanceTicket t : svc.getAllTickets()) {
                    String meta = svc.describeMeta(t.getTicketId());
                    System.out.println(t + (meta.isEmpty() ? "" : " " + meta));
                }
                System.out.print("Enter ticket id to open, or blank to back: ");
                String idIn = sc.nextLine().trim();
                if (idIn.isEmpty()) continue;
                Integer id = parseIntOrNull(idIn);
                if (id == null) { System.out.println("Invalid id."); continue; }
                MaintenanceTicket t = svc.getTicket(id);
                if (t == null) { System.out.println("Not found."); continue; }
                while (true) {
                    String meta = svc.describeMeta(id);
                    System.out.println("\nTicket: " + t + (meta.isEmpty() ? "" : " " + meta));
                    System.out.println("1) Edit visit time");
                    System.out.println("2) Record guest feedback");
                    System.out.println("0) Back");
                    System.out.print("Choose: ");
                    String sub = sc.nextLine().trim();
                    if ("0".equals(sub)) break;
                    if ("1".equals(sub)) {
                        System.out.print("New visit window: ");
                        String nw = sc.nextLine().trim();
                        try {
                            svc.rescheduleVisit(id, nw);
                            System.out.println("Visit updated.");
                        } catch (Exception e) {
                            System.out.println("Failed: " + e.getMessage());
                        }
                    } else if ("2".equals(sub)) {
                        System.out.print("Accepted? (y/n): ");
                        boolean ok = sc.nextLine().trim().toLowerCase().startsWith("y");
                        try {
                            svc.guestConfirm(id, ok);
                            System.out.println(ok ? "Recorded accepted." : "Recorded not accepted.");
                        } catch (Exception e) {
                            System.out.println("Failed: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
                continue;
            }

            if ("3".equals(c)) {
                System.out.print("Ticket id: ");
                Integer id = parseIntOrNull(sc.nextLine().trim());
                if (id == null) { System.out.println("Invalid id."); continue; }
                try {
                    svc.visitAndFix(id);
                    system.resolveTicket(id);
                    System.out.println("Resolved. Use View to record guest feedback if needed.");
                } catch (Exception e) {
                    System.out.println("Failed: " + e.getMessage());
                }
                continue;
            }

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

    private static String normalizeSeverity(String in) {
        if (in == null) return null;
        String s = in.trim().toUpperCase();
        if (s.equals("LOW") || s.equals("MEDIUM") || s.equals("HIGH")) return s;
        if (s.equals("MINOR")) return "LOW";
        if (s.equals("BLOCKING")) return "HIGH";
        return null;
    }

    private static Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }
}
