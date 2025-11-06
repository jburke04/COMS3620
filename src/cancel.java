package src;

import java.util.Scanner;
import models.*;
import java.util.ArrayList;

/**
 * User Interface that handles the cancellation process.
 */
public class cancel {

    public static void start(Scanner scanner, ArrayList<Booking> bookings) {
        String choice = "";
        ArrayList<Integer> ids = new ArrayList<>();
        Booking selected;

        for (int i = 0; i < bookings.size(); i++) {
            ids.add(bookings.get(i).getBookingID());
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
            if (choice.toLowerCase().equals("q")
                || choice.toLowerCase().equals("quit")) {
                System.out.println("Cancellation request cancelled, returning to main screen.");
                return;
            }
            // user inputted an ID, check if it's valid:
            if (ids.contains(Integer.parseInt(choice))) {
                selected = findBookingByID(Integer.parseInt(choice), bookings);
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
        for (int i = 0; i < bookingsList.size(); i++)
            System.out.println(bookingsList.get(i).getBookingID + " " + getRoomType(bookingsList.get(i).getRoom()));
    }

    /**
     * Find Booking by provided Booking ID
     * @param int ID of Booking to find
     * @param ArrayList List of Bookings to search through
     * @return Booking that corresponds to provided ID
     */
    private static Booking findBookingByID(int bookingID, ArrayList<Booking> bookingsList) {
        for (int i = 0; i < bookingsList.size(); i++) {
            if (bookingsList.get(i).getBookingID == bookingID)
                return bookingsList.get(i);
        }

        return null;
    }

    /**
     * Print out String representation or Room Type
     * @param Room Room to convert to String
     * @return String representation of the Room type
     */
    private static String getRoomType(Room room) {
        switch (room.getRoomType()) {
            case SINGLE -> {
                return "Single Bed";
            }
            case DOUBLE -> {
                return "Double Bed";
            }
            case KING -> {
                return "King Bed";
            }
            case PRESIDENTIAL -> {
                return "Presidential Suite";
            } 
        }
        return ""; 
    }

    private int confirmationLoop(Booking selected, Scanner scanner) {
        String choice;
        // confirm user wants to cancel that booking:
        do { 
            System.out.println("You're cancelling Booking " + selected.getBookingID + ". Is that correct?");
            System.out.println("[Y]es to confirm or [N]o to cancel.");

            choice = scanner.nextLine();
            choice = choice.trim();

            // user confirmed, cancel the booking:
            if (choice.toLowerCase().equals("y") || choice.toLowerCase().equals("yes")) {
                selected.setStatus(BookingStatus.CANCELLED);
                System.out.println("Booking " + selected.getBookingID + " cancelled.");
                break;
            }
            else if (choice.toLowerCase().equals("n") || choice.toLowerCase().equals("no")) {
                System.out.println("Returning to Booking selection.");
                return 1;
            }
            else
                System.out.println("Invalid input.");
        } while (true);

        return 0;
    }
}