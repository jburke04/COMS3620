package models;

import helpers.Parser;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hotel's Booking System that contains the list of Rooms that exist within the Hotel,
 * the list of Bookings made under that Hotel, the list of Guests under that Hotel, and
 * the list of Maintenance Tickets that exist for all Rooms.
 */
public class BookingSystem implements SubSystem {

	private final List<Booking> bookings = new ArrayList<>();

	private final String bookingsPath = "src/assets/Bookings.json";

	private int nextTicketId = 1;

	/**
	 * Loads booking data from JSON file of bookings
	 */
	@Override
	public void load() {
		Parser.parseBookings(this.bookingsPath, this.bookings);
	}

	/**
	 * Saves booking data into JSON file of bookings
	 */
	@Override
	public void save() {
		Parser.saveBookings(this.bookingsPath, this.bookings);
	}

	/**
	 * Gets the list of Bookings for this Booking System.
	 * @return list of Bookings that were created under this Booking System.
	 */
	public List<Booking> getBookings() { return this.bookings; }

	/**
	 * Takes a room number provided by a user and searches through the Hotel's
	 * Booking System to see if that room number exists.
	 * @param roomNumber Room number to search for.
	 * @return Room with corresponding room number, or null if no such Room
	 * 			exists in the Hotel.
	 */
	public Room findRoomByNumber(int roomNumber) {
		// iterate through the list of Rooms:
		for (Room r : this.rooms) if (r.getRoomNumber() == roomNumber) return r;

		// no corresponding room found, return null:
		return null;
	}

	/**
	 * Takes a confirmation number provided by a user and searches through the Hotel's
	 * Booking System to see if that confirmation number exists.
	 * @param conf Confirmation number to search for.
	 * @return Booking with corresponding confirmation number, or null if no such
	 * 			Booking exists within the Booking System.
	 */
	public Booking findBookingByConfirmation(int conf) {
		// iterate through the list of Bookings:
		for (Booking b : this.bookings) if (b.getConfirmationNumber() == conf) return b;

		// no corresponding booking found, return null:
		return null;
	}

	/**
	 * Iterates through the Hotel's list of Rooms to find Rooms that match the criteria
	 * provided by the user.
	 * @param type RoomType to search for.
	 * @param start Start time that the Room must be available for.
	 * @param end End time that the Room must be available for.
	 * @return List of Rooms that match the desired RoomType and are available during
	 * 			the period defined by the start and end times provided.
	 */
	public List<Room> findAvailableRooms(RoomType type, Calendar start, Calendar end) {
		return rooms.stream()
				.filter(r -> r.getRoomType() == type)
				.filter(r -> isRoomAvailable(r, start, end))
				.collect(Collectors.toList());
	}

	/**
	 * Checks whether a Room is available within the defined window.
	 * @param room Room to check for availability.
	 * @param start Start time that the Room must be available for.
	 * @param end End time that the Room must be available for.
	 * @return True if the Room is available at that time, False if the Room isn't.
	 */
	public boolean isRoomAvailable(Room room, Calendar start, Calendar end) {		
		// check if the Room is not currently AVAILABLE:
		if (room.getStatus() != Status.AVAILABLE) return false;

		// Check if any Bookings with this Room conflict with the defined window:
		for (Booking b : this.bookings) {
			// make sure room number corresponds to the desired Room's room number:
			if (b.getRoomNumber() != room.getRoomNumber()) continue;

			// don't check with CANCELLED or COMPLETED Bookings:
			if (b.getStatus() == BookingStatus.CANCELLED || b.getStatus() == BookingStatus.COMPLETED) continue;

			// compare requested window to the window of the existing Booking:
			if (start.before(b.getEndTime()) && b.getStartTime().after(end)) {
				return false;
			}
		}

		// no conflicting Bookings, return true:
		return true;
	}

