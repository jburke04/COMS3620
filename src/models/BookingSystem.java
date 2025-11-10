package src.models;

import src.helpers.Parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

///**
// * System that the Hotel utilizes for managing Bookings. The Booking System itself
// * provides services for creating, cancelling, checking-in, checking-out, and paying
// * for a Booking.
// */
//public class BookingSystem {
//	private RoomUtils roomUtils;
//	private List<Guest> guests;
//
//	/**
//	 * Constructor for BookingSystem that takes two filepaths and uses them to populate
//	 * the list of rooms and list of bookings that the Room Utility class will support
//	 * complex logic for.
//	 * @param f1 filepath for the list of rooms
//	 * @param f2 filepath for the list of bookings
//	 * @param f3 filepath for the list of guests
//	 */
//	// public BookingSystem(String f1, String f2, String f3) {
//    //     roomUtils = new RoomUtils();
//    //     Parser.parseRooms(f1, roomUtils.getRooms());
//    //     Parser.parseBookings(f2, roomUtils.getBookings());
//	// 	//TODO: iterate through the third file to fetch the list of guests
//    //     Parser.parseGuests(f3, guests);
//	// }
//
//	/**
//	 * Constructor for BookingSystem that takes already defined lists for the rooms and
//	 * bookings and provides them to the Room Utility class.
//	 * @param ArrayList list of rooms
//	 * @param ArrayList list of bookings
//	 */
//	public BookingSystem(ArrayList<Room> roomsList, ArrayList<Booking> bookingsList, List<Guest> guests) {
//		this.roomUtils = new RoomUtils(roomsList, bookingsList);
//		this.guests = guests;
//	}
//
//	public Booking book(RoomType type, Calendar startDate, Calendar endDate, Guest guest) {
//		//TODO: search for the next available room by room type and check if it's not booked during the requested dates
//		return null;
//	}
//
//	public Booking book(int roomNumber, Calendar startDate, Calendar endDate, Guest guest) {
//		Room room = roomUtils.findRoom(roomNumber);
//		if (room == null)
//			return null;
//
//		//TODO: calculate the full cost of the booking and add it as the total cost:
//		double totalCost = 0;
//		Booking b = new Booking(guest.getGuestID(), startDate, endDate, room, BookingStatus.BOOKED, totalCost);
//		return b;
//	}
//
//	/**
//	 * A Guest wants to cancel a specified Booking. The Hotel BookingSystem needs to check the following:
//	 * 	- if the Booking exists
//	 * 	- if the request has been made before the cancellation window has passed
//	 * If either condition is violated, then this function will return an error code respective of the
//	 * violated condition, and the user interface will provide a corresponding message.
//	 * @param Booking desired Booking to cancel
//	 * @return 0 if the cancellation was successful, 1 if the booking doesn't exist, and 2 if the cancellation is not within the cancellation window
//	 */
//	public int cancel(Booking booking) {
//		// check if the Booking exists
//		if (!roomUtils.isExisting(booking))
//			return 1;
//
//		// check if the cancellation request is being made within the 24-hr window
//
//		return 0;
//	}
//
//	public boolean update(Booking oldBooking, Booking newBooking) {
//
//
//		return false;
//	}
//
//	public boolean checkIn(Booking booking) {
//
//
//		return false;
//	}
//
//	public boolean checkOut(Booking booking) {
//
//
//		return false;
//	}
//
//	public boolean clean(Room room) {
//		room.setStatus(Status.CLEANING);
//		return false;
//	}
//
//	public boolean inspect(Room room) {
//		room.setStatus(Status.INSPECTING);
//		return false;
//	}
//
//	// to book we need to see if rooms are available
//	public List<Room> findAvailableRooms(RoomType type, Calendar startDate, Calendar endDate) {
//
//		return null;
//	}
//
//	public boolean isRoomAvailable(Room room, Calendar startDate, Calendar endDate){
//		return false;
//	}
//
//	/**
//	 * Checks in for a guest using the provided confirmation number.
//	 * @param int confirmation number to check in
//	 * @return true if the booking doesn't exist (error), false if the
//	 * 			check in was successful.
//	 */
//	public boolean checkIn(int confirmationNumber) {
//		Booking b = roomUtils.findBooking(confirmationNumber);
//		// no such booking exists, return true for error
//		if (b == null)
//			return true;
//
//		// booking exists, update statuses and return false for no error
//		b.setStatus(BookingStatus.CHECKEDIN);
//		b.getRoom().setStatus(Status.OCCUPIED);
//		return false;
//	}
//
//	// commented out bc of errors for now
//	// // if confirmation number is not avilable
//	// public boolean checkIn(Booking booking) {
//	// 	booking.setStatus(BookingStatus.CHECKEDIN);
//	// 	booking.getRoom().setStatus(Status.OCCUPIED);
//	// 	return false;
//	// }
//
//	/**
//	 * Change the room for the Booking.
//	 * @param Booking booking to update
//	 * @param Room room to change the booking to
//	 * @return true if there is an issue with the requested room,
//	 * 			false if the booking update was successful.
//	 */
//	public boolean change(Booking booking, Room newRoom) {
//		//TODO: first ensure that the requested room is not booked during that time
//
//		// not booked during that time, continue with the update
//		booking.setRoom(newRoom);
//		return false;
//	}
//
//	/**
//	 * Searches for the Booking by the confirmation number. Returns null if no such
//	 * confirmation number exists.
//	 * @param int confirmation number to search
//	 * @return Booking with corresponding confirmation number
//	 */
//	public Booking findBookingByConfirmation(int confirmationNumber) {
//		return roomUtils.findBooking(confirmationNumber);
//	}
//
//	/**
//	 * Search for a Guest by the provided name.
//	 * @param String name to search
//	 * @return list of guests with corresponding name
//	 */
//	public List<Guest> searchGuestByName(String name) {
//		return null;
//	}
//
//	/**
//	 * Search for a Guest by the provided phone number.
//	 * @param String phone number to search
//	 * @return list of guests with corresponding phone number
//	 */
//	public Guest searchGuestByPhone(String phone) {
//		return null;
//	}
//
//	/**
//	 * Adds a Guest to the current list of guests in this system.
//	 * @param String name of guest
//	 * @param String phone number of guest
//	 * @param String email of guest
//	 * @param String ID Document of guest
//     * @return guest being added
//	 */
//	public Guest addGuest(String name, String phone, String email, String idDocument) {
//        Guest guest = new Guest(name, phone, email, idDocument);
//
//        return guest;
//	}
//
//	private Room getRoomByNumber(int roomNumber) {
//		return roomUtils.findRoom(roomNumber);
//	}
//
//	public ArrayList<Room> getRooms() {
//		return this.roomUtils.getRooms();
//	}
//
//	public List<Booking> getBookings() {
//		return this.roomUtils.getBookings();
//	}
//
//	public List<Guest> getGuests() {
//		return this.guests;
//	}
//}
/**
 * Iteration 1
 */

