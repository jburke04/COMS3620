package models;

/**
 * Guests will interact with the Hotel's Booking System in order to make
 * reservations, put in requests, change reservations, etc.
 */
public class Guest {
	private final String name;
	private final int guestID;

	/**
	 * Constructor for a Guest
	 * @param String Guest name
	 * @param int Guest ID
	 */
	public Guest(String name, int guestID) {
		this.name = name;
		this.guestID = guestID;
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
}
