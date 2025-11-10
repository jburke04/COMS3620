package src.models;

/**
 * 
 */
//public class Room {
//	public RoomDescription description;
//	public int roomNumber;
//	private Status status;
//
//	public Room(RoomType type, int roomNumber) {
//		if (type == RoomType.SINGLE) {
//			description = new RoomDescription(type, 100);
//		}
//		else if (type == RoomType.DOUBLE) {
//			description = new RoomDescription(type, 150);
//		}
//		else if (type == RoomType.KING) {
//			description = new RoomDescription(type, 200);
//		}
//		else if (type == RoomType.PRESIDENTIAL) {
//			description = new RoomDescription(type, 300);
//		}
//
//		this.roomNumber = roomNumber;
//		status = Status.AVAILABLE;
//	}
//
//	public Room(RoomType type, int roomNumber, Status status) {
//		this(type, roomNumber);
//		this.status = status;
//	}
//
//	public RoomType getRoomType() {
//		return this.description.getRoomType();
//	}
//
//	public double getCost() {
//		return this.description.getCost();
//	}
//
//	public int getRoomNumber() {
//		return this.roomNumber;
//	}
//
//	/**
//	 * Gets the current status value for this Room
//	 * @return status value of the room
//	 */
//	public Status getStatus() {
//		return status;
//	}
//
//	/**
//	 * Sets the Room's status to the desired value
//	 * @param Status status value to set the room to
//	 */
//	public void setStatus(Status status) {
//		this.status = status;
//	}

/**
 * Redoing the file for iteration 1 demo 1
 */
public class Room {
	private int roomNumber;
	private Status status;
	private RoomType roomType;
	private double cost;

	public Room(int roomNumber, Status status, RoomType roomType, double cost) {
		this.roomNumber = roomNumber;
		this.status = status;
		this.roomType = roomType;
		this.cost = cost;
	}

	public int getRoomNumber() { return roomNumber; }
	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	public RoomType getRoomType() { return roomType; }
	public double getCost() { return cost; }
}