import java.util.stream.Collectors;

/** In-memory store + file I/O for rooms, guests, bookings. */
public class BookingSystem {

	private final List<Room> rooms = new ArrayList<>();
	private final List<Guest> guests = new ArrayList<>();
	private final List<Booking> bookings = new ArrayList<>();

	private final String roomsPath = "src/assets/Rooms.json";
	private final String guestsPath = "src/assets/Guests.json";
	private final String bookingsPath = "src/assets/Bookings.json";

	public void loadAll() {
		Parser.parseRooms(roomsPath, rooms);
		Parser.parseGuests(guestsPath, guests);
		Parser.parseBookings(bookingsPath, bookings);
	}

	public void saveAll() {
		Parser.saveRooms(roomsPath, rooms);
		Parser.saveBookings(bookingsPath, bookings);
		// (Guests unchanged in Book-a-Room flow)
	}

	// ---- getters ----
	public List<Room> getRooms() { return rooms; }
	public List<Guest> getGuests() { return guests; }
	public List<Booking> getBookings() { return bookings; }

	// ---- queries ----
	public Guest findGuestByPhoneOrName(String phoneOrName) {
		for (Guest g : guests) {
			if (g.getPhoneNumber().equalsIgnoreCase(phoneOrName)
					|| g.getName().equalsIgnoreCase(phoneOrName)) {
				return g;
			}
		}
		return null;
	}

	public Room findRoomByNumber(int roomNumber) {
		for (Room r : rooms) if (r.getRoomNumber() == roomNumber) return r;
		return null;
	}

	public List<Room> findAvailableRooms(RoomType type, Calendar start, Calendar end) {
		return rooms.stream()
				.filter(r -> r.getRoomType() == type)
				.filter(r -> isRoomAvailable(r, start, end))
				.collect(Collectors.toList());
	}

	public boolean isRoomAvailable(Room room, Calendar start, Calendar end) {
		if (room.getStatus() != Status.AVAILABLE) return false;

		for (Booking b : bookings) {
			if (b.getRoomNumber() != room.getRoomNumber()) continue;
			if (b.getStatus() == BookingStatus.CANCELLED) continue;

			// date overlap check: (start < b.end) && (b.start < end)
			if (start.before(b.getEndTime()) && b.getStartTime().before(end)) {
				return false;
			}
		}
		return true;
	}

	// ---- command ----
	public Booking createBooking(Guest guest, Room room, Calendar start, Calendar end) {
		long nights = Math.max(1, (end.getTimeInMillis() - start.getTimeInMillis()) / (24L*60L*60L*1000L));
		double cost = nights * room.getCost();
		Booking booking = new Booking(guest.getGuestId(), start, end, room.getRoomNumber(),
				BookingStatus.CONFIRMED, cost);
		bookings.add(booking);
		room.setStatus(Status.BOOKED);
		saveAll();
		return booking;
	}
}
