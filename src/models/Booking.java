package src.models;

import java.util.Calendar;

/**
 * Booking class used for tracking windows that Rooms are booked, the status of the Booking,
 * The Guest the Booking was made under, the Room booked, and the calculated total cost
 * of the Booking (without discounts).
 */
public class Booking {
	private static int nextConfirmationNumber = 1000;

	private int confirmationNumber;
	private int guestID;
	private Calendar startTime;
	private Calendar endTime;
	private int roomNumber;
	private BookingStatus status;
	private double cost;

	/**
	 * Constructor that creates a Booking with a set confirmation number.
	 * @param confirmationNumber Confirmation Number for this Booking.
	 * @param guestID Guest ID the Booking is under.
	 * @param startTime Start time of the Booking.
	 * @param endTime End time of the Booking.
	 * @param roomNumber Room number the Booking is for.
	 * @param BookingStatus Current status of the Booking.
	 * @param double Full calculated cost of the Booking.
	 */
	public Booking(int confirmationNumber, int guestID, Calendar startTime, Calendar endTime,
				   int roomNumber, BookingStatus status, double cost) {
		this.confirmationNumber = confirmationNumber;
		this.guestID = guestID;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomNumber = roomNumber;
		this.status = status;
		this.cost = cost;

		if (confirmationNumber >= Booking.nextConfirmationNumber) {
			Booking.nextConfirmationNumber = confirmationNumber + 1;
		}
	}

	/**
	 * Constructor that creates a new Booking object.
	 * @param guestID Guest ID the Booking is under.
	 * @param startTime Start time of the Booking.
	 * @param endTime End time of the Booking.
	 * @param roomNumber Room number the Booking is for.
	 * @param BookingStatus Current status of the Booking.
	 * @param double Full calculated cost of the Booking.
	 */
	public Booking(int guestID, Calendar startTime, Calendar endTime,
				   int roomNumber, BookingStatus status, double cost) {
		this(nextConfirmationNumber++, guestID, startTime, endTime, roomNumber, status, cost);
	}

	// -------------- Getters --------------

	/**
	 * Gets this Booking's confirmation number.
	 * @return confirmation number of this Booking.
	 */
	public int getConfirmationNumber() { return this.confirmationNumber; }

	/**
	 * Gets the Guest ID this Booking is under.
	 * @return Guest ID of the Booking.
	 */
	public int getGuestID() { return this.guestID; }

	/**
	 * Gets the start time for this Booking.
	 * @return Starting date and time for this Booking.
	 */
	public Calendar getStartTime() { return this.startTime; }

	/**
	 * Gets the end time for this Booking.
	 * @return Ending date and time for this Booking.
	 */
	public Calendar getEndTime() { return this.endTime; }

	/**
	 * Gets the room number this Booking was made for.
	 * @return Corresponding room number for this Booking.
	 */
	public int getRoomNumber() { return this.roomNumber; }

	/**
	 * Gets the BookingStatus for this Booking.
	 * @return Current BookingStatus for this Booking.
	 */
	public BookingStatus getStatus() { return this.status; }

	/**
	 * Gets the total cost for this Booking.
	 * @return Total cost of the Booking without discounts.
	 */
	public double getCost() { return this.cost; }

	// --------------- Setters -----------------

	/**
	 * Sets the room number for this Booking.
	 * @param roomNumber Room number to set to.
	 */
	public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

	/**
	 * Sets the BookingStatus for this Booking.
	 * @param status BookingStatus to set to.
	 */
	public void setStatus(BookingStatus status) { this.status = status; }
}

