package models;

public class HousekeepingEmployee implements Employee{
    private int id;
    private String name;
    private String phone;
    private String email;

    public HousekeepingEmployee(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void fulfillRequest() {

    }

    @Override
    public EmployeeType type() {
        return EmployeeType.HOUSEKEEPING;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
    }
}