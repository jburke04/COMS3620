package models;

/**
 * Guests that exist under the Hotel's Booking System. This keeps track of the Guest's unique 
 * ID, their Name, the Phone number associated with their account, and their email.
 */
public class Guest {
	private static int id = 1;

	private final int guestId;
	private String name;
	private String phoneNumber;
	private String email;
	private String licensePlate;

	/**
	 * Constructor for an existing Guest.
	 * @param guestId ID for this Guest.
	 * @param name Name for this Guest.
	 * @param phoneNumber Phone number for this Guest.
	 * @param email Email for this Guest.
	 */
	public Guest(int guestId, String name, String phoneNumber, String email) {
		this.guestId = guestId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;

		if (guestId >= Guest.id)
			Guest.id = guestId + 1;
	}

	/**
	 * Constructor for a new Guest.
	 * @param name Name for this Guest.
	 * @param phoneNumber Phone number for this Guest.
	 * @param email Email for this Guest.
	 */
	public Guest(String name, String phoneNumber, String email) {
		this.guestId = Guest.id++;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	// -------------- Getters ----------------

	/**
	 * Gets this Guest's ID.
	 * @return Guest's ID.
	 */
	public int getGuestId() { return this.guestId; }

	/**
	 * Gets this Guest's name.
	 * @return Guest's name.
	 */
	public String getName() { return this.name; }

	/**
	 * Gets this Guest's phone number.
	 * @return Guest's phone number.
	 */
	public String getPhoneNumber() { return this.phoneNumber; }

	/**
	 * Gets this Guest's email.
	 * @return Guest's email.
	 */
	public String getEmail() { return this.email; }
	public String getLicensePlate() {
		return licensePlate;
	}

	// ---------------- Setters ----------------

	/**
	 * Sets the Guest's phone number. USE ONLY FROM HOTELSYSTEM changeGuestInfo() METHOD PLEASE!
	 * @param phoneNumber Phone number to change to.
	 */
	public void setPhonenumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	/**
	 * Sets the Guest's email. USE ONLY FROM HOTELSYSTEM changeGuestInfo() METHOD PLEASE!
	 * @param email Email to change to.
	 */
	public void setEmail(String email) { this.email = email; }

    /**
     * Sets the Guest's name. People have changed their names before. USE ONLY FROM HOTELSYSTEM changeGuestInfo() METHOD PLEASE!
     * @param name Name to change to.
     */
    public void setName(String name) { this.name = name; }


	public void setLicensePlate(String plate) {
		this.licensePlate = plate;
	}


	/**
	 * Stringifies this Guest object.
	 */
	@Override public String toString() {
		return this.name + ", #" + this.guestId + ", Phone: " + this.phoneNumber + ", Email:" + this.email;
	}

}

