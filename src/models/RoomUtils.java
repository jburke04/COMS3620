package models;

import java.util.*;

public class RoomUtils {
    private List<Booking> bookingsList;
    private List<Room> roomsList;

    public RoomUtils(List<Booking> bookings, List<Room> rooms) {
        this.bookingsList = bookings;
        this.roomsList = rooms;
    }

    public RoomUtils(ArrayList<Room> roomsList, ArrayList<Booking> bookingsList) {
        this.roomsList = roomsList;
        this.bookingsList = bookingsList;
    }

    public List<Room> getRooms() {
        return this.roomsList;
    }

    public List<Booking> getBookings() {
        return this.bookingsList;
    }

    /**
     * Iterates through the list of rooms in the Hotel to find the Room with
     * the corresponding Room Number. Returns null if no such Room Number exists.
     * @param roomNumber Room Number to search for
     * @return Room with the corresponding Room Number, or null if no such Room
     *          Number exists.
     */
    public Room findRoom(int roomNumber) {
        // see if this room exists in the list of rooms:
        for (Room r : this.roomsList) {
            if (r.getRoomNumber() == roomNumber)
                return r;
        }

        // no such room number exists, return null:
        return null;
    }

    /**
     * Iterates through the list of confirmation numbers to find the Booking with
     * the corresponding confirmation number. Returns null if no such Booking
     * exists.
     * @param confirmationNumber confirmation number to look for
     * @return corresponding Booking with that confirmation number, or null
     *          if it doesn't exist.
     */
    public Booking findBooking(int confirmationNumber) {
        // see if this booking exists in the system:
        for (Booking b : this.bookingsList) {
            if (b.getConfirmationNumber() == confirmationNumber)
                return b;
        }

        // no such confirmation number exists, return null:
        return null;
    }

    /**
     * Checks if the Booking exists within the list of Bookings for this Hotel
     * @return true if the Booking exists, false if it doesn't
     */
    public boolean isExisting(Booking booking) {
        return bookingsList.contains(booking);
    }
}