package src.models;

/**
 * Room that exist within this Hotel. This tracks the Room's number, the current Status of
 * the Room, the type of Room, and the cost per night.
 */
public class Room {
	private int roomNumber;
	private Status status;
	private RoomType roomType;
	private double cost;

	/**
	 * Constructor for a Room.
	 * @param roomNumber Room number of this Room.
	 * @param status Status for this Room.
	 * @param roomType RoomType for this Room.
	 * @param cost Cost per night for this Room.
	 */
	public Room(int roomNumber, Status status, RoomType roomType, double cost) {
		this.roomNumber = roomNumber;
		this.status = status;
		this.roomType = roomType;
		this.cost = cost;
	}

	// ----------- Getters ------------

	/**
	 * Gets the room number for this Room.
	 * @return Room number of the Room.
	 */
	public int getRoomNumber() { return this.roomNumber; }

	/**
	 * Gets the Status for this Room.
	 * @return Status of the Room.
	 */
	public Status getStatus() { return this.status; }

	/**
	 * Gets the RoomType for this Room.
	 * @return RoomType of the Room.
	 */
	public RoomType getRoomType() { return this.roomType; }

	/**
	 * Gets the Cost per night for this Room.
	 * @return Cost per night for the Room.
	 */
	public double getCost() { return this.cost; }

	// ----------- Setters ------------

	/**
	 * Sets the status for this Room.
	 * @param status Status to change to.
	 */
	public void setStatus(Status status) { this.status = status; }
}


