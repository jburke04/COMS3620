package models;

public class RoomDescription {
	private models.RoomType type;
	private double cost;
	
	/**
	 * Constructor for a Room Description that holds information for a specific
	 * room type and the regular cost per night
	 * @param RoomType enumerated value representing the type of room
	 * @param double cost per night for this room
	 */
	public RoomDescription(models.RoomType type, double cost) {
		this.type = type;
		this.cost = cost;
	}

	/**
	 * Returns the enumberated value for the Room Type
	 * @return enum of Room Type
	 */
	public models.RoomType getRoomType() {
		return this.type;
	}
}
