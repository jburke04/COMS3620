package models;

public class BellboyEmployee implements Employee{
    private int id;
    private final EmployeeType type = EmployeeType.BELLBOY;
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
        System.out.println("Bellboy " + name + " is assisting guests with luggage.");
    }

    @Override
    public EmployeeType type() {
        return type;
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
