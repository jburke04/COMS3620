package models;

/**
 * A Guest may want to request Food be sent to their Room during their stay. A
 * Food Request will be made by calling the front desk and providing the items they want
 * and the Room for it to be delivered to.
 */
public class FoodRequest {

    private static int reqID = 100;
    private final int guestID;
    private final Room room;
    private double cost;
    private String desc;

        /**
     * Constructor for a new Food Request.
     * @param reqID Unique ID for this request
     * @param guestID Guest's ID
     * @param room Room to service to
     * @param cost Total cost of request
     * @param desc Formatted description of the request.
     */
    public FoodRequest(int reqID, int guestID, Room room, double cost, String desc) {
        this.reqID = reqID;
        this.guestID = guestID;
        this.room = room;
        this.cost = cost;
        this.desc = desc;

        if (reqID >= FoodRequest.reqID)
            FoodRequest.reqID = this.reqID + 1;
        
    }

    /**
     * Constructor for a new Food Request.
     * @param guestID Guest's ID
     * @param room Room to service to
     * @param cost Total cost of request
     * @param desc Formatted description of the request.
     */
    public FoodRequest(int guestID, Room room, double cost, String desc) {
        this(FoodRequest.reqID++, guestID, room, cost, desc);
    }

    // --------------- GETTERS -------------------

    public int getReqID() {
        return this.reqID;
    }

    public int getGuestID() {
        return this.guestID;
    }

    public Room getRoom() {
        return this.room;
    }

    public double getCost() {
        return this.cost;
    }

    public String getDesc() {
        return this.desc;
    }

    // --------------- SETTERS ----------------

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}