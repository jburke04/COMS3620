package services;

import java.util.Scanner;
import models.*;

public class UpdateBookingService {

    /**
     * Starter loop that checks which Booking a user wants to update
     * @param scanner Input scanner for user input
     * @param system Hotel System to utilize
     */
    public void start(Scanner scanner, HotelSystem system) {
        String choice = "";
        Booking selected;
        System.out.println();

        // UI loop:
        do { 
            System.out.println("=== UPDATE BOOKING ===\n");

            System.out.println("List of Bookings:\n");
            printBookings(system);

            System.out.print("\nEnter the corresponding ID of the Booking to update. [Q]uit to go back: ");

            choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("\nUpdate request cancelled. Returning to Bookings Menu.\n");
                return;
            }
            // check if choice was a valid confirmation number:
            selected = system.getBookingByConfirmation(Integer.parseInt(choice));
            if (selected != null) {
                if (confirmationLoop(scanner, selected, system) == 0)
                    return;
            }
            else
                System.out.println("\nInvalid ID. Please input an ID from the list of Bookings.\n");

        } while (true);
    }

    /**
     * Prints Bookings with their confirmation number, start time, and end time.
     */
    private void printBookings(HotelSystem system) {
        for (Booking b : system.getBookings()) {
            System.println(b.getConfirmationNumber + ": \n" +
            "\tStart: " + b.getStartTime.toString() +
            "\n\tEnd: " + b.getEndTime.toString());
        }
    }

    /**
     * Loop to gather information on what to update the booking to.
     * @param scanner Input scanner for user input
     * @param selected Selected Booking to update
     * @param system Hotel System to utilize
     */
    private void infoLoop(Scanner scanner, Booking selected, HotelSystem system) {

    }

    /**
     * Confirmation loop to ensure the user wants to update this booking and with what information.
     */
    private void confirmationLoop(Scanner scanner, Booking selected, HotelSystem system) {

    }
}