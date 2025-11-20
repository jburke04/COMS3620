package services;

import models.*;

import java.util.*;

public class ListGuests {
    public static void start(Scanner scanner, HotelSystem system) {

        System.out.println("=== LIST GUESTS ===");

        String choice = makeChoice(scanner);

        switch (choice) {
            case "0":
                System.out.println("Returning to previous menu... \n \n");
                return;
            case "1":
                System.out.println("Listing all guests in system... \n \n");
                List<Guest> guests = system.getGuests();
                if (guests.isEmpty()) {
                    System.out.println("No guests in system. \n \n");
                    return;
                }
                System.out.println(String.format("NAME                           PHONE      EMAIL                       GUEST ID#"));
                for (Guest g : guests) {
                    printGuest(g);
                }
                return;
            case "2":
                System.out.println("Listing all guests with active bookings... \n \n");
                List<Guest> activeGuests = new ArrayList<>();
                for (Booking b : system.getConfirmedBookings()) {
                    if (!activeGuests.contains(system.findGuestByID(b.getGuestID()))) {
                        activeGuests.add(system.findGuestByID(b.getGuestID()));
                    }
                }
                if (activeGuests.isEmpty()) {
                    System.out.println("No guests with active bookings. \n \n");
                    return;
                }
                System.out.println(String.format("NAME                           PHONE      EMAIL                       GUEST ID#"));
                for (Guest g : activeGuests) {
                    printGuest(g);
                }
                return;
            case "3":
                System.out.println("Listing all guests with past bookings... \n \n");
                List<Guest> pastGuests = new ArrayList<>();
                for (Booking b : system.getPastBookings()) {
                    if (!pastGuests.contains(system.findGuestByID(b.getGuestID()))) {
                        pastGuests.add(system.findGuestByID(b.getGuestID()));
                    }
                }
                if (pastGuests.isEmpty()) {
                    System.out.println("No guests with past bookings. \n \n");
                    return;
                }
                System.out.println(String.format("NAME                           PHONE      EMAIL                       GUEST ID#"));
                for (Guest g : pastGuests) {
                    printGuest(g);
                }
                return;
        }

    }

    private static String makeChoice(Scanner scanner) {
        boolean exit = false;
        String choice = "";
        while (!exit) {
            System.out.println("Select which guests to display: ");
            System.out.println("1) All Guests");
            System.out.println("2) Guests with Active Bookings");
            System.out.println("3) Guests with Past Bookings");
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
        return choice;
    }

    private static void printGuest(Guest g) {
        System.out.printf("%-30s %-10s %-35s %d\n", g.getName(), g.getPhoneNumber(), g.getEmail(), g.getGuestId());
    }
}
