package src.models;

import java.util.Calendar;

public class Booking {
	private static int nextConfirmationNumber = 1000;

	private int confirmationNumber;
	private int guestID;
	private Calendar startTime;
	private Calendar endTime;
	private int roomNumber;
	private BookingStatus status;
	private double cost;

	public Booking(int confirmationNumber, int guestID, Calendar startTime, Calendar endTime,
				   int roomNumber, BookingStatus status, double cost) {
		this.confirmationNumber = confirmationNumber;
		this.guestID = guestID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomNumber = roomNumber;
		this.status = status;
		this.cost = cost;

		if (confirmationNumber >= nextConfirmationNumber) {
			nextConfirmationNumber = confirmationNumber + 1;
		}
	}

	public Booking(int guestID, Calendar startTime, Calendar endTime,
				   int roomNumber, BookingStatus status, double cost) {
		this(nextConfirmationNumber++, guestID, startTime, endTime, roomNumber, status, cost);
	}

	public int getConfirmationNumber() { return this.confirmationNumber; }
	public int getGuestID() { return this.guestID; }
	public Calendar getStartTime() { return this.startTime; }
	public Calendar getEndTime() { return this.endTime; }
	public int getRoomNumber() { return this.roomNumber; }
	public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }
	public BookingStatus getStatus() { return this.status; }
	public void setStatus(BookingStatus status) { this.status = status; }
	public double getCost() { return this.cost; }
}

