package models;

/**
 * Employee interface to be used for employees of various departments.
 */
public interface Employee {

    /**
     * Employee fulfilling a request with their respective department
     */
    void fulfillRequest();

    /**
     * Used to grab the type of employee, specifically for when using collections of this interface.
     */
    EmployeeType type();

    /**
     * Grabs this employee's ID.
     * @return ID
     */
    int getId();

    /**
     * Returns this employee's name
     * @return Name
     */
    String getName();

    /**
     * Returns this employee's phone
     * @return
     */
    String getPhone();

    /**
     * Returns this employee's email address.
     * @return Email
     */
    String getEmail();
}