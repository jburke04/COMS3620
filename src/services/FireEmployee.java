package services;

import models.*;
import java.util.*;

public class FireEmployee {
    public static void start (Scanner scanner, HotelSystem system) {
        System.out.println("=== FIRE AN EMPLOYEE ===");
        boolean exit = false;

        while (!exit) {
            int method = chooseSearch(scanner);
            if (method == 0) {
                System.out.println("Returning to previous menu... \n \n");
                return;
            }

            Employee employee = chooseEmployee(scanner, system, method);
            if (Objects.isNull(employee)) { //Indicates that the choose employee function was quit early.
                continue;
            }

            System.out.println("Are you sure you want to fire this employee?");
            System.out.println("y/n ");
            String choice = scanner.nextLine().substring(0, 1);
            boolean success = false;
            if (choice.equalsIgnoreCase("y")) {
                success = system.fireEmployee(employee);
            }
            if (success) {
                System.out.println("Employee fired.");
            }
            else {
                System.out.println("Employee not fired.");
            }
            return;
        }
    }

    private static int chooseSearch(Scanner scanner) {
        boolean exit = false;
        String choice = "";
        while (!exit) {
            System.out.println("Choose employee search method: ");
            System.out.println("1) Name");
            System.out.println("2) Phone Number");
            System.out.println("3) Employee ID");
            System.out.println("0) Quit");

            choice = scanner.nextLine();
            if (Objects.isNull(choice)) {
                System.out.println("Please input a choice. \n \n");
                continue;
            }
            choice = choice.substring(0, 1);
            if (!(choice.equals("0") || choice.equals("1") || choice.equals("2") || choice.equals("3"))) {
                System.out.println("Please input a valid choice. \n \n");
                continue;
            }
            exit = true;
        }
        return Integer.parseInt(choice);
    }

    private static Employee chooseEmployee(Scanner scanner, HotelSystem system, int method) {
        boolean exit = false;
        Employee employee = null;
        while (!exit) {
            switch (method) {
                case 1:
                    System.out.println("Enter the full name of an employee: ");
                    break;
                case 2:
                    System.out.println("Enter the phone number of an employee: ");
                    break;
                case 3:
                    System.out.println("Enter the ID of an employee: ");
                    break;
            }
            System.out.println("Enter Q to quit.");
            String input = scanner.nextLine().strip();

            if (Objects.isNull(input)) {
                System.out.println("Please input a value. \n \n");
                continue;
            }

            if (input.equalsIgnoreCase("q")) return null;

            employee = null; //Initialize it with something.
            switch (method) {
                case 1:
                    employee = system.findEmployeeByNameOrPhone(input);
                    break;
                case 2:
                    employee = system.findEmployeeByNameOrPhone(input);
                    break;
                case 3:
                    try {
                        employee = system.findEmployeeByID(Integer.parseInt(input));
                    }
                    catch (NumberFormatException e) {
                        //Do nothing, leave employee be null.
                    }
                    break;
            }

            if (Objects.isNull(employee)) {
                System.out.println("No employees were found for that search. \n \n");
                continue;
            }

            System.out.println("Is this the correct employee?\n");
            printEmployee(employee);
            System.out.println("\ny/n");
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return employee;
            }
            else {
                System.out.println("\n \n");
                return null;
            }
        }
        return employee;
    }

    private static void printEmployee(Employee e) {
        System.out.printf("%s, Role: %s, Phone #: %s, EMail: %s, Employee ID#: %d\n", e.getName(), e.type(), e.getPhone(), e.getEmail(), e.getId());
    }
}
