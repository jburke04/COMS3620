package models;

public interface Request {

    int getReqID();

    int getGuestID();

    Room getRoom();

    String getDesc();

    RequestStatus getStatus();

    void setDesc(String desc);

    void setStatus(RequestStatus status);
    /**
     * Completion of the request updates corresponding information
     */
    void complete();
}