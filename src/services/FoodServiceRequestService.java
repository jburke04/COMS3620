package services;

import java.util.*;
import models.*;

/**
 * Service for creating a Food Service Request on behalf of a Guest
 */
public class FoodServiceRequestService {

    /**
     * Start loop for creating a Food Service Request
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
            if (Double.isNaN(Double.parseDouble(input.trim())))
                g = system.findGuestByPhoneOrName(input);
            else
                g = system.findGuestByID(Integer.parseInt(input));

            if (g != null)
                break;
            else {
                System.out.println("\nProvided Name or ID does not exist in System. Please provide an existing ID or Name.\n");
            }
        } while (true);

        do { 
            //Ask for Room number:
            System.out.print("\nEnter Room Number. [Q]uit to exit: ");
            input = sc.nextLine();

            if (input.trim().equalsIgnoreCase("q") || input.trim().equalsIgnoreCase("quit")) {
                System.out.println("\nReturning to previous Menu.\n");
                return;
            }

            //TODO: check if Room exists, if Room is checked in, and if this Guest

            
        } while (true);

        //TODO: after confirming Guest and Room, choose item from menu

        //TODO: after choosing item(s), calculate the cost and add it on to the Booking
    }

    /**
     * Calculates the cost of all ordered items
     */
    private static void calcCost(HotelSystem system, String[] inputs) {

    }
}