package src;

import java.util.Calendar;

public class Booking {
	private static int nextConfirmationNumber = 1000;

	int confirmationNumber;
	Guest guest;
	Calendar startDate;
	Calendar endDate;
	Room room;
	Status status;
	int cost;

	public Booking(Guest guest, Room room, Calendar startDate, Calendar endDate, int cost) {
		this.confirmationNumber = nextConfirmationNumber++;
		this.guest = guest;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
		this.cost = cost;
		this.status = Status.BOOKED;
	}

	// Getters
	public int getConfirmationNumber() { return confirmationNumber; }
	public Guest getGuest() { return guest; }
	public Room getRoom() { return room; }
	public Calendar getStartDate() { return startDate; }
	public Calendar getEndDate() { return endDate; }
	public Status getStatus() { return status; }
	public int getCost() { return cost; }

	// Setters
	public void setStatus(Status status) { this.status = status; }
	public void setCost(int cost) { this.cost = cost; }

}
