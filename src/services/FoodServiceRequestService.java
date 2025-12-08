package services;

import java.util.*;
import models.*;

/**
 * Service for creating a Food Service Request on behalf of a Guest
 */
public class FoodServiceRequestService {

    /**
     * Start loop for creating a Food Service Request
     *
     * @param sc Scanner for User input.
     * @param system Hotel System to utilize.
     */
    public static void start(Scanner sc, HotelSystem system) {
        String input = "";
        Guest g = null;
        Room r = null;

        System.out.println("\n=== CREATE FOOD SERVICE REQUEST ===\n");

        do {
            //Ask for Guest ID or Name:
            System.out.print("Enter Guest ID or Name. [Q]uit to go back: ");
            input = sc.nextLine();

            if (input.trim().equalsIgnoreCase("q") || input.trim().equalsIgnoreCase("quit")) {
                System.out.println("\nReturning to previous Menu.\n");
                return;
            }

            // check if Guest exists:
            if (Double.isNaN(Double.parseDouble(input.trim()))) {
                g = system.findGuestByPhoneOrName(input);
            } else {
                g = system.findGuestByID(Integer.parseInt(input));
            }

            // Guest exists:
            if (g != null) {
                boolean good = false;
                do {
                    // Ask for Room number:
                    System.out.print("\nEnter Room Number. [Q]uit to exit, [B]ack to go back to the Guest ID: ");
                    input = sc.nextLine();

                    // exiting:
                    if (input.trim().equalsIgnoreCase("q") || input.trim().equalsIgnoreCase("quit")) {
                        System.out.println("\nReturning to previous Menu.\n");
                        return;
                    }
                    // going back to guest ID:
                    if (input.trim().equalsIgnoreCase("b") || input.trim().equalsIgnoreCase("back"))
                        break;

                    // invalid input:
                    if (Double.isNaN(Double.parseDouble(input.trim())))
                        System.out.println("\nInvalid input. Please input an integer value for the Room Number.\n"); 

                    // check if Room satisfies preconditions:
                    r = checkRoom(system, Integer.parseInt(input.trim()));

                    // Room satisfied preconditions:
                    if (r != null) {
                        good = true;
                        break;
                    }

                } while (true);

                if (good)
                    break;
            } else
                System.out.println("\nProvided Name or ID does not exist in System. Please provide an existing ID or Name.\n");
        } while (true);

        //TODO: after confirming Guest and Room, choose item from menu
        do {

        } while (true);

        //TODO: after choosing item(s), calculate the cost and add it on to the Booking
    }

    /**
     * Checks if the provided room number exists, is checked in, and is checked in by that Guest.
     * @param system Hotel System to utilize.
     * @param roomNumber Room Number to check.
     * @return Room that corresponds to the provided Room number if it satisfies all conditions, null if not.
     */
    private static Room checkRoom(HotelSystem system, int roomNumber) {
        Room r = null;

        //room doesn't exist:
        if ((r = system.getRoomByNumber(roomNumber)) == null)
            System.out.println("\nRoom number does not exist. Please provide an existing Room Number.\n"); 
        //room isn't checked in:
        else if (r.getStatus != Status.OCCUPIED) {
            System.out.println("\nRoom must currently be checked in to provide a Food Service Request.\n");
            return null;
        }
        //room isn't checked in by Guest:


        return r;
    }

    /**
     * Calculates the cost of all ordered items
     */
    private static void calcCost(HotelSystem system, String[] inputs) {

    }
}