	/**
	 * Creates a Booking under a Guest with the desired Room and timeframe.
	 * @param guest Guest to put the Booking under.
	 * @param room Room being booked.
	 * @param start Start time of the Booking.
	 * @param end End time of the Booking.
	 * @return Newly created Booking object, null if no such Booking could be created.
	 */
	public Booking createBooking(Guest guest, Room room, Calendar start, Calendar end) {
		//TODO: need to verify if the booking can be created.
		long nights = Math.max(1, (end.getTimeInMillis() - start.getTimeInMillis()) / (24L*60L*60L*1000L));
		double cost = nights * room.getCost();
		Booking booking = new Booking(guest.getGuestId(), start, end, room.getRoomNumber(),
				BookingStatus.CONFIRMED, cost);
		bookings.add(booking);
		room.setStatus(Status.AWAITING);
		this.save();
		return booking;
	}

	/**
	 * Cancels a Booking that corresponds with the provided confirmation number.
	 * @param confirmationNumber confirmation number to cancel.
	 * @return false if the Booking was not cancelled, true if the Booking was.
	 */
	public boolean cancelBooking(int confirmationNumber, RoomUtils utils) {
		// search for Booking:
		Booking b = findBookingByConfirmation(confirmationNumber);
		// no corresponding Booking found, return false:
		if (b == null) return false;
		// Booking was already CANCELLED or COMPLETED, return false:
		if (b.getStatus() == BookingStatus.CANCELLED || b.getStatus() == BookingStatus.COMPLETED) return false;

		// update Booking status and set Room as AVAILABLE:
		b.setStatus(BookingStatus.CANCELLED);
		Room r = utils.findRoom(b.getRoomNumber());
		if (r != null && r.getStatus() == Status.AWAITING) {
			r.setStatus(Status.AVAILABLE);
		}
        this.save();
		return true;
	}

	/**
	 * Changes the room number under an existing Booking.
	 * @param confirmationNumber Booking to modify.
	 * @param newRoomNumber Room number to change to.
	 * @return True if the operation was successful, false if it failed.
	 */
	public boolean changeRoom(int confirmationNumber, int newRoomNumber, RoomUtils utils) {
		//TODO: need to calculate the new cost of the Booking after changing the room.

		Booking b = findBookingByConfirmation(confirmationNumber);
		if (b == null) return false;

		Room current = utils.findRoom(b.getRoomNumber());
		Room target  = utils.findRoom(newRoomNumber);
		if (target == null) return false;

		if (!isRoomAvailable(target, b.getStartTime(), b.getEndTime())) return false;

		// free previous room if it was just booked (not occupied)
		if (current != null && current.getStatus() == Status.BOOKED) {
			current.setStatus(Status.AVAILABLE);
		}
		// assign new room
		b.setRoomNumber(newRoomNumber);
		if (b.getStatus() == BookingStatus.CHECKEDIN) {
			target.setStatus(Status.OCCUPIED);
		} else {
			target.setStatus(Status.BOOKED);
		}
		this.save();
		return true;
	}

	/**
	 * Checks In for a provided confirmation number.
	 * @param confirmationNumber Booking to Check In.
	 * @return True if the operation was successful, false if not.
	 */
	public boolean checkIn(int confirmationNumber) {
		Booking b = findBookingByConfirmation(confirmationNumber);
		if (b == null) return false;
		if (b.getStatus() != BookingStatus.CONFIRMED) return false;

		Room r = findRoomByNumber(b.getRoomNumber());
		if (r == null || r.getStatus() == Status.AWAITING) return false;

		b.setStatus(BookingStatus.CHECKEDIN);
		r.setStatus(Status.OCCUPIED);
		this.save();
		return true;
	}

	/**
	 * Validates whether the Booking exists and is CHECKEDIN.
	 * @param confirmationNumber Booking to check.
	 * @return True if the Booking is not set to CHECKEDIN status, false if so.
	 */
    public boolean validateBooking(int confirmationNumber){
        Booking b = findBookingByConfirmation(confirmationNumber);
        if (b == null) return false;
        if (b.getStatus() != BookingStatus.CHECKEDIN) return false;
        return true;
    }

