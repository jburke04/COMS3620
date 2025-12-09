package models;

import helpers.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeSystem implements SubSystem{
    private final String employeePath = "src/assets/Employees.json";
    private List<Employee> employees = new ArrayList<>();
    private int nextEmployeeId = 1;

    @Override
    public void load() {
        Parser.parseEmployees(employeePath, employees);

        // Compute next id based on current employees
        int max = 0;
        for (Employee e : employees) {
            if (e.getId() > max) {
                max = e.getId();
            }
        }
        nextEmployeeId = max + 1;
    }


    @Override
    public void save() {
        Parser.saveEmployees(employeePath, employees);
    }

    /**
     * Read-only view of current employees.
     */
    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }


    /**
     * Core operation for the "Onboard New Employee" use case.
     * Creates a new Employee, assigns a unique id, stores it, and saves to file.
     */
    public Employee hireEmployee(EmployeeType type,
                                 String name,
                                 String phone,
                                 String email) {

        if (type == null) {
            throw new IllegalArgumentException("Employee type is required.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name is required.");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Employee phone is required.");
        }

        String trimmedName = name.trim();
        String trimmedPhone = phone.trim();
        String trimmedEmail = (email == null) ? "" : email.trim();

        // Optional duplicate check (same phone + role)
        for (Employee e : employees) {
            if (e.type() == type &&
                    e.getPhone().equalsIgnoreCase(trimmedPhone)) {
                // For now just allow duplicates; higher-level service warns user.
                break;
            }
        }

        Employee newEmp = EmployeeFactory.makeEmployee(
                type,
                nextEmployeeId++,
                trimmedName,
                trimmedPhone,
                trimmedEmail
        );

        employees.add(newEmp);
        save();
        return newEmp;
    }

    /**
     * (Optional for future iterations.)
     */
    public boolean fireEmployee(int employeeId) {
        return employees.removeIf(e -> e.getId() == employeeId);
    }
}