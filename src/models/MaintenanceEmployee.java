package models;

public class MaintenanceEmployee implements Employee{
    private int id;
    private final EmployeeType type = EmployeeType.TECHNICIAN;
    private String name;
    private String phone;
    private String email;

    public MaintenanceEmployee(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void fulfillRequest() {
        System.out.println("Maintenance technician " + name + " is handling technical issues.");
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
