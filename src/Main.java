
import java.util.Scanner;
import models.*;
import services.*;

public class Main {

    public static void main(String[] args) {
        HotelSystem system = new HotelSystem();
        //system.loadAll();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== HOTEL SYSTEM DEMO ===");
            System.out.println("1) Guest Services");
            System.out.println("2) Staff Operations");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    guestServiceMenu(sc, system);
                    break;
                case "2":
                    staffOperationMenu(sc, system);
                    break;
                case "0":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }

    /**
     * Menu for guest-facing operations: booking, cancel, change, maintenance
     * check in, checkout, payment, guest lookup, uodate booking
     */
    private static void guestServiceMenu(Scanner sc, HotelSystem system) {
        System.out.println("\n=== GUEST SERVICES ===");
        System.out.println("1) Book a Room");
        System.out.println("2) Cancel a Booking");
        System.out.println("3) Change Room");
        System.out.println("4) In-Stay Maintenance");
        System.out.println("5) Check In");
        System.out.println("6) Checkout & Payment");
        System.out.println("7) Guest Lookup");
        System.out.println("8) Update a Booking");
        System.out.println("9) Register a Vehicle");
        System.out.println("10) List Guests");
        System.out.println("11) Update Guests");
        System.out.println("12) Food Menu & Ordering");
        System.out.println("13) Key Replacement");
        System.out.println("0) Back to Main Menu");
        System.out.println("Choose: ");
        String choice = sc.nextLine().trim();
        switch (choice) {
            case "1":
                BookRoom.start(sc, system);
                break;
            case "2":
                CancelService.start(sc, system);
                break;
            case "3":
                ChangeRoom.start(sc, system);
                break;
            case "4":
                InStayMaintenance.start(sc, system);
                break;
            case "5":
                CheckIn.start(sc, system);
                break;
            case "6":
                checkout.start(sc, system);
            case "7":
                GetGuests.start(sc, system);
                break;
            case "8":
                UpdateBookingService.start(sc, system);
                break;
            case "9":
                RegisterVehicle.start(sc,system);
                break;
            case "10":
                ListGuests.start(sc, system);
            case "11":
                UpdateGuest.start(sc, system);
            case "12":
                FoodOrderService.start(sc, system);
                break;
            case "13":
                keyReplacementService.start(sc, system);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid.");
        }
    }

    /**
     * Staff Menu - View all Booking
     */
    private static void staffOperationMenu(Scanner sc, HotelSystem system) {
        System.out.println("\n=== STAFF OPERATIONS ===");
        System.out.println("1) View all Bookings");
        System.out.println("2) Post-Checkout Cleaning & Maintenance");
        System.out.println("3) Hire / Onboard Employee");
        System.out.println("4) View Employees");
        System.out.println("5) Fire Employee");
        System.out.println("0) Back to Main Menu");
        System.out.print("Choose: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                ViewBookingsService.start(sc, system);
                break;
            case "2":
                PostCheckoutCleaningService.start(sc, system);
                break;
            case "3":
                HireEmployeeService.start(sc, system);
                break;
            case "4":
                ViewEmployeeService.start(sc, system);
                break;
            case "5":
                FireEmployee.start(sc, system);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid.");
        }
    }


}
