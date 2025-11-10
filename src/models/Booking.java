package src.models;

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
	private int guestID;
	private Calendar startTime;
	private Calendar endTime;
	private Room room;
	private BookingStatus status;
	private double cost;

	/**
	 * Constructor for a Booking
	 * @param guestID Guest ID the Booking is reserved under
	 * @param startTime start time and day of the Booking
	 * @param endTime end time and day of the Booking
	 * @param room Room being booked
	 * @param status status of the Booking
	 * @param cost cost of the whole Booking
	 */
	public Booking(int guestID, Calendar startTime, Calendar endTime, Room room, BookingStatus status, double cost) {
		this.confirmationNumber = nextConfirmationNumber++;
		this.guestID = guestID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		this.status = status;
		this.cost = cost;
	}

	/**
	 * Returns the confirmation number for this Booking
	 * @return confirmation number (unique identifier) for the Booking
	 */
	public int getConfirmationNumber() {
		return this.confirmationNumber;
	}

	/**
	 * Returns the Guest for this Booking
	 * @return Guest the Booking is under
	 */
	public int getGuestID() {
		return this.guestID;
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

	/**
	 * Returns the start date and time of the reservation
	 * @return date and time the reservation starts
	 */
	public Calendar getStartTime() { 
		return this.startTime;
	}

	/**
	 * Returns the end date and time of the reservation
	 * @return date and time the reservation ends
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * Returns the full estimated cost (without discounts) for the reservation
	 * @return full cost of the reservation
	 */
	public double getCost() {
		return this.cost;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Sets the Booking's current status to the desired value
	 * @param status enumerated value representing the status to set this Booking to
	 */
	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * Sets the Booking's current cost to the desired value
	 * @param cost cost to set
	 */
	public void setCost(int cost) { 
		this.cost = cost;
	}
}
