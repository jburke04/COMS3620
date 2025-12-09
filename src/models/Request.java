package models;

public interface Request {

    int getReqID();

    int getGuestID();

    Room getRoom();

    String getDesc();

    RequestStatus getStatus();
    /**
     * Completion of the request updates corresponding information
     */
    void complete();
}