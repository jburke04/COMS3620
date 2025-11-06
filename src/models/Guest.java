package models;

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
	 * Constructor for a Guest
	 * @param String Guest name
	 * @param String Phone Number
	 * @param String Guest email
	 * @param String ID Documentation for Guest
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
		return this.guestID;
	}

	public String getPhoneNumber() { return this.phoneNumber; }

	public String getEmail() { return this.email; }
	
	public List<Booking> getBookingHistory() { return this.bookingHistory; }
}
