package src.models;

public interface Request {
    
    /**
     * Request notifies the appropriate department with the relevant
     * information for the request
     */
    void requestNotify();

    /**
     * Completion of the request updates corresponding information
     */
    void complete();
}