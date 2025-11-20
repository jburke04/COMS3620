package services;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import models.*;

/**
 * Service for Checking In.
 */
public class CheckIn {

    /**
     * Start loop for Check In Service.
     * @param scanner Input scanner for user input.
     * @param system Hotel System to utilize.
     */
    public static void start(Scanner scanner, HotelSystem system) {
        while (true) {
            System.out.println("\n=== CHECK-IN SERVICE ===");
            System.out.println("1) Check in by confirmation number");
            System.out.println("2) Find guest bookings by name/phone");
            System.out.println("0) Back to previous menu");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "1":
                    handleCheckInByConfirmationNumber(scanner, system);
                    break;
                case "2":
                    handleCheckInByGuestLookup(scanner, system);
                    break;
                case "0":
                case "q":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Check in using confirmation number.
     */
    private static void handleCheckInByConfirmationNumber(Scanner scanner, HotelSystem system) {
        System.out.print("Enter confirmation number (or blank to cancel): ");
        String line = scanner.nextLine().trim();
        if (line.isEmpty()) {
            System.out.println("Check-in cancelled.");
            return;
        }

        int confirmationNumber;
        try {
            confirmationNumber = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid confirmation number.");
            return;
        }

        // Ask about early arrival & payment before attempting check-in
        // (we don't have direct access to the Booking here, so we simulate the flow)
        System.out.print("Is this guest arriving earlier than their scheduled check-in time? (y/n): ");
        String early = scanner.nextLine().trim().toLowerCase();
        if (early.startsWith("y")) {
            handleEarlyCheckIn(scanner, null); // no Booking object here, so pass null
        }

        boolean paymentOk = processPayment(scanner);
        if (!paymentOk) {
            handlePaymentFailure(scanner);
            return;
        }

        boolean success = system.checkInByNumber(confirmationNumber);
        if (success) {
            System.out.println("Check-in successful for confirmation #" + confirmationNumber);
        } else {
            System.out.println("Check-in failed. The booking may not exist or may not be eligible for check-in.");
            handleBookingNotFound(scanner, system);
        }
    }

    /**
     * Check in by looking up the guest's bookings.
     */
    private static void handleCheckInByGuestLookup(Scanner scanner, HotelSystem system) {
        System.out.print("Enter guest name or phone: ");
        String key = scanner.nextLine().trim();

        if (key.isEmpty()) {
            System.out.println("No input entered.");
            return;
        }

        List<Booking> bookings = system.getConfirmedBookingsByGuestNameOrPhone(key);
        if (bookings == null || bookings.isEmpty()) {
            System.out.println("No confirmed bookings found for that guest.");
            handleBookingNotFound(scanner, system);
            return;
        }

        System.out.println("\nConfirmed bookings for guest:");
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            System.out.println(
                    (i + 1) + ") Confirmation #" + b.getConfirmationNumber() +
                            " | Room " + b.getRoom().getRoomNumber() +
                            " | " + formatDate(b.getStartTime()) + " to " + formatDate(b.getEndTime())
            );
        }

        System.out.print("Select booking number to check in (or 0 to cancel): ");
        String line = scanner.nextLine().trim();
        if (line.equals("0") || line.isEmpty()) {
            System.out.println("Check-in cancelled.");
            return;
        }

        int idx;
        try {
            idx = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Invalid selection.");
            return;
        }

        if (idx < 1 || idx > bookings.size()) {
            System.out.println("Selection out of range.");
            return;
        }

        Booking selected = bookings.get(idx - 1);

        // === Alternate flow: Early check-in ===
        Calendar now = Calendar.getInstance();
        Calendar start = selected.getStartTime();
        if (now.before(start)) {
            handleEarlyCheckIn(scanner, selected);
            // Guest may decide to cancel check-in attempt
            System.out.print("Proceed with check-in attempt anyway? (y/n): ");
            String proceed = scanner.nextLine().trim().toLowerCase();
            if (!proceed.startsWith("y")) {
                System.out.println("Check-in postponed.");
                return;
            }
        }

        // === Alternate flow: Room not ready (CLEANING / INSPECTING / AWAITING) ===
        Status roomStatus = selected.getRoom().getStatus();
        if (roomStatus == Status.CLEANING ||
                roomStatus == Status.INSPECTING ||
                roomStatus == Status.AWAITING) {

            handleRoomNotReady(scanner, system, selected);

            // After handling, ask if we should still attempt check-in
            System.out.print("Attempt to check in to this room now? (y/n): ");
            String attempt = scanner.nextLine().trim().toLowerCase();
            if (!attempt.startsWith("y")) {
                System.out.println("Check-in cancelled for now.");
                return;
            }
        }

        // === Payment processing before check-in ===
        boolean paymentOk = processPayment(scanner);
        if (!paymentOk) {
            handlePaymentFailure(scanner);
            return;
        }

        boolean success = system.checkInByNumber(selected.getConfirmationNumber());
        if (success) {
            System.out.println("Check-in successful for confirmation #" + selected.getConfirmationNumber());
        } else {
            System.out.println("Check-in failed for this booking.");
            handleBookingNotFound(scanner, system);
        }
    }

    /**
     * Booking not found / cannot check-in alternate path.
     */
    private static void handleBookingNotFound(Scanner scanner, HotelSystem system){
        System.out.println("\n--- Booking Not Found / Not Eligible ---");
        System.out.println("1) Try another confirmation number");
        System.out.println("2) Search by guest name/phone");
        System.out.println("3) Create a new booking instead");
        System.out.println("0) Return to Check-In menu");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "1":
                handleCheckInByConfirmationNumber(scanner, system);
                break;
            case "2":
                handleCheckInByGuestLookup(scanner, system);
                break;
            case "3":
                offerNewBooking(scanner, system);
                break;
            case "0":
            case "q":
                System.out.println("Returning to Check-In menu.");
                break;
            default:
                System.out.println("Invalid choice. Returning to Check-In menu.");
        }
    }

