package src.models;

public class RoomDescription {
	private RoomType type;
	private double cost;
	
	/**
	 * Constructor for a Room Description that holds information for a specific
	 * room type and the regular cost per night
	 * @param RoomType enumerated value representing the type of room
	 * @param double cost per night for this room
	 */
	public RoomDescription(RoomType type, double cost) {
		this.type = type;
		this.cost = cost;
	}

	/**
	 * Returns the enumerated value for the Room Type
	 * @return enum of Room Type
	 */
	public RoomType getRoomType() {
		return this.type;
	}

	/**
	 * Returns the cost per night for this room
	 * @return double value for the room's daily cost
	 */
	public double getCost() {
		return this.cost;
	}

	/**
	 * Stringifies the RoomType
	 * @return String representation of this Room Description
	 */
    public String toString() {
        switch (this.type) {
            case DOUBLE:
                return "Double Bed";
            case KING:
                return "King Bed";
            case PRESIDENTIAL:
                return "Presidential Suite";
            default:
                return "Single Bed";
        }
    }
}
