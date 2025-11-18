package src.models;

/**
 * System interface to be applied to various Systems within the Hotel.
 */
public interface System {

    /**
     * Loads respective files for System
     */
    void load();

    /**
     * Saves respective files for System
     */
    void save();
}