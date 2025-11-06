package src;

import java.util.Calendar;

public class Room {
	int roomNumber;
	Status status;
	RoomDescription description;
	
	public Room(RoomType type) {
		if (type == RoomType.SINGLE) {
			description = new RoomDescription(type, 100);
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
		
		status = Status.AVAILABLE;
	}
	
	public Room(RoomType type, Status status) {
		this(type);
		this.status = status;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
}
