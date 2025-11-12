package src.models;

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

	public int getRoomNumber() { return this.roomNumber; }
	public Status getStatus() { return this.status; }
	public void setStatus(Status status) { this.status = status; }
	public RoomType getRoomType() { return this.roomType; }
	public double getCost() { return this.cost; }
}


