package src;

import java.util.ArrayList;
import java.util.Scanner;
import src.models.*;

/**
 * User Interface that handles the cancellation process.
 */
public class Cancel {

    public static void start(Scanner scanner, ArrayList<Booking> bookings, BookingSystem system) {
        String choice = "";
        ArrayList<Integer> ids = new ArrayList<>();
        Booking selected;

        for (Booking booking : bookings) {
            ids.add(booking.getConfirmationNumber());
        }

        // allow guest to select which Booking to cancel:
        do {
            System.out.println("Available Bookings:");
            printBookings(bookings);
            System.out.println("Enter the corresponding ID of the Booking to cancel.");
            System.out.println("Enter '[Q]uit' to Go Back");
            
            choice = scanner.nextLine();
            choice = choice.trim();

            // user chose to go back to main screen:
            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("Cancellation request cancelled, returning to main screen.");
                return;
            }
            // user inputted an ID, check if it's valid:
            if (ids.contains(Integer.parseInt(choice))) {
                selected = system.findBookingByConfirmation(Integer.parseInt(choice));
                // user confirmed the cancellation, break out of this loop, otherwise return to the list
                if (confirmationLoop(selected, scanner) == 0)
                    return;
            }
            // invalid ID:
            else
                System.out.println("Invalid ID, Please input an ID from the list of available Bookings.");
        } while (true);

    }

    /**
     * Print out list of Bookings
     */
    private static void printBookings(ArrayList<Booking> bookingsList) {
        for (Booking booking : bookingsList)
            System.out.println(booking.getConfirmationNumber() + " " + booking.getRoom().getRoomType().toString());
    }

    /**
     * Confirmation loop for the Guest to determine if they want to cancel the
     * Booking or not.
     * @param selected Booking to cancel
     * @param scanner Input scanner
     * @return 0 if the cancel was confirmed and completed, 1 if the cancel was
     *          denied or the user changed their mind
     */
    private static int confirmationLoop(Booking selected, Scanner scanner) {
        String choice;
        // confirm user wants to cancel that booking:
        do { 
            System.out.println("You're cancelling Booking " + selected.getConfirmationNumber() + ". Is that correct?");
            System.out.println("[Y]es to confirm or [N]o to cancel.");

            choice = scanner.nextLine();
            choice = choice.trim();

            // user confirmed, cancel the booking:
            if (choice.equalsIgnoreCase("y") || choice.equalsIgnoreCase("yes")) {
                selected.setStatus(BookingStatus.CANCELLED);
                System.out.println("Booking " + selected.getConfirmationNumber() + " cancelled.");
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