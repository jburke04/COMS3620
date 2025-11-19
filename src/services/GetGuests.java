package services;

import helpers.Parser;
import java.util.List;
import java.util.Scanner;
import models.*;

public class GetGuests {

    public static void start(Scanner scanner, HotelSystem system) {

        System.out.println("=== Guest Lookup ===");

        String fullName = "";

        while (true) {
            System.out.println("Enter guest's FULL NAME:");
            System.out.println("Or press [Q] to quit.");
            System.out.print("Choice: ");
            fullName = scanner.nextLine().trim();

            if (fullName.equalsIgnoreCase("q")) {
                System.out.println("Lookup cancelled.");
                return;
            }

            if (fullName.isEmpty()) {
                System.out.println("Name cannot be empty. Try again.");
                continue;
            }

            // System checks if guest exists
            Guest guest = system.findGuestByPhoneOrName(fullName);

            if (guest == null) {
                System.out.println("No guest found with name: " + fullName);
                continue;
            }

            // Found guest — print details
            printGuestInfo(system, guest);
            return;
        }
    }

    private static void printGuestInfo(HotelSystem system, Guest guest) {
        System.out.println("\n=== Guest Information ===");
        System.out.println("Name: " + guest.getName());
        System.out.println("ID: " + guest.getGuestId());
        System.out.println("Phone: " + guest.getPhoneNumber());
        System.out.println("Email: " + guest.getEmail());

        System.out.println("\n=== Booking History ===");

        List<Booking> bookings = system.getBookingByGuest(guest);

        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this guest.");
            return;
        }

        for (Booking b : bookings) {
            System.out.println("  Confirmation #: " + b.getConfirmationNumber());
            System.out.println("  Room: " + b.getRoom().getRoomNumber());
            System.out.println("  Cost: $" + b.getCost());
            System.out.println("  Start: " +Parser.isoFromCalendar(b.getStartTime()));
            System.out.println("  End: " + Parser.isoFromCalendar(b.getEndTime()));
            System.out.println("  Status: " + b.getStatus());

        }
    }
}
