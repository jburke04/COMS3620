
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
            System.out.println("1) Book a Room");
            System.out.println("2) Cancel a Booking");
            System.out.println("3) Change Room");
            System.out.println("4) In-Stay Maintenance");
            System.out.println("5) Checkout & Payment");
            System.out.println("6) Guest Lookup");
            System.out.println("7) Update a Booking");
            System.out.println("8) View all Bookings");
            System.out.println("0) Exit");
            System.out.print("Choose: ");
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
                    checkout.start(sc, system);
                    break;
                case "6":
                    GetGuests.start(sc, system);
                    break;
                case "7":
                    UpdateBookingService.start(sc, system);
                    break;
                case "8":
                    ViewBookingsService.start(sc, system);
                    break;
                case "0":
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid.");
            }
        }
    }
}
