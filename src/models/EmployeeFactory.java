package models;

public class EmployeeFactory {

    public static Employee makeEmployee(EmployeeType type,
                                        int id,
                                        String name,
                                        String phone,
                                        String email) {
        if (type == null) {
            throw new IllegalArgumentException("EmployeeType cannot be null");
        }
        switch (type) {
            case EmployeeType.RECEPTION:
                return new ReceptionistEmployee(id, name, phone, email);
            case EmployeeType.HOUSEKEEPING:
                return new HousekeepingEmployee(id, name, phone, email);
            case EmployeeType.TECHNICIAN:
                return new MaintenanceEmployee(id, name, phone, email);
            case EmployeeType.BELLBOY:
                return new BellboyEmployee(id, name, phone, email);
            default:
                throw new IllegalArgumentException("Unknown EmployeeType: " + type);
        }
    }
}
