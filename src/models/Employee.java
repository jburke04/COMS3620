package models;

/**
 * Employee interface to be used for employees of various departments.
 * Each Employee has an id, a role (EmployeeType), and basic contact info.
 */
public interface Employee {

    /**
     * Employee fulfilling a request with their respective department
     */
    void fulfillRequest();

    /**
     *Returns the role/type of this employee.
     */
    EmployeeType type();

    /**
     * Returns this employee's ID.
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
     * @return Phone
     */
    String getPhone();

    /**
     * Returns this employee's email address.
     * @return Email
     */
    String getEmail();
}