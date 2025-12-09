package services;

import java.util.*;
import models.*;

/**
 * Service for creating a Food Service Request on behalf of a Guest
 */
public class FoodServiceRequestService {

    /**
     * Array of item costs to apply to the final Booking cost:
     */
    private static final double costs[] = {
        8.50,
        11.50,
        8.40,
        12.50,
        18.60,
        25.00,
        4.00,
        6.80,
        7.60,
        5.30
    };

    private static final String items[] = {
        "Deluxe Club Sandwich",
        "10 in. Pizza",
        "3pc. Chicken Tenders",
        "Spaghetti & Meatballs",
        "8oz Steak & Mushrooms",
        "Buttered Lobster",
        "Chocolate Chip Cookie",
        "Molten Lava Cake",
        "Classic Cheesecake",
        "Creme Brulee"
    };

    /**
     * Start loop for creating a Food Service Request.
     * @param sc Scanner for User input.
     * @param system Hotel System to utilize.
     */
    public static void start(Scanner sc, HotelSystem system) {
        String input = "";
        Guest g = null;
        Room r = null;
        double total = 0;
        String desc = "";

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
                    r = checkRoom(system, Integer.parseInt(input.trim()), g);

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


        // Food menu:
            System.out.println("Menu:\n"
                + "\t1.  Deluxe Club Sandwich \t$8.50\n"
                + "\t2.  10 in. Pizza         \t$11.50\n"
                + "\t3.  3pc. Chicken Tenders \t$8.40\n"
                + "\t4.  Spaghetti & Meatballs\t$12.50\n"
                + "\t5.  8oz Steak & Mushrooms\t$18.60\n"
                + "\t6.  Buttered Lobster     \t$25.00\n"
                + "\t7.  Chocolate Chip Cookie\t$4.00\n"
                + "\t8.  Molten Lava Cake     \t$6.80\n"
                + "\t9.  Classic Cheesecake   \t$7.60\n"
                + "\t10. Creme Brulee         \t$5.30\n");
        do {

            System.out.print("Select item(s) to order (separate by commas). [Q]uit to return to Main Menu: ");
            input = sc.nextLine();

            String[] inputs = input.trim().split(",");

            if (inputs.length == 0)
                System.out.println("\nEmpty string. Please provide entries for the request or terminate the process.");
            // check for valid input:
            else if ((total = calcCost(inputs)) > 0) {
                System.out.printf("\nCost of Request: $%.2f\n", total);

                // Optional Special Instructions for order:
                System.out.print("Special Requests (Enter if none): ");
                String spInstr = sc.nextLine().trim();

                // Format Description:
                desc = formatDescription(inputs, spInstr);

                break;
            }

        } while (true);

        // Create ticket:
        Request f = new FoodRequest(g.getGuestId(), r, total, desc);
        system.addRequest(f);
        System.out.println("\nFood Service Request Created.\n" + f.toString());
    }

    /**
     * Checks if the provided room number exists, is checked in, and is checked in by that Guest.
     * @param system Hotel System to utilize.
     * @param roomNumber Room Number to check.
     * @return Room that corresponds to the provided Room number if it satisfies all conditions, null if not.
     */
    private static Room checkRoom(HotelSystem system, int roomNumber, Guest g) {
        Room r = null;

        //room doesn't exist:
        if ((r = system.getRoomByNumber(roomNumber)) == null)
            System.out.println("\nRoom number does not exist. Please provide an existing Room Number.\n"); 
        //room isn't checked in:
        else if (r.getStatus() != Status.OCCUPIED) {
            System.out.println("\nRoom must currently be checked in to provide a Food Service Request.\n");
            return null;
        }
        
        //check if Guest is checked in to this room:
        for (Booking b : system.getBookings()) {
            if (b.getRoom() == r) {
                if (b.getGuestID() != g.getGuestId()) {
                    System.out.println("\nGuest is not Checked-in to this Room. Guest needs to be checked-in to the Room to create a Request.\n");
                    return null;
                }
                else
                    break;
            }
        }

        // all preconditions met, return Room:
        return r;
    }

    /**
     * Calculates the cost of all ordered items
     * 
     */
    private static double calcCost(String[] inputs) {
        double total = 0.00;
        
        // iterate through provided list of inputs:
        for (String s : inputs) {
            // entry was not a number:
            if (Double.isNaN(Double.parseDouble(s))) {
                System.out.println("\nInvalid input. Make sure all entries are numbers.");
                return 0.00;
            }
            // entry out of bounds or added a ".":
            else if (Integer.parseInt(s) > 10 || Integer.parseInt(s) < 1 || Double.parseDouble(s) % 1 > 0) {
                System.out.println("\nInvalid input. Entries must be a whole number between 1-10.");
                return 0.00;
            }
            else {
                total += costs[Integer.parseInt(s) - 1];
            }
        }

        // return total:
        return total;
    }

    /**
     * Formats the description to provide the item ordered, the number of times ordered,
     * and any special instructions requested.
     * @param inputs List of inputted entries for request.
     * @param instr Special instructions per Guest request.
     * @return String representation of the full description.
     */
    private static String formatDescription(String[] inputs, String instr) {
        String result = "Order: \n";
        int[] entries = new int[10];

        // calculate how many of each entry in this order:
        for (String s : inputs) {
            entries[Integer.parseInt(s) - 1]++;
        }

        // format into result string:
        for (int i = 0; i < 10; i++) {
            if (entries[i] != 0)
                result += "\t" + entries[i] + "x " + items[i];
            if (i < 9)
                result += "\n";
        }

        // append Special Instructions if any:
        if (instr.length() > 0)
            result += "\nSpecial Instructions: " + instr;

        return result;
    }
}
