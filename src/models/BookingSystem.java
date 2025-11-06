package models;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * System that the Hotel utilizes for managing Bookings. The Booking System itself
 * provides services for creating, cancelling, checking-in, checking-out, and paying
 * for a Booking.
 */
public class BookingSystem {
	private final RoomUtils roomUtils;
	private Room[] rooms;
	
	/**
	 * Constructor for BookingSystem that takes two filepaths and uses them to populate
	 * the list of rooms and list of bookings that the Room Utility class will support
	 * complex logic for.
	 * @param String filepath for the list of rooms
	 * @param String filepath for the list of bookings
	 */
	public BookingSystem(String f1, String f2) {
		this.roomUtils = new RoomUtils(f1, f2);
	}

	/**
	 * Constructor for BookingSystem that takes already defined lists for the rooms and
	 * bookings and provides them to the Room Utility class.
	 * @param ArrayList list of rooms
	 * @param ArrayList list of bookings
	 */
	public BookingSystem(ArrayList<Room> roomsList, ArrayList<Booking> bookingsList) {
		this.roomUtils = new RoomUtils(roomsList, bookingsList);
	}

	public BookingSystem(int numRooms) {
		this.rooms = new Room[numRooms];
	}
	
	public boolean book(RoomType type, Calendar startDate, Calendar endDate) {
		
		return false;
	}
	
	public boolean book(int roomNumber, Calendar startDate, Calendar endDate ) {
		
		return false;
	}
	
	/**
	 * A Guest wants to cancel a specified Booking. The Hotel BookingSystem needs to check the following:
	 * 	- if the Booking exists
	 * 	- if the request has been made before the cancellation window has passed
	 * If either condition is violated, then this function will return an error code respective of the
	 * violated condition, and the user interface will provide a corresponding message.
	 * @param booking desired Booking to cancel 
	 * @return 0 if the cancellation was successful, 1 if the booking doesn't exist, and 2 if the cancellation is not within the cancellation window
	 */
	public int cancel(Booking booking) {
		// check if the Booking exists
		if (!roomUtils.isExisting(booking))
			return 1;

		// check if the cancellation request is being made within the 24-hr window

		return 0;
	}
	
	public boolean update(Booking oldBooking, Booking newBooking) {
		

		return false;
	}
	
	public boolean checkIn(Booking booking) {
		

		return false;
	}
	
	public boolean checkOut(Booking booking) {
		

		return false;
	}
	
	public boolean clean(int roomNumber) {
		
		return false;
	}
	
	public boolean inspect(int roomNumber) {
		
		return false;
	}

	// to book we need to see if rooms are available
	public List<Room> findAvailableRooms(RoomType type, Calendar startDate, Calendar endDate) {

		return null;
	}

	public boolean isRoomAvailable(int roomNumber, Calendar startDate, Calendar endDate){
		return false;
	}

	// Check in a guest
	public boolean checkIn(int confirmationNumber) {
		return false;
	}

	// if confirmation number is not avilable
	public boolean checkIn(Booking booking) {
		return false;
	}

	// Find booking by confirmation number
	public Booking findBookingByConfirmation(int confirmationNumber) {
		return null;
	}

	public List<Guest> searchGuestByName(String name) {
		return null;
	}

	// Search guest by phone
	public Guest searchGuestByPhone(String phone) {
		return null;
	}

	public Guest addGuest(String name, String phone, String email, String idDocument) {
		return null;
	}

	private Room getRoomByNumber(int roomNumber) {
		return null;
	}

	// Getters
	public Room[] getRooms() { return this.rooms; }
	public List<Booking> getBookings() { return this.bookings; }
	public List<Guest> getGuests() { return this.guests; }
}
