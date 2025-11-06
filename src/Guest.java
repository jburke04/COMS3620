package src;

import java.util.ArrayList;
import java.util.List;

public class Guest {
	private static int nextId = 1;

	int guestId;
	String name;
	String phoneNumber;
	String email;
	String idDocument;
	List<Booking> bookingHistory;

	public Guest(String name, String phoneNumber, String email, String idDocument) {
		this.guestId = nextId++;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.idDocument = idDocument;
		this.bookingHistory = new ArrayList<>();
	}
	// Geetrs and Setters
	public int getGuestId() { return guestId; }
	public String getName() { return name; }
	public String getPhoneNumber() { return phoneNumber; }
	public String getEmail() { return email; }
	public List<Booking> getBookingHistory() { return bookingHistory; }
}
