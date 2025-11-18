package src.models;

import java.util.*;
import java.util.stream.Collectors;
import src.helpers.Parser;

/**
 * Hotel's Booking System that contains the list of Rooms that exist within the Hotel,
 * the list of Bookings made under that Hotel, the list of Guests under that Hotel, and
 * the list of Maintenance Tickets that exist for all Rooms.
 */
public class BookingSystem {

	private final List<Room> rooms = new ArrayList<>();
	private final List<Guest> guests = new ArrayList<>();
	private final List<Booking> bookings = new ArrayList<>();
	private final List<MaintenanceTicket> tickets = new ArrayList<>();

	private final String roomsPath = "src/assets/Rooms.json";
	private final String guestsPath = "src/assets/Guests.json";
	private final String bookingsPath = "src/assets/Bookings.json";
	private final String ticketsPath = "src/assets/MaintenanceTickets.json";

	private int nextTicketId = 1;

	/**
	 * Loads all JSON data into each array list.
	 */
	public void loadAll() {
		Parser.parseRooms(roomsPath, rooms);
		Parser.parseGuests(guestsPath, guests);
		Parser.parseBookings(bookingsPath, bookings);
		Parser.parseTickets(ticketsPath, tickets);
		nextTicketId = tickets.stream().mapToInt(MaintenanceTicket::getTicketId).max().orElse(0) + 1;
	}

	/**
	 * Saves all JSON data to their respective JSON files.
	 */
	public void saveAll() {
		Parser.saveRooms(roomsPath, rooms);
		Parser.saveBookings(bookingsPath, bookings);
		Parser.saveTickets(ticketsPath, tickets);
	}

	/**
	 * Gets the list of Rooms for this Booking System.
	 * @return list of Rooms that exist in the Hotel.
	 */
	public List<Room> getRooms() { return this.rooms; }

	/**
	 * Gets the list of Guests for this Booking System.
	 * @return list of Guests that are registered in the Booking System.
	 */
	public List<Guest> getGuests() { return this.guests; }

	/**
	 * Gets the list of Bookings for this Booking System.
	 * @return list of Bookings that were created under this Booking System.
	 */
	public List<Booking> getBookings() { return this.bookings; }

	/**
	 * Gets the list of Maintenance Tickets for this Booking System.
	 * @return list of Maintenance Tickets created under this Booking System.
	 */
	public List<MaintenanceTicket> getTickets() { return this.tickets; }

	/**
	 * Takes a phone number or name of a Guest expected to be in the Hotel's
	 * Booking System and searches for their information.
	 * @param phoneOrName Phone number or name of Guest to search for.
	 * @return Guest object with matching phone number or name, or null if
	 * 			no such Guest exists.
	 */
	public Guest findGuestByPhoneOrName(String phoneOrName) {
		// iterate through the list of Guests:
		for (Guest g : this.guests) {
			// Guest name or phone number matches search query:
			if (g.getPhoneNumber().equalsIgnoreCase(phoneOrName)
					|| g.getName().equalsIgnoreCase(phoneOrName)) {
				return g;
			}
		}

		// no matching Guest found, return null:
		return null;
	}

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
	 * Checks whether or not a Room is available within the defined window.
	 * @param room Room to check for availability.
	 * @param start Start time that the Room must be available for.
	 * @param end End time that the Room must be available for.
	 * @return True if the Room is available at that time, False if the Room isn't.
	 */
	public boolean isRoomAvailable(Room room, Calendar start, Calendar end) {
		//TODO: check if the Room is available within a window of time, currently only checking if it has the AVAILABLE status
		
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
		room.setStatus(Status.BOOKED);
		saveAll();
		return booking;
	}

	/**
	 * Cancels a Booking that corresponds with the provided confirmation number.
	 * @param confirmatioNumber confirmation number to cancel.
	 * @return false if the Booking was not cancelled, true if the Booking was.
	 */
	public boolean cancelBooking(int confirmationNumber) {
		// search for Booking:
		Booking b = findBookingByConfirmation(confirmationNumber);
		// no corresponding Booking found, return false:
		if (b == null) return false;
		// Booking was already CANCELLED or COMPLETED, return false:
		if (b.getStatus() == BookingStatus.CANCELLED || b.getStatus() == BookingStatus.COMPLETED) return false;

		// update Booking status and set Room as AVAILABLE:
		b.setStatus(BookingStatus.CANCELLED);
		Room r = findRoomByNumber(b.getRoomNumber());
		if (r != null && r.getStatus() == Status.BOOKED) {
			r.setStatus(Status.AVAILABLE);
		}
		saveAll();
		return true;
	}

	/**
	 * Changes the room number under an existing Booking.
	 * @param confirmationNumber Booking to modify.
	 * @param newRoomNumber Room number to change to.
	 * @return True if the operation was successful, false if it failed.
	 */
	public boolean changeRoom(int confirmationNumber, int newRoomNumber) {
		//TODO: need to calculate the new cost of the Booking after changing the room.

		Booking b = findBookingByConfirmation(confirmationNumber);
		if (b == null) return false;

		Room current = findRoomByNumber(b.getRoomNumber());
		Room target  = findRoomByNumber(newRoomNumber);
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
		saveAll();
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
		saveAll();
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
		saveAll();
		return true;
	}

	// ---- maintenance ----
	public MaintenanceTicket createTicket(int roomNumber, String description, String severity) {
		MaintenanceTicket t = new MaintenanceTicket(nextTicketId++, roomNumber, description, severity, MaintenanceStatus.OPEN);
		tickets.add(t);
		// if severe, mark room awaiting
		Room r = findRoomByNumber(roomNumber);
		if (r != null && "HIGH".equalsIgnoreCase(severity)) {
			r.setStatus(Status.AWAITING);
		}
		saveAll();
		return t;
	}

	public boolean resolveTicket(int ticketId) {
		for (MaintenanceTicket t : tickets) {
			if (t.getTicketId() == ticketId) {
				t.setStatus(MaintenanceStatus.RESOLVED);
				// if room was awaiting, free or keep occupied depending on bookings
				Room r = findRoomByNumber(t.getRoomNumber());
				if (r != null && r.getStatus() == Status.AWAITING) {
					// if someone is checked-in on this room, it should be OCCUPIED, else AVAILABLE
					boolean occupied = bookings.stream()
							.anyMatch(b -> b.getRoomNumber() == r.getRoomNumber() && b.getStatus() == BookingStatus.CHECKEDIN);
					r.setStatus(occupied ? Status.OCCUPIED : Status.AVAILABLE);
				}
				saveAll();
				return true;
			}
		}
		return false;
	}
	public List<Booking> getBookingsForGuest(int guestId) {
		List<Booking> result = new ArrayList<>();
		for (Booking b : bookings) {
			if (b.getGuestID() == guestId) {
				result.add(b);
			}
		}
		return result;
	}

}
