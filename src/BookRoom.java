package src;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class BookRoom {
    public static void start(Scanner scanner, models.BookingSystem system) {
        System.out.println("\n=== BOOK A ROOM ===\n");

        // Step 1: Get or create guest
        models.Guest guest = getOrCreateGuest(scanner, system);
        if (guest == null) {
            System.out.println("Booking cancelled.");
            return;
        }

        // Step 2: Get dates
        System.out.println("\n--- Enter Stay Dates ---");
        Calendar startDate = getDate(scanner, "Check-in");
        Calendar endDate = getDate(scanner, "Check-out");

        if (endDate.before(startDate) || endDate.equals(startDate)) {
            System.out.println("Error: Check-out date must be after check-in date.");
            return;
        }

        // Step 3: Get number of guests
        System.out.print("Number of guests: ");
        int numGuests = Integer.parseInt(scanner.nextLine().trim());

        // Step 4: Select room type
        models.RoomType selectedType = selectRoomType(scanner);
        if (selectedType == null) {
            System.out.println("Booking cancelled.");
            return;
        }

        // Step 5: Find available rooms
        List<models.Room> availableRooms = system.findAvailableRooms(selectedType, startDate, endDate);

        if (availableRooms.isEmpty()) {
            System.out.println("\nNo rooms available for selected dates and type.");
            handleNoAvailability(scanner, system, guest, startDate, endDate);
            return;
        }

        // Step 6: Display available rooms and select
        System.out.println("\nAvailable rooms:");
        for (int i = 0; i < availableRooms.size(); i++) {
            models.Room room = availableRooms.get(i);
            System.out.println((i + 1) + ". Room " + room.roomNumber +
                    " - " + room.description.type +
                    " - $" + room.description.cost + " per night");
        }

        System.out.print("\nSelect room number (or 0 to cancel): ");
        int choice = Integer.parseInt(scanner.nextLine().trim());

        if (choice == 0 || choice > availableRooms.size()) {
            System.out.println("Booking cancelled.");
            return;
        }

        models.Room selectedRoom = availableRooms.get(choice - 1);

        // Step 7: Calculate cost and create booking
        long diffInMillis = endDate.getTimeInMillis() - startDate.getTimeInMillis();
        int numberOfNights = (int) (diffInMillis / (1000 * 60 * 60 * 24));
        int totalCost = numberOfNights * selectedRoom.description.cost;

        System.out.println("\n--- Reservation Summary ---");
        System.out.println("Guest: " + guest.name);
        System.out.println("Room: " + selectedRoom.roomNumber + " (" + selectedRoom.description.type + ")");
        System.out.println("Check-in: " + formatDate(startDate));
        System.out.println("Check-out: " + formatDate(endDate));
        System.out.println("Number of nights: " + numberOfNights);
        System.out.println("Cost per night: $" + selectedRoom.description.cost);
        System.out.println("Total cost: $" + totalCost);

        System.out.print("\nConfirm booking? (Y/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (!confirm.equals("y")) {
            System.out.println("Booking cancelled.");
            return;
        }

        // Step 8: Create booking
        models.Booking booking = system.book(selectedRoom.roomNumber, startDate, endDate, guest);

        if (booking != null) {
            System.out.println("\n✓ Booking successful!");
            System.out.println("Confirmation number: " + booking.getConfirmationNumber());
            System.out.println("Room status changed to: BOOKED");
        } else {
            System.out.println("\n✗ Booking failed. Please try again.");
        }
    }

    private static models.Guest getOrCreateGuest(Scanner scanner, models.BookingSystem system) {
        System.out.println("Does guest have existing record? (Y/N): ");
        String hasRecord = scanner.nextLine().trim().toLowerCase();

        if (hasRecord.equals("y")) {
            // Search for existing guest
            System.out.println("\nSearch by: [N]ame or [P]hone?");
            String searchType = scanner.nextLine().trim().toLowerCase();

            if (searchType.equals("n")) {
                System.out.print("Enter guest name: ");
                String name = scanner.nextLine().trim();
                List<Guest> results = system.searchGuestByName(name);

                if (results.isEmpty()) {
                    System.out.println("No guest found with that name.");
                    return createNewGuest(scanner, system);
                }

                System.out.println("\nGuests found:");
                for (int i = 0; i < results.size(); i++) {
                    models.Guest g = results.get(i);
                    System.out.println((i + 1) + ". " + g.getName() + " - " + g.getPhoneNumber());
                }

                System.out.print("Select guest (or 0 to create new): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (choice > 0 && choice <= results.size()) {
                    Guest selected = results.get(choice - 1);
                    System.out.println("✓ Guest information confirmed: " + selected.name);
                    return selected;
                }
            } else if (searchType.equals("p")) {
                System.out.print("Enter phone number: ");
                String phone = scanner.nextLine().trim();
                Guest guest = system.searchGuestByPhone(phone);

                if (guest != null) {
                    System.out.println("✓ Guest found: " + guest.name);
                    System.out.print("Confirm this guest? (Y/N): ");
                    if (scanner.nextLine().trim().toLowerCase().equals("y")) {
                        return guest;
                    }
                } else {
                    System.out.println("No guest found with that phone number.");
                }
            }
        }

        return createNewGuest(scanner, system);
    }

    private static models.Guest createNewGuest(Scanner scanner, models.BookingSystem system) {
        System.out.println("\n--- New Guest Information ---");
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("ID Document: ");
        String idDoc = scanner.nextLine().trim();

        return system.addGuest(name, phone, email, idDoc);
    }

    private static models.RoomType selectRoomType(Scanner scanner) {
        System.out.println("\n--- Select Room Type ---");
        System.out.println("1. Single ($100/night)");
        System.out.println("2. Double ($150/night)");
        System.out.println("3. King ($200/night)");
        System.out.println("4. Presidential ($300/night)");
        System.out.print("Choice (1-4, or 0 to cancel): ");

        int choice = Integer.parseInt(scanner.nextLine().trim());

        switch (choice) {
            case 1: return models.RoomType.SINGLE;
            case 2: return models.RoomType.DOUBLE;
            case 3: return models.RoomType.KING;
            case 4: return models.RoomType.PRESIDENTIAL;
            default: return null;
        }
    }

    private static Calendar getDate(Scanner scanner, String prompt) {
        System.out.print(prompt + " date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine().trim();
        String[] parts = dateStr.split("-");

        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]) - 1,
                Integer.parseInt(parts[2]),
                0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }

    private static void handleNoAvailability(Scanner scanner, models.BookingSystem system, models.Guest guest, Calendar startDate, Calendar endDate) {
        System.out.println("\n--- Alternative Options ---");
        System.out.println("1. Try different room type");
        System.out.println("2. Try different dates");
        System.out.println("3. Cancel");
        System.out.print("Choice: ");

        int choice = Integer.parseInt(scanner.nextLine().trim());

        if (choice == 1) {
            start(scanner, system);
        } else if (choice == 2) {
            start(scanner, system);
        } else {
            System.out.println("Guest declined alternatives. Booking cancelled.");
        }
    }

    private static String formatDate(Calendar cal) {
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

}
