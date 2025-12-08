package models;

import helpers.Parser;

import java.util.*;

public class EmployeeSystem implements SubSystem{
    private final String employeePath = "src/assets/Employees.json";
    private List<Employee> employees = new ArrayList<>();

    @Override
    public void load() {
        Parser.parseEmployees(employeePath, employees);
    }

    @Override
    public void save() {
        Parser.saveEmployees(employeePath, employees);
    }

    public void hireEmployee() {

    }

    public boolean fireEmployee() {
        return false;
    }
}