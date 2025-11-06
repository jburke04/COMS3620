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
}
