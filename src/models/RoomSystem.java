package models;

import helpers.Parser;
import java.util.*;
import java.util.stream.Collectors;

public class RoomSystem {
    private final List<Room> rooms = new ArrayList<>();
    private final String roomPath = "src/assets/Rooms.json";

    public RoomSystem() {
        Parser.parseRooms(roomPath, rooms);
    }

    /**
     * Saves the room list to Rooms.json
     */
    public void saveRooms() {
        Parser.saveRooms(roomPath, rooms);
    }

    /**
     * Takes a room number provided by a user and searches through the Hotel's
     * Booking System to see if that room number exists.
     * @param roomNumber Room number to search for.
     * @return Room with corresponding room number, or null if no such Room
     * 			exists in the Hotel.
     */
    public Room findRoomByNumber(int roomNumber) {
        for (Room r : rooms) {
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }

        return null;
    }

    /**
     * Iterates through the Hotel's list of Rooms to find Rooms that match the criteria
     * provided by the user.
     * @param type RoomType to search for.
     * @param startDate Start time that the Room must be available for.
     * @param endDate End time that the Room must be available for.
     * @return List of Rooms that match the desired RoomType and are available during
     * 			the period defined by the start and end times provided.
     */
    public List<Room> findAvailableRooms(RoomType type, List<Booking> bookings, Calendar startDate, Calendar endDate) {
        return rooms.stream()
                .filter(r -> r.getRoomType() == type)
                .filter(r -> isRoomAvailable(r, bookings, startDate, endDate))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether a Room is available within the defined window.
     * @param room Room to check for availability.
     * @param bookings A list of bookings
     * @param startDate Start time that the Room must be available for.
     * @param endDate End time that the Room must be available for.
     * @return True if the Room is available at that time, False if the Room isn't.
     */
    public boolean isRoomAvailable(Room room, List<Booking> bookings, Calendar startDate, Calendar endDate) {
        // check if the Room is not currently AVAILABLE:
        if (room.getStatus() != Status.AVAILABLE) return false;

        // Check if any Bookings with this Room conflict with the defined window:
        for (Booking b : bookings) {
            // make sure room number corresponds to the desired Room's room number:
            if (b.getRoomNumber() != room.getRoomNumber()) continue;

            // don't check with CANCELLED or COMPLETED Bookings:
            if (b.getStatus() == BookingStatus.CANCELLED || b.getStatus() == BookingStatus.COMPLETED) continue;

            // compare requested window to the window of the existing Booking:
            if (startDate.before(b.getEndTime()) && b.getStartTime().after(endDate)) {
                return false;
            }
        }

        // no conflicting Bookings, return true:
        return true;
    }

    /**
     * Whether a room is checked-in or not.
     * @param room The room to check.
     * @param bookings The list of all bookings.
     * @return True if room is checked-in, false if not.
     */
    public boolean isRoomCheckedIn(Room room, List<Booking> bookings) {
        for (Booking b : bookings) {
            if (b.getRoomNumber() != room.getRoomNumber()) {
                continue;
            }
            if (b.getStatus() == BookingStatus.CHECKEDIN) {
                return true;
            }
        }
        return false; //Room is presumably available
    }
}