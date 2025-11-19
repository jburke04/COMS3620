package models;

import helpers.Parser;
import java.util.*;
import java.util.stream.Collectors;

public class RoomSystem implements SubSystem {
    private final List<Room> rooms = new ArrayList<>();
    private final String roomPath = "src/assets/Rooms.json";

    @Override
    public void load() {
        Parser.parseRooms(this.roomPath, this.rooms);
    }

    @Override
    public void save() {
        Parser.saveRooms(this.roomPath, this.rooms);
    }

    /**
     * Takes a room number provided by a user and searches through the Hotel's
     * Booking System to see if that room number exists.
     * @param roomNumber Room number to search for.
     * @return Room with corresponding room number, or null if no such Room
     * 			exists in the Hotel.
     */
    public Room findRoomByNumber(int roomNumber) {
        // search for corresponding room:
        for (Room r : this.rooms) {
            // match found:
            if (r.getRoomNumber() == roomNumber) {
                return r;
            }
        }

        // no room found, return null:
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
     * Gets the list of Rooms for this Room System
     * @return list of Rooms
     */
    public List<Room> getRooms() {
        return this.rooms;
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
        if (room.getStatus() != Status.AVAILABLE && room.getStatus() != Status.AWAITING) return false;

        // Check if any Bookings with this Room conflict with the defined window:
        for (Booking b : bookings) {
            // make sure room number corresponds to the desired Room's room number:
            if (b.getRoom() != room) continue;

            // don't check with CANCELLED or COMPLETED Bookings:
            if (b.getStatus() == BookingStatus.CANCELLED || b.getStatus() == BookingStatus.COMPLETED) continue;

            // compare requested window to the window of the existing Booking:
            if (during(startDate, b.getStartTime(), b.getEndTime())) return false; //If the start of this booking falls inside another
            if (during(endDate, b.getStartTime(), b.getEndTime())) return false; //If the end falls inside another

            if (during(b.getStartTime(), startDate, endDate)) return false; //If the current booking falls inside wanted start-end
            if (during(b.getEndTime(), startDate, endDate)) return false; //same here

            if (startDate.equals(b.getStartTime())) return false; //two bookings cannot start the same day for the same room.
            if (endDate.equals(b.getEndTime())) return false; //same with endings.
        }

        // no conflicting Bookings, return true:
        return true;
    }

    /**
     * helper for the function above.
     * @param toCheck date to check if it falls during range start-end
     * @param start start of range
     * @param end end of range
     * @return whether the checked date is during the range.
     */
    private boolean during(Calendar toCheck, Calendar start, Calendar end) {
        return toCheck.after(start) && toCheck.before(end);
    }

    /**
     * Whether a room is checked-in or not.
     * @param room The room to check.
     * @param bookings The list of all bookings.
     * @return True if room is checked-in, false if not.
     */
    public boolean isRoomCheckedIn(Room room, List<Booking> bookings) {
        for (Booking b : bookings) {
            if (b.getRoom() != room) {
                continue;
            }
            if (b.getStatus() == BookingStatus.CHECKEDIN) {
                return true;
            }
        }
        return false; //Room is presumably available
    }

    public int setAvailable(Room room) {
        if (room != null && room.getStatus() == Status.AWAITING)
			room.setStatus(Status.AVAILABLE);
        this.save();
        return 0;
    }

    public int setOccupied(Room room) {
        if (room != null)
            room.setStatus(Status.OCCUPIED);
        this.save();
        return 0;
    }

    public int setCleaning(Room room) {
        if (room != null)
            room.setStatus(Status.NEEDS_CLEANING);
        this.save();
        return 0;
    }
}