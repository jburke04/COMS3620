package src.services;

import java.util.ArrayList;
import java.util.Scanner;
import src.models.*;

/**
 * Service for Booking Cancellation.
 */
public class Cancel {

    /**
     * Starter loop that checks which Booking a user wants to cancel.
     * @param scanner Input scanner for reading user input.
     * @param system Booking System to utilize.
     */
    public static void start(Scanner scanner, BookingSystem system) {
        String choice = "";
        ArrayList<Integer> ids = new ArrayList<>();
        Booking selected;
        System.out.println("");

        for (Booking booking : system.getBookings()) {
            ids.add(booking.getConfirmationNumber());
        }

        // allow guest to select which Booking to cancel:
        do {
            System.out.println("=== CANCEL BOOKING ===");
            System.out.println("");
            System.out.println("Available Bookings:");
            printBookings(system);
            System.out.print("Enter the corresponding ID of the Booking to cancel. Enter '[Q]uit' to Go Back: ");

            choice = scanner.nextLine();
            choice = choice.trim();
            System.out.println();

            // user chose to go back to main screen:
            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Cancellation request cancelled, returning to main screen.");
                return;
            }

            // user inputted an ID, check if it's valid:
            selected = system.findBookingByConfirmation(Integer.parseInt(choice));
            if (selected != null) {
                // user confirmed the cancellation, break out of this loop, otherwise return to the list
                if (confirmationLoop(selected, scanner, system) == 0)
                    return;
            }
            // invalid ID:
            else
                System.out.println("Invalid ID, Please input an ID from the list of available Bookings.");
        } while (true);

    }

    /**
     * Print out list of Bookings.
     * @param system Booking System that provides the list of Bookings.
     */
    private static void printBookings(BookingSystem system) {
        for (Booking booking : system.getBookings())
            if (booking.getStatus() == BookingStatus.CONFIRMED)
                System.out.println("Confirmation Number: " + booking.getConfirmationNumber() + ", Room #" + booking.getRoomNumber());
    }

    /**
     * Confirmation loop for the Guest to determine if they want to cancel the
     * Booking or not.
     * @param selected Booking to cancel.
     * @param scanner Input scanner.
     * @return 0 if the cancel was confirmed and completed, 1 if the cancel was
     *          denied or the user changed their mind.
     */
    private static int confirmationLoop(Booking selected, Scanner scanner, BookingSystem system) {
        String choice;
        // confirm user wants to cancel that booking:
        do {
            System.out.println("You're cancelling Booking " + selected.getConfirmationNumber() + ". Is that correct?");
            System.out.println("[Y]es to confirm or [N]o to cancel.");

            choice = scanner.nextLine();
            choice = choice.trim();

            // user confirmed, cancel the booking:
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                system.cancelBooking(selected.getConfirmationNumber());
                System.out.println("Booking " + selected.getConfirmationNumber() + " cancelled. Returning to Main screen.");
                break;
            }
            else if (choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("no")) {
                System.out.println("Returning to Booking selection.");
                return 1;
            }
            else
                System.out.println("Invalid input.");
        } while (true);

        return 0;
    }
}