	/**
	 * Checks Out for the provided Booking and sets the Room to status
	 * NEEDS_CLEANING.
	 * @param confirmationNumber Booking to Check Out.
	 * @return True if the operation was successful, false if not.
	 */
	public boolean checkoutAndPay(int confirmationNumber) {
        Booking b = findBookingByConfirmation(confirmationNumber);
        if(validateBooking(confirmationNumber)){
            return false;
        }

		// simulate external payment success (method is not validated here)
		Room r = findRoomByNumber(b.getRoomNumber());
		b.setStatus(BookingStatus.COMPLETED);
		if (r != null) r.setStatus(Status.NEEDS_CLEANING);
		this.save();
		return true;
	}

	public boolean updateBooking(Booking b, Calendar start, Calendar end) {
		// check if the room is available during that time:
		if (isRoomAvailable(findRoomByNumber(b.getRoomNumber()), start, end))
			return false;

		// update the start and end times:
		b.setStartTime(start);
		b.setEndTime(end);

		this.save();
		return true;
	}

    /**
     * Returns a list of confirmed bookings that have the corresponding guest.
     * @param guest Guest to find bookings for.
     * @return List of active bookings of Guest
     */
    public List<Booking> getConfirmedBookingsByGuest(Guest guest) {
        List<Booking> out = new ArrayList<>();
        for (Booking b : this.bookings) {
            if (b.getGuestID() == guest.getGuestId() && b.getStatus() == BookingStatus.CONFIRMED) {
                out.add(b);
            }
        }
        return out;
    }

    public List<Booking> getConfirmedBookings() {
        List<Booking> out = new ArrayList<>();
        for (Booking b : this.bookings) {
            if (b.getStatus() == BookingStatus.CONFIRMED) {
                out.add(b);
            }
        }
        return out;
    }

    public List<Booking> getCheckedBookingsByGuest(Guest guest) {
        List<Booking> out = new ArrayList<>();
        for (Booking b : this.bookings) {
            if (b.getGuestID() == guest.getGuestId() && b.getStatus() == BookingStatus.CHECKEDIN) {
                out.add(b);
            }
        }
        return out;
    }

	// ---- maintenance ----
	public MaintenanceTicket createTicket(int roomNumber, String description, String severity) {
		MaintenanceTicket t = new MaintenanceTicket(nextTicketId++, findRoomByNumber(roomNumber), description, severity, MaintenanceStatus.OPEN);
		tickets.add(t);
		// if severe, mark room awaiting
		Room r = findRoomByNumber(roomNumber);
		if (r != null && "HIGH".equalsIgnoreCase(severity)) {
			r.setStatus(Status.AWAITING);
		}
		this.save();
		return t;
	}

	public boolean resolveTicket(int ticketId) {
		for (MaintenanceTicket t : tickets) {
			if (t.getTicketId() == ticketId) {
				t.setStatus(MaintenanceStatus.RESOLVED);
				// if room was awaiting, free or keep occupied depending on bookings
				Room r = t.getRoom();
				if (r != null && r.getStatus() == Status.AWAITING) {
					// if someone is checked-in on this room, it should be OCCUPIED, else AVAILABLE
					boolean occupied = bookings.stream()
							.anyMatch(b -> b.getRoomNumber() == r.getRoomNumber() && b.getStatus() == BookingStatus.CHECKEDIN);
					r.setStatus(occupied ? Status.OCCUPIED : Status.AVAILABLE);
				}
				this.save();
				return true;
			}
		}
		return false;
	}
	public List<Booking> getBookingsForGuest(Guest guest) {
		List<Booking> result = new ArrayList<>();
		for (Booking b : bookings) {
			if (b.getGuestID() == guest.getGuestId()) {
				result.add(b);
			}
		}
		return result;
	}

}
