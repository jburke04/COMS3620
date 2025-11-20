package services;

import java.util.Scanner;
import models.*;

public class PostCheckoutCleaningService {

    public static void start(Scanner scanner, HotelSystem system) {
        while (true) {
            System.out.println("\n=== POST-CHECKOUT CLEANING & MAINTENANCE ===");
            System.out.println("1) Process room after guest checkout");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();

            if ("0".equals(choice)) {
                return;
            } else if ("1".equals(choice)) {
                handleRoom(scanner, system);
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void handleRoom(Scanner scanner, HotelSystem system) {
        System.out.print("\nEnter room number to process: ");
        String input = scanner.nextLine().trim();
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return;
        }

        if (!system.roomInSystem(roomNumber)) {
            System.out.println("Room does not exist in system.");
            return;
        }

        Room room = system.getRoomByNumber(roomNumber);
        Status status = room.getStatus();

        if (status != Status.NEEDS_CLEANING && status != Status.USED) {
            System.out.println("Room is not waiting for cleaning. Current status: " + status);
            return;
        }

        System.out.println("Processing room " + roomNumber + " (status: " + status + ")");
        System.out.println("1) Cleaning completed, room passes inspection");
        System.out.println("2) Cleaning completed, MINOR issue found");
        System.out.println("3) Cleaning completed, MAJOR issue found");
        System.out.println("4) Room FAILS inspection (send back)");
        System.out.println("0) Back");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                handlePassesInspection(room);
                break;
            case "2":
                handleMinorIssue(scanner, system, roomNumber, room);
                break;
            case "3":
                handleMajorIssue(scanner, system, roomNumber, room);
                break;
            case "4":
                handleFailInspection(scanner, system, roomNumber, room);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void handlePassesInspection(Room room) {
        room.setStatus(Status.AVAILABLE);
        System.out.println("Cleaning complete. Room " + room.getRoomNumber()
                + " passed inspection and is now AVAILABLE.");
    }

    private static void handleMinorIssue(Scanner scanner, HotelSystem system,
                                         int roomNumber, Room room) {
        System.out.print("Describe the minor issue (e.g., loose handle, dead bulb): ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            desc = "Minor issue reported during post-checkout inspection.";
        }

        MaintenanceTicket t = system.createTicket(roomNumber, desc, "LOW");
        if (t != null) {
            System.out.println("Minor maintenance ticket created: #" + t.getTicketId());
        } else {
            System.out.println("Unable to create maintenance ticket.");
        }

        room.setStatus(Status.AVAILABLE);
        System.out.println("Room " + room.getRoomNumber()
                + " remains AVAILABLE while minor issue is scheduled.");
    }

    private static void handleMajorIssue(Scanner scanner, HotelSystem system,
                                         int roomNumber, Room room) {
        System.out.print("Describe the major problem (e.g., leak, AC failure): ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            desc = "Major issue reported during post-checkout inspection.";
        }

        MaintenanceTicket t = system.createTicket(roomNumber, desc, "HIGH");
        if (t != null) {
            System.out.println("Blocking maintenance ticket created: #" + t.getTicketId());
        } else {
            System.out.println("Unable to create maintenance ticket.");
        }

        room.setStatus(Status.AWAITING);
        System.out.println("Room " + room.getRoomNumber()
                + " taken OUT OF SERVICE until maintenance is completed.");
        System.out.println("After repairs and re-cleaning, run this service again and choose '1' to mark it AVAILABLE.");
    }

    private static void handleFailInspection(Scanner scanner, HotelSystem system,
                                             int roomNumber, Room room) {
        System.out.println("Inspection FAILED. Reason?");
        System.out.println("1) Cleaning issue only (send back to housekeeping)");
        System.out.println("2) Maintenance issue (create ticket and remove from service)");
        System.out.print("Choose: ");
        String choice = scanner.nextLine().trim();

        if ("1".equals(choice)) {
            room.setStatus(Status.NEEDS_CLEANING);
            System.out.println("Room " + room.getRoomNumber()
                    + " sent back to housekeeping. Status set to NEEDS_CLEANING.");
        } else if ("2".equals(choice)) {
            // treat like major issue path
            handleMajorIssue(scanner, system, roomNumber, room);
            System.out.println("Room will need re-cleaning and re-inspection after maintenance.");
        } else {
            System.out.println("Invalid choice. No changes made.");
        }
    }
}
