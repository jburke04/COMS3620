package services;

import models.HotelSystem;
import java.util.Scanner;

public class keyReplacementService {

    public static void start(Scanner scanner, HotelSystem system) {
        String choice = "";

        // Step 1 – Initiation
        while (true) {
            System.out.println("Press '[I]nitiate' to begin key replacement request");
            System.out.println("Press '[Q]uit' to cancel key replacement");
            choice = readChoice(scanner);

            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'i' (initiate) or 'q' (quit).");
                continue;
            }

            char ch = choice.charAt(0);
            if (ch == 'i') break;
            if (ch == 'q') {
                System.out.println("Key Replacement cancelled by agent.");
                return;
            }

            System.out.println("Invalid choice. Please enter 'i' or 'q'.");
        }

        // Step 2 – Ask for Guest Confirmation Number
        int confirmationNumber;
        while (true) {
            System.out.println("Please enter the confirmation number: ");
            choice = readChoice(scanner);

            try {
                int num = Integer.parseInt(choice);
                if (system.validateBooking(num)) {
                    confirmationNumber = num;
                    break;
                } else {
                    System.out.println("Invalid Booking Number.");
                }
            } catch (Exception e) {
                System.out.println("Invalid number. Please try again.");
            }
        }

        // Step 3 – Explain reason for key replacement
        System.out.println("Please choose the reason for key replacement:");
        System.out.println("Press '[L]ost key'");
        System.out.println("Press '[D]amaged key'");
        System.out.println("Press '[G]uest locked out'");
        System.out.println("Press '[Q]uit' to cancel");
        char reasonChar;

        while (true) {
            choice = readChoice(scanner);
            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'l', 'd', 'g', or 'q'.");
                continue;
            }

            reasonChar = choice.charAt(0);

            if (reasonChar == 'q') {
                System.out.println("Key Replacement cancelled by agent.");
                return;
            }
            if (reasonChar == 'l' || reasonChar == 'd' || reasonChar == 'g')
                break;

            System.out.println("Invalid choice. Please enter 'l', 'd', 'g', or 'q'.");
        }

        // Step 4 – Security Verification
        System.out.println("Security Verification required:");
        System.out.println("Ask guest for ID or security question.");
        System.out.println("Press '[V]erify' to confirm guest identity");
        System.out.println("Press '[Q]uit' to cancel");

        while (true) {
            choice = readChoice(scanner);
            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'v' (verify) or 'q' (quit).");
                continue;
            }

            char sec = choice.charAt(0);
            if (sec == 'v') {
                System.out.println("Guest identity verified.");
                break;
            }
            if (sec == 'q') {
                System.out.println("Key Replacement cancelled by agent.");
                return;
            }

            System.out.println("Invalid choice. Please enter 'v' or 'q'.");
        }

        // Step 5 – Issue new key
        System.out.println();
        System.out.println("Issuing new keycard...");
        System.out.println("Encoding room access...");
        System.out.println("Key Replacement completed successfully.");


        System.out.println("New key issued for booking #" + confirmationNumber);
    }

    private static String readChoice(Scanner scanner) {
        System.out.print("Choice: ");
        try {
            return scanner.nextLine().trim().toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }
}
