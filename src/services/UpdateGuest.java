package services;

import models.*;
import java.util.*;

public class UpdateGuest {
    public static void start (Scanner scanner, HotelSystem system) {
        System.out.println("=== UPDATE GUEST INFORMATION ===");
        boolean exit = false;

        while (!exit) {
            int method = chooseSearch(scanner);
            if (method == 0) {
                System.out.println("Returning to previous menu... \n \n");
                return;
            }

            Guest guest = chooseGuest(scanner, system, method);
            if (Objects.isNull(guest)) { //Indicates that the choose guest function was quit early.
                continue;
            }

            int field = chooseField(scanner);
            if (field == 0) {
                System.out.println("\n \n");
                continue;
            }

            String info = getField(scanner, field);

            system.changeGuestInfo(guest, field, info);
            System.out.println("Guest Info changed successfully.");
            return;
        }
    }

    private static int chooseSearch(Scanner scanner) {
        boolean exit = false;
        String choice = "";
        while (!exit) {
            System.out.println("Choose guest search method: ");
            System.out.println("1) Name");
            System.out.println("2) Phone Number");
            System.out.println("3) Guest ID");
            System.out.println("0) Quit");

            choice = scanner.nextLine();
            if (Objects.isNull(choice)) {
                System.out.println("Please input a choice. \n \n");
                continue;
            }
            choice = choice.substring(0, 1);
            if (!(choice.equals("0") || choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                System.out.println("Please input a valid choice. \n \n");
                continue;
            }
            exit = true;
        }
        return Integer.parseInt(choice);
    }

    private static Guest chooseGuest(Scanner scanner, HotelSystem system, int method) {
        boolean exit = false;
        Guest guest = null;
        while (!exit) {
            switch (method) {
                case 1:
                    System.out.println("Enter the full name of a guest: ");
                    break;
                case 2:
                    System.out.println("Enter the phone number of a guest: ");
                    break;
                case 3:
                    System.out.println("Enter the ID of a guest: ");
                    break;
            }
            System.out.println("Enter Q to quit.");
            String input = scanner.nextLine().strip();

            if (Objects.isNull(input)) {
                System.out.println("Please input a value. \n \n");
                continue;
            }

            if (input.equalsIgnoreCase("q")) return null;

            guest = null; //Initialize it with something.
            switch (method) {
                case 1:
                    guest = system.findGuestByPhoneOrName(input);
                    break;
                case 2:
                    guest = system.findGuestByPhoneOrName(input);
                    break;
                case 3:
                    try {
                        guest = system.findGuestByID(Integer.parseInt(input));
                    }
                    catch (NumberFormatException e) {
                        //Do nothing, leave guest be null.
                    }
                    break;
            }

            if (Objects.isNull(guest)) {
                System.out.println("No guests were found for that search. \n \n");
                continue;
            }

            System.out.println("Is this the correct guest?\n");
            printGuest(guest);
            System.out.println("\ny/n");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return guest;
            }
            else {
                System.out.println("\n \n");
                return null;
            }
        }
        return guest;
    }

    private static int chooseField(Scanner scanner) {
        boolean exit = false;
        String choice = "";
        while (!exit) {
            System.out.println("Choose guest field to change: ");
            System.out.println("1) Name");
            System.out.println("2) Phone Number");
            System.out.println("3) EMail");
            System.out.println("0) Quit");

            choice = scanner.nextLine();
            if (Objects.isNull(choice)) {
                System.out.println("Please input a choice. \n \n");
                continue;
            }
            choice = choice.substring(0, 1);
            if (!(choice.equals("0") || choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                System.out.println("Please input a valid choice. \n \n");
                continue;
            }
            exit = true;
        }
        return Integer.parseInt(choice);
    }

    private static String getField(Scanner scanner, int field) {
        boolean exit = false;
        String choice = "";
        while (!exit) {
            switch (field) {
                case 1:
                    System.out.println("Enter a new name: ");
                    break;
                case 2:
                    System.out.println("Enter a new phone number: ");
                    break;
                case 3:
                    System.out.println("Enter a new email: ");
                    break;
            }
            choice = scanner.nextLine();

            if (Objects.isNull(choice)) {
                System.out.println("Please input a new field.");
                continue;
            }

            if (field == 2 && choice.length() != 10) {
                System.out.println("Please input a valid phone number.");
                continue;
            }

            exit = true;
        }
        return choice;
    }

    private static void printGuest(Guest g) {
        System.out.printf("%s, Phone #: %s, EMail: %s, Guest ID#: %d\n", g.getName(), g.getPhoneNumber(), g.getEmail(), g.getGuestId());
    }
}
