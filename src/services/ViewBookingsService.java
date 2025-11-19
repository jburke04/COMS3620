package services;

import java.util.*;
import models.*;

/**
 * Service for Viewing all Bookings in the System
 */
public class ViewBookingsService {

    /**
     * Start loop for viewing all Bookings in the Hotel System.
     * @param scanner Input scanner for user input.
     * @param system Hotel System to utilize.
     */
    public static void start(Scanner scanner, HotelSystem system) {
        String input = "";

        System.out.println("\n=== VIEW BOOKINGS ===\n");

        // print bookings:
        printBookings(system);

        // UI loop:
        do { 
            // allow user to exit:
            System.out.print("Press any key to exit: ");
            input = scanner.nextLine();

            if (input != null) {
                System.out.println("\nReturning to previous menu.");
                return;
            }
        } while (true);
    }

    /**
     * Prints all Bookings within the Hotel System.
     * @param system Hotel System to utilize.
     */
    private static void printBookings(HotelSystem system) {
        // print Booking regardless of status:
        for (Booking b : system.getBookings())
            System.out.println(b.toString());
        System.out.println("");
    }
}