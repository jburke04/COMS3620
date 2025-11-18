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
                // information inputted was correct and user did not go back a step:
                if (infoLoop(scanner, selected, system) == 0)
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
    private int infoLoop(Scanner scanner, Booking selected, HotelSystem system) {
        //TODO: get the dates to change it to and check if the dates are valid
        System.out.println("Updating booking " + selected.getConfirmationNumber());
        String input = "";
        Calendar start , end;
        start = end = null;

        // UI loop:
        do { 
            System.out.println("Input required information ([Q]uit to go back)-------\n");

            // get start date:
            do { 
                System.out.print("Start date (YYYY-MM-DD): ");
                input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                    System.out.println("Returning to list of Bookings.");
                    return 1;
                }
                // check if inputted date was formatted correctly:
                if (parseDate(input, start) == 0)
                    break;                
            } while (true);

            // get end date:
            do { 
                System.out.print("\nEnd date (YYYY-MM-DD): ");
                input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                    System.out.println("Returning to list of Bookings.");
                    return 1;
                }
                // check if inputted date was formatted correctly:
                if (parseDate(input, end) == 0)
                    break;
            } while (true);

            // check if dates are valid:
            if (system.updateBooking(selected, start, end) == true)
                return 0;
            else
                System.out.println("\nCould not update Booking. Try again.");
        } while (true);
    }

    /**
     * Parses provided String and sets it as the date
     * @param input Inputted string to parse
     * @param date Date to set with the parsed string
     */
    private int parseDate(String input, Calendar date) {
        date.clear();
        try {
            String tokens[] = input.split("-");
            int yr = Integer.parseInt(tokens[0]);
            int m = Integer.parseInt(tokens[1]);
            int d = Integer.parseInt(tokens[2]);
            date.set(yr, m - 1, d);
            return 0;
        } catch (Exception e) {
            System.out.println("\nInvalid Date. Please try again.");
            return 1;
        }
    }

    /**
     * Confirmation loop to ensure the user wants to update this booking and with what information.
     */
    private void confirmationLoop(Scanner scanner, Booking selected, HotelSystem system) {

    }
}