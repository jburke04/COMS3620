package src.models;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * System that the Hotel utilizes for managing Bookings. The Booking System itself
 * provides services for creating, cancelling, checking-in, checking-out, and paying
 * for a Booking.
 */
public class BookingSystem {
	private RoomUtils roomUtils;
	
	public BookingSystem(String f1, String f2) {
		this.roomUtils = new RoomUtils(f1, f2);
	}

	public BookingSystem(ArrayList<Room> roomsList, ArrayList<Booking> bookingsList) {
		this.roomUtils = new RoomUtils(roomsList, bookingsList);
	}
	
	public boolean book(RoomType type, Calendar startDate, Calendar endDate) {
		
		return false;
	}
	
	public boolean book(int roomNumber, Calendar startDate, Calendar endDate ) {
		
		return false;
	}
	
	/**
	 * A Guest wants to cancel the Booking
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
}
