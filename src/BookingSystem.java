package src;

import java.util.Calendar;

public class BookingSystem {
	private Room[] rooms;
	
	public BookingSystem(int numRooms) {
		rooms = new Room[numRooms];
	}
	
	public boolean book(RoomType type, Calendar startDate, Calendar endDate) {
		
	}
	
	public boolean book(int roomNumber, Calendar startDate, Calendar endDate ) {
		
	}
	
	public boolean cancel(Booking booking) {
		
	}
	
	public boolean update(Booking oldBooking, Booking newBooking) {
		
	}
	
	public boolean checkIn(Booking booking) {
		
	}
	
	public boolean checkOut(Booking booking) {
		
	}
	
	public boolean clean(int roomNumber) {
		
	}
	
	public boolean inspect(int roomNumber) {
		
	}

	// to book we need to see if rooms are available
	public List<Room> findAvailableRooms(RoomType type, Calendar startDate, Calendar endDate) {

	}

	public boolean isRoomAvailable(int roomNumber, Calendar startDate, Calendar endDate){

	}

	// Check in a guest
	public boolean checkIn(int confirmationNumber) {

	}

	// if confirmation number is not avilable
	public boolean checkIn(Booking booking) {

	}

	// Find booking by confirmation number
	public Booking findBookingByConfirmation(int confirmationNumber) {

	}

	public List<Guest> searchGuestByName(String name) {

	}

	// Search guest by phone
	public Guest searchGuestByPhone(String phone) {

	}

	public Guest addGuest(String name, String phone, String email, String idDocument) {

	}

	private Room getRoomByNumber(int roomNumber) {

	}

	// Getters
	public Room[] getRooms() { return rooms; }
	public List<Booking> getBookings() { return bookings; }
	public List<Guest> getGuests() { return guests; }


	}
