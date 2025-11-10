package src.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Guests will interact with the Hotel's Booking System in order to make
 * reservations, put in requests, change reservations, etc.
 */
public class Guest {
	private static int nextId = 1;

	int guestId;
	String name;
	String phoneNumber;
	String email;
	String idDocument;
	List<Booking> bookingHistory;

	/**
	 * Constructor for a new Guest
	 * @param name Guest name
	 * @param phoneNumber Phone Number
	 * @param email Guest email
	 * @param idDocument ID Documentation for Guest
	 */
	public Guest(String name, String phoneNumber, String email, String idDocument) {
		this.guestId = nextId++;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.idDocument = idDocument;
		this.bookingHistory = new ArrayList<>();
	}

		/**
	 * Constructor for a Guest
	 * @param String Guest name
	 * @param String Phone Number
	 * @param String Guest email
	 * @param String ID Documentation for Guest
	 */
	public Guest(String name, String phoneNumber, String email, String idDocument, ArrayList<Booking> bookingHistory) {
		this.guestId = nextId++;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.idDocument = idDocument;
		this.bookingHistory = bookingHistory;
	}

	/**
	 * Returns the name of this Guest
	 * @return String of the Guest's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the ID for this Guest
	 * @return Guest's ID
	 */
	public int getGuestID() {
		return this.guestId;
	}

	/**
	 * Returns the phone number for this Guest
	 * @return Guest's phone number
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	/**
	 * Returns the email for this Guest
	 * @return Guest's email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Returns a list of the Booking history for this Guest
	 * @return Guest's Booking history
	 */
	public List<Booking> getBookingHistory() {
		return this.bookingHistory;
	}

	/**
	 * Updates Guest's phone number
	 * @param String phone number to update to
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Updates Guest's email
	 * @param String email to update to
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Upon creating a booking, adds the booking to the Guest's
	 * Booking history list
	 * @param Booking booking to append
	 */
	public void addBooking(Booking booking) {
		this.bookingHistory.add(booking);
	}
}
