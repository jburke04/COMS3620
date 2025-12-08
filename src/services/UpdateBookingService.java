package services;

import java.util.*;
import models.*;

/**
 * Service for Updating a Booking
 */
public class UpdateBookingService {

    /**
     * Starter loop that checks which Booking a user wants to update
     * @param scanner Input scanner for user input
     * @param system Hotel System to utilize
     */
    public static void start(Scanner scanner, HotelSystem system) {
        String choice = "";
        Booking selected;
        System.out.println();

        // UI loop:
        do { 
            System.out.println("=== UPDATE BOOKING ===\n");

            System.out.print("Enter the corresponding confirmation number of the Booking to update. [Q]uit to go back: ");

            choice = scanner.nextLine().trim();

            // user wants to return to previous menu:
            if (choice.equalsIgnoreCase("q") || choice.equalsIgnoreCase("quit")) {
                System.out.println("\nUpdate request cancelled. Returning to Bookings Menu.\n");
                return;
            }
            // check if choice was a valid confirmation number:
            selected = system.getBookingByConfirmation(Integer.parseInt(choice));
            if (selected != null) {
                // information inputted was correct and user did not go back a step:
                if (infoLoop(scanner, selected, system) == 0)
                    return;
            }
            else
                System.out.println("\nInvalid confirmation number. Please input a valid confirmation number.\n");

        } while (true);
    }

    /**
     * Loop to gather information on what to update the booking to.
     * @param scanner Input scanner for user input
     * @param selected Selected Booking to update
     * @param system Hotel System to utilize
     */
    private static int infoLoop(Scanner scanner, Booking selected, HotelSystem system) {
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
                if ((start = parseDate(input)) != null)
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
                if ((end = parseDate(input)) != null)
                    break;
            } while (true);

            // Confirm if user wants to update Booking:
            if (confirmationLoop(scanner, selected, system) == 0) {
                // make sure operation completes:
                if (system.updateBooking(selected, start, end) == true) {
                    System.out.println("\nBooking updated successfully:\n" + selected.toString());
                    return 0;
                }
                // operation failed:
                else 
                    System.out.println("\nBooking could not be updated. Try again.");
            }
            // user declined:
            else {
                System.out.println("\nBooking update cancelled.");
            }
        } while (true);
    }

    /**
     * Parses provided String and sets it as the date
     * @param input Inputted string to parse
     * @param date Date to set with the parsed string
     * @return 0 if the date was parsed successfully, 1 if it was not.
     */
    private static Calendar parseDate(String input) {
        Calendar date = null;
        try {
            String tokens[] = input.split("-");
            int yr = Integer.parseInt(tokens[0]);
            int m = Integer.parseInt(tokens[1]);
            int d = Integer.parseInt(tokens[2]);
            date = GregorianCalendar.getInstance();
            date.set(yr, m - 1, d, 14, 0, 0);
            return date;
        } catch (Exception e) {
            System.out.println("\nInvalid Date. Please try again.");
            return null;
        }
    }

    /**
     * Confirmation loop to ensure the user wants to update this booking and with what information.
     * @param scanner Input scanner for user input.
     * @param selected Selected Booking to update.
     * @param system Hotel System to utilize.
     * @return 0 if confirmed, 1 if not confirmed.
     */
    private static int confirmationLoop(Scanner scanner, Booking selected, HotelSystem system) {
        String input = "";

        // UI loop:
        do { 
            System.out.print("\nAre you sure you want to update? [Y]es or [N]o: ");

            input = scanner.nextLine().trim();

            // confirmed, return 0:
            if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes"))
                return 0;
            // not confirmed, return 1:
            else if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no"))
                return 1;
            // invalid input:
            else 
                System.out.println("\nInvalid input.");
        } while (true);
    }

    private static String fmt(Calendar c) {
        return String.format("%04d-%02d-%02d",
                c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    }
}