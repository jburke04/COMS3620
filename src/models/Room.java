package models;

/**
 * 
 */
public class Room {
	public models.RoomDescription description;
	public int roomNumber;
	private models.Status status;
	
	public Room(models.RoomType type, int roomNumber) {
		if (type == models.RoomType.SINGLE) {
			description = new models.RoomDescription(type, 100);
		}
		else if (type == RoomType.DOUBLE) {
			description = new RoomDescription(type, 150);
		}
		else if (type == RoomType.KING) {
			description = new RoomDescription(type, 200);
		}
		else if (type == RoomType.PRESIDENTIAL) {
			description = new RoomDescription(type, 300);
		}
		
		this.roomNumber = roomNumber;
		status = Status.AVAILABLE;
	}
	
	public Room(RoomType type, int roomNumber, Status status) {
		this(type, roomNumber);
		this.status = status;
	}
	
	/**
	 * Gets the current status value for this Room
	 * @return status value of the room
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Sets the Room's status to the desired value
	 * @param Status status value to set the room to
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	public RoomType getRoomType() {
		return this.description.getRoomType();
	}
}