    /**
     * Offer to route user to the booking flow if no valid booking exists.
     * (We do not call BookRoom directly to avoid extra dependencies; we just guide the clerk.)
     */
    private static void offerNewBooking(Scanner scanner, HotelSystem system) {
        System.out.println("\nThe guest does not have a valid booking.");
        System.out.print("Would you like to create a new booking now? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        if (ans.startsWith("y")) {
            System.out.println("Please return to the Guest Services menu and select 'Book a Room'.");
        } else {
            System.out.println("No new booking created. Check-in will not proceed.");
        }
    }

    /**
     * Handle early check-in policies (before scheduled start time).
     */
    private static void handleEarlyCheckIn(Scanner scanner, Booking booking) {
        System.out.println("\n--- Early Check-In ---");
        System.out.println("Guest is arriving before scheduled check-in time.");
        System.out.println("Policy options:");
        System.out.println("1) Allow early check-in with no extra fee");
        System.out.println("2) Allow early check-in with an extra fee");
        System.out.println("3) Do not allow early check-in (guest waits until standard time)");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "1":
                System.out.println("Early check-in approved with no extra fee.");
                break;
            case "2":
                System.out.print("Enter early check-in fee amount (just for record): ");
                String fee = scanner.nextLine().trim();
                System.out.println("Early check-in approved with fee: " + fee);
                break;
            case "3":
                System.out.println("Early check-in not allowed. Guest should wait until normal check-in time.");
                offerLuggageStorage(scanner);
                break;
            default:
                System.out.println("No valid option selected. Defaulting to: guest waits until normal check-in time.");
                offerLuggageStorage(scanner);
        }
    }

    /**
     * Offer luggage storage when room is not yet ready or early check-in not allowed.
     */
    private static void offerLuggageStorage(Scanner scanner) {
        System.out.print("Offer luggage storage while the guest waits? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        if (ans.startsWith("y")) {
            System.out.println("Luggage storage offered. Tag and store guest bags according to hotel policy.");
        } else {
            System.out.println("Guest declined luggage storage.");
        }
    }

    /**
     * Handle room-not-ready alternate flow.
     */
    private static void handleRoomNotReady(Scanner scanner, HotelSystem system, Booking booking) {
        System.out.println("\n--- Room Not Ready ---");
        System.out.println("Current room status: " + booking.getRoom().getStatus());
        System.out.println("Options:");
        System.out.println("1) Ask guest to wait until room is ready");
        System.out.println("2) Offer different available room (same type/price if possible)");
        System.out.println("3) Offer a temporary room or lounge access");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "1":
                System.out.println("Guest will wait until the room is ready.");
                offerLuggageStorage(scanner);
                break;
            case "2":
                System.out.println("Please use the booking/room tools to assign a different room.");
                System.out.println("(In this version, room reassignment is handled outside CheckIn service.)");
                break;
            case "3":
                System.out.println("Guest is given temporary access (e.g., lounge or holding room).");
                offerLuggageStorage(scanner);
                break;
            default:
                System.out.println("Invalid option. Treating as 'guest waits until room is ready'.");
                offerLuggageStorage(scanner);
        }
    }

    /**
     * Simulate payment processing at check-in.
     * @return true if payment succeeded, false otherwise.
     */
    private static boolean processPayment(Scanner scanner){
        System.out.print("Does the guest need to pay any remaining balance at check-in? (y/n): ");
        String need = scanner.nextLine().trim().toLowerCase();
        if (!need.startsWith("y")) {
            // No payment needed.
            return true;
        }

        while (true) {
            System.out.println("\nChoose payment method:");
            System.out.println("1) Credit card");
            System.out.println("2) Cash");
            System.out.println("0) Cancel payment");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "1":
                    System.out.println("Simulating credit card authorization...");
                    System.out.print("Approve payment? (y/n): ");
                    String approve = scanner.nextLine().trim().toLowerCase();
                    if (approve.startsWith("y")) {
                        System.out.println("Payment approved.");
                        return true;
                    } else {
                        System.out.println("Payment declined by external system.");
                        return false;
                    }
                case "2":
                    System.out.println("Cash payment recorded.");
                    return true;
                case "0":
                case "q":
                    System.out.println("Payment cancelled.");
                    return false;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    /**
     * Handle what happens when payment fails during check-in.
     */
    private static void handlePaymentFailure(Scanner scanner){
        System.out.println("\n--- Payment Failure ---");
        System.out.println("The payment could not be completed.");
        System.out.println("Options for the agent:");
        System.out.println("1) Retry payment");
        System.out.println("2) Ask for a different payment method");
        System.out.println("3) Contact manager / escalate");
        System.out.println("4) Cancel check-in");
        System.out.print("Choice: ");

        String choice = scanner.nextLine().trim().toLowerCase();
        switch (choice) {
            case "1":
                System.out.println("Please retry the payment step.");
                break;
            case "2":
                System.out.println("Ask guest for a different card or cash.");
                break;
            case "3":
                System.out.println("Manager is contacted to assist with the payment issue.");
                break;
            case "4":
                System.out.println("Check-in cancelled due to payment failure.");
                break;
            default:
                System.out.println("Invalid option. Treating as: check-in cancelled due to payment failure.");
        }
    }

    private static String formatDate(Calendar cal) {
        if (cal == null) return "";
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

}
