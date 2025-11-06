package models;

import java.util.ArrayList;

public class RoomUtils {
    private ArrayList<Booking> bookingsList;
    private ArrayList<Room> roomsList;

    /**
     * Constructor for the Room Utility class that takes a filepath for the list of Rooms
     * within a Hotel and a filepath for the list of Bookings within that Hotel's Booking
     * System
     * @param f1 filepath to a file with the list of rooms
     * @param f2 filepath to a file with the list of bookings
     */
    public RoomUtils(String f1, String f2) {
        // TODO: have this constructor iterate through each file to get each room and booking
    }

    public RoomUtils(ArrayList<Room> roomsList, ArrayList<Booking> bookingsList) {
        this.roomsList = roomsList;
        this.bookingsList = bookingsList;
    }

    /**
     * Checks if the Booking exists within the list of Bookings for this Hotel
     * @return true if the Booking exists, false if it doesn't
     */
    public boolean isExisting(Booking booking) {
        return bookingsList.contains(booking);
    }
}