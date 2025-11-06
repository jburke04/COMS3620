package models;

import java.util.Calendar;

/**
 * A Guest can book rooms for specified dates and times with a specific room type in mind.
 * The Booking class contains the Guest that Booked the room, the Room being booked, the 
 * dates (start and end) of the Booking, the total cost of the Booking (without discounts),
 * and the status of the Booking itself.
 */
public class Booking {
	private static int nextConfirmationNumber = 1000;

	private int confirmationNumber;
	private Guest guest;
	private int bookingID;
	private Calendar startTime;
	private Calendar endTime;
	private Room room;
	private BookingStatus status;
	private double cost;

	/**
	 * Constructor for a Booking
	 * @param Guest Guest the Booking is reserved under
	 * @param int ID of the Booking transaction
	 * @param Calendar start time and day of the Booking
	 * @param Calendar end time and day of the Booking
	 * @param Room Room being booked
	 * @param BookingStatus status of the Booking
	 * @param double cost of the whole Booking
	 */
	public Booking(Guest guest, int bookingID, Calendar startTime, Calendar endTime, Room room, BookingStatus status, double cost) {
		this.confirmationNumber = nextConfirmationNumber++;
		this.guest = guest;
		this.bookingID = bookingID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		this.status = status;
		this.cost = cost;
	}

	public int getConfirmationNumber() { return confirmationNumber; }

	/**
	 * Returns the Guest for this Booking
	 * @return Guest the Booking is under
	 */
	public Guest getGuest() {
		return this.guest;
	}

	/**
	 * Returns the ID for this Booking
	 * @return ID of the Booking
	 */
	public int getBookingID() {
		return this.bookingID;
	}

	/**
	 * Returns the current status for this Booking
	 * @return Booking's current Status
	 */
	public BookingStatus getStatus() {
		return this.status;
	}

	/**
	 * Returns the Room within this Booking
	 * @return Room being Booked
	 */
	public Room getRoom() {
		return this.room;
	}

	public Calendar getStartDate() { return startDate; }

	public Calendar getEndDate() { return endDate; }

	public double getCost() { return cost; }

	/**
	 * Sets the Booking's current status to the desired value
	 * @param status enumerated value representing the status to set this Booking to
	 */
	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	/**
	 * Sets the Booking's current cost to the desired value
	 * @param int cost to set
	 */
	public void setCost(int cost) { 
		this.cost = cost;
	}
}
