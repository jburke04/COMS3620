package models;

public interface SubSystem {

    /**
     * Loads respective data into SubSystem
     */
    void load();

    /**
     * Saves data into JSON files
     */
    void save();
}