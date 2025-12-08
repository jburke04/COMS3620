package models;

public class BellboyEmployee implements Employee{
    private int id;
    private String name;
    private String phone;
    private String email;

    public BellboyEmployee(int id, String name, String phone, String email) {
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
        return null;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getPhone() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }
}
