package services;

import java.util.List;
import java.util.Scanner;

import models.*;

public class LostAndFoundService {

    public static void start(Scanner sc, HotelSystem system) {
        while (true) {
            System.out.println("\n=== LOST & FOUND ===");
            System.out.println("1) Log found item");
            System.out.println("2) Guest reports missing item");
            System.out.println("3) View all Lost & Found tickets");
            System.out.println("0) Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    logFoundItem(sc, system);
                    break;
                case "2":
                    guestReportsMissing(sc, system);
                    break;
                case "3":
                    viewAllItems(system);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void logFoundItem(Scanner sc, HotelSystem system) {
        System.out.print("Enter short description of the item: ");
        String desc = sc.nextLine().trim();

        System.out.print("Where was it found (room or area)? ");
        String location = sc.nextLine().trim();

        System.out.print("Do you know which guest it might belong to? (y/n): ");
        String yn = sc.nextLine().trim().toLowerCase();

        Guest owner = null;
        if (yn.startsWith("y")) {
            System.out.print("Enter guest name or phone: ");
            String key = sc.nextLine().trim();
            owner = system.findGuestByPhoneOrName(key);
            if (owner == null) {
                System.out.println("No matching guest found. Item will be logged as UNCLAIMED.");
            }
        }

        LostAndFoundItem item = system.logLostFoundItem(desc, location, owner);
        if (item != null) {
            System.out.println("Lost & Found ticket created: #" + item.getId());
            System.out.println(item);
        } else {
            System.out.println("Unable to create Lost & Found ticket.");
        }
    }

    private static void guestReportsMissing(Scanner sc, HotelSystem system) {
        System.out.print("Briefly describe the missing item: ");
        String query = sc.nextLine().trim();

        List<LostAndFoundItem> matches = system.searchLostFoundItems(query);
        if (matches.isEmpty()) {
            System.out.println("No matching items found in Lost & Found.");
            System.out.println("We will contact the guest if the item is found later.");
            return;
        }

        System.out.println("Possible matches:");
        for (LostAndFoundItem item : matches) {
            System.out.println(item);
        }

        System.out.print("If one belongs to the guest, enter its ID to mark as claimed (or press Enter to skip): ");
        String idStr = sc.nextLine().trim();
        if (idStr.isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr);
            LostAndFoundItem item = system.findLostFoundItemById(id);
            if (item == null) {
                System.out.println("No ticket with that ID.");
                return;
            }
            system.markLostItemClaimed(id);
            System.out.println("Item #" + id + " marked as CLAIMED.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void viewAllItems(HotelSystem system) {
        List<LostAndFoundItem> all = system.getAllLostFoundItems();
        if (all.isEmpty()) {
            System.out.println("No Lost & Found tickets recorded.");
            return;
        }
        System.out.println("\nCurrent Lost & Found items:");
        for (LostAndFoundItem item : all) {
            System.out.println(item);
        }
    }
}
