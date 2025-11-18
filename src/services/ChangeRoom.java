package services;

import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import models.*;

/**
 * Service for Changing Rooms.
 */
public class ChangeRoom {

    /**
     * Start loop for Room Changing Service.
     * @param sc Input scanner for user input.
     * @param system Booking System to utilize.
     */
    public static void start(Scanner sc, HotelSystem system) {
        System.out.println("\n=== GUEST CHANGES ROOM ===");
        System.out.print("Enter confirmation number: ");
        int conf = Integer.parseInt(sc.nextLine().trim());

        Booking b = system.getBookingByConfirmation(conf);
        if (b == null) {
            System.out.println("No booking found.");
            return;
        }

        Calendar start = b.getStartTime();
        Calendar end   = b.getEndTime();
        Room current = system.getRoomByNumber(b.getRoomNumber());

        System.out.println("Current room: " + (current != null ? current.getRoomNumber() + " (" + current.getRoomType() + ")" : "?"));

        // choose type first for convenience
        System.out.println("Choose desired room type:");
        int idx = 1;
        for (RoomType t : RoomType.values()) {
            System.out.println(" " + (idx++) + ") " + t);
        }
        int pickType = Integer.parseInt(sc.nextLine().trim());
        RoomType desired = RoomType.values()[pickType - 1];

        List<Room> avail = system.findAvailableRooms(desired, start, end);
        if (avail.isEmpty()) {
            System.out.println("No rooms available of that type for the booking dates.");
            return;
        }

        System.out.println("Available rooms:");
        for (Room r : avail) {
            System.out.println(" - " + r.getRoomNumber() + " ($" + r.getCost() + "/night)");
        }
        System.out.print("Pick room number: ");
        int newRoom = Integer.parseInt(sc.nextLine().trim());

        boolean ok = system.changeRoom(conf, newRoom);
        System.out.println(ok ? "Room changed." : "Could not change room.");
    }
}
