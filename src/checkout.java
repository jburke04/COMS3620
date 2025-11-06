package src;

import java.util.Scanner;

public class checkout {
    public static void start(Scanner scanner) {
        String choice = "";
        while (true) {
            System.out.println("Press '[I]nitiate' to begin checkout");
            System.out.println("Press '[Q]uit' to cancel checkout");
            choice = readChoice(scanner);
            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'i' (initiate) or 'q' (quit).");
                continue;
            }
            char ch = choice.charAt(0);
            if (ch == 'i') break;
            if (ch == 'q') {
                System.out.println("Checkout cancelled by agent");
                return;
            }
            System.out.println("Invalid choice. Please enter 'i' (initiate) or 'q' (quit).");
        }

        System.out.println("Retrieving guest stay summary...");
        System.out.println(" - Room rate: $150.00 per night");
        System.out.println(" - Additional charges: $45.75");
        System.out.println(" - Taxes & fees: $19.25");
        System.out.println(" => Total: $215.00");
        System.out.println();

        while (true) {
            System.out.println("Press '[C]onfirm' to accept charges");
            System.out.println("Press '[D]ispute' to dispute charges");
            System.out.println("Press '[Q]uit' to cancel checkout");
            choice = readChoice(scanner);
            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'c' (confirm), 'd' (dispute), or 'q' (quit).");
                continue;
            }
            char ch = choice.charAt(0);
            if (ch == 'd') {
                System.out.println("Guest disputes a charge.");
                return;
            }
            if (ch == 'c') {
                System.out.println("Guest confirms charges.");
                break;
            }
            if (ch == 'q') {
                System.out.println("Checkout cancelled by agent.");
                return;
            }
            System.out.println("Invalid choice. Please enter 'c' (confirm), 'd' (dispute), or 'q' (quit).");
        }

        boolean checkoutComplete = false;
        while (!checkoutComplete) {
            System.out.println();
            System.out.println("[Step 3] Choose payment method:");
            System.out.println("Press '[C]redit Card'");
            System.out.println("Press '[H]ard Cash'");
            System.out.println("Press '[Q]uit' to cancel checkout");
            choice = readChoice(scanner);
            if (choice.length() == 0) {
                System.out.println("Invalid choice. Please enter 'c' (credit), 'h' (cash), or 'q' (quit).");
                continue;
            }
            char c = choice.charAt(0);
            if (c == 'q') {
                System.out.println("Checkout cancelled by agent.");
                break;
            }
            if (c == 'h') {
                // Cash payment path
                System.out.println("Guest chooses Cash payment.");
                checkoutComplete = true;
                break;
            }
            if (c != 'c') {
                System.out.println("Invalid choice. Please enter 'c' (credit), 'h' (cash), or 'q' (quit). Please try again.");
                continue;
            }

            // Credit card payment path
            boolean cardFlowComplete = false;
            while (!cardFlowComplete) {
                System.out.println("Guest chooses Credit Card.");
                System.out.println("Agent enters or verifies payment details (card number, expiry, CVV).");
                System.out.println("Press '[A]pprove' to simulate APPROVED by External Banking System ");
                System.out.println("Press '[N]egate' to simulate DECLINED by External Banking System");
                System.out.println("Press '[Q]uit' to cancel checkout");

                choice = readChoice(scanner);
                if (choice.length() == 0) {
                    System.out.println("Invalid choice. Pleas enter 'a' (approve), 'n' (decline), or 'q' (cancel).");
                    continue;
                }
                char r = choice.charAt(0);
                if (r == 'q') {
                    System.out.println("Checkout cancelled by agent.");
                    checkoutComplete = true;
                    break;
                }
                if (r == 'a') {
                    // Payment approved
                    System.out.println("Payment processed: APPROVED.");
                    cardFlowComplete = true;
                    checkoutComplete = true;
                    break;
                }
                if (r != 'n') {
                    System.out.println("Invalid choice. Expected 'a' (approved) or 'n' (declined). Please try again.");
                    continue;
                }

                // Payment declined alternate flow
                System.out.println("Payment processed: DECLINED by External Banking System.");
                System.out.println("System notifies agent of decline and logs decline reason.");
                System.out.println("Agent options:");
                System.out.println("Press '[R]etry' to retry payment with same card (r).");
                System.out.println("Press '[M]ethod' to choose a different payment method (m).");
                System.out.println("Press '[G]et manager' to contact manager for assistance (g)");
                System.out.println("Press '[Q]uit' to cancel checkout");

                // loop until agent chooses a valid decline option
                while (true) {
                    choice = readChoice(scanner);
                    if (choice.length() == 0) {
                        System.out.println("No input entered — please enter 'r' (retry), 'm' (method), 'g' (get manager), or 'q' (quit).");
                        continue;
                    }
                    char o = choice.charAt(0);
                    if (o == 'r') {
                        System.out.println("Agent retries payment with same card. Re-running card authorization step.");
                        break;
                    }
                    if (o == 'm') {
                        System.out.println("Agent selects a different payment method. Returning to payment method selection.");
                        cardFlowComplete = true; 
                        break;
                    }
                    if (o == 'g') {
                        System.out.println("Agent contacts manager. Manager intervenes to assist with payment or override.");
                        cardFlowComplete = true;
                        checkoutComplete = true;
                        break;
                    }
                    if (o == 'q') {
                        System.out.println("Checkout cancelled by agent.");
                        cardFlowComplete = true;
                        checkoutComplete = true;
                        break;
                    }
                    System.out.println("Invalid option. Enter 'r', 'm', 'g', or 'q'.");
                }
            }
        }
    }

    private static String readChoice(Scanner scanner) {
        System.out.print("Choice: ");
        String line = "";
        try {
            line = scanner.nextLine().trim().toLowerCase();
        } catch (Exception e) {
            return "";
        }
        return line;
    }

}
