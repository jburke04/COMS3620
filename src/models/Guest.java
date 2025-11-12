package src.models;

/**
 * 
 */
public class Guest {
	private int guestId;
	private String name;
	private String phoneNumber;
	private String email;

	public Guest(int guestId, String name, String phoneNumber, String email) {
		this.guestId = guestId;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public int getGuestId() { return guestId; }
	public String getName() { return name; }
	public String getPhoneNumber() { return phoneNumber; }
	public String getEmail() { return email; }

	@Override public String toString() {
		return name + " (ID: " + guestId + ", phone: " + phoneNumber + ")";
	}
}

