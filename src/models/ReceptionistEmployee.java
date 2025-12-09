package models;

public class ReceptionistEmployee implements Employee{

    private final int id;
    private final EmployeeType type = EmployeeType.RECEPTION;
    private String name;
    private String phone;
    private String email;

    public ReceptionistEmployee(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }


    @Override
    public void fulfillRequest() {
        System.out.println("Receptionist " + name + " is handling front-desk requests.");
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