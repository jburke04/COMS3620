package services;

import java.util.Scanner;
import models.*;

/**
 * Service for onboarding / hiring a new employee.
 * Implements the "Onboard New Employee" use case.
 */
public class HireEmployeeService {

    public static void start(Scanner sc, HotelSystem system) {
        System.out.println("\n=== HIRE / ONBOARD EMPLOYEE ===");

        EmployeeType type = chooseRole(sc);
        if (type == null) {
            System.out.println("Hiring cancelled.");
            return;
        }

        String name  = promptRequired(sc, "Enter employee full name: ");
        String phone = promptRequired(sc, "Enter phone number: ");
        System.out.print("Enter email (optional): ");
        String email = sc.nextLine().trim();

        // Simple duplicate warning (same role + phone)
        Employee possibleDuplicate = findByRoleAndPhone(system, type, phone);
        if (possibleDuplicate != null) {
            System.out.println("\nWARNING: An employee with this phone and role already exists:");
            System.out.println("  ID:   " + possibleDuplicate.getId());
            System.out.println("  Name: " + possibleDuplicate.getName());
            System.out.println("  Role: " + possibleDuplicate.type());
            System.out.print("Continue and create a new record anyway? (Y/N): ");
            String dupChoice = sc.nextLine().trim().toLowerCase();
            if (!dupChoice.startsWith("y")) {
                System.out.println("Hiring cancelled to avoid duplicate.");
                return;
            }
        }

        System.out.println("\n--- New Employee Summary ---");
        System.out.println("Role : " + type);
        System.out.println("Name : " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + (email.isEmpty() ? "(none)" : email));

        System.out.print("\nConfirm hire? (Y/N): ");
        String confirm = sc.nextLine().trim().toLowerCase();
        if (!confirm.startsWith("y")) {
            System.out.println("Hiring cancelled.");
            return;
        }

        try {
            Employee emp = system.hireEmployee(type, name, phone, email);
            System.out.println("\n✓ Employee onboarded successfully.");
            System.out.println("Assigned Employee ID: " + emp.getId());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (RuntimeException ex) {
            System.out.println("Error while saving employee: " + ex.getMessage());
            System.out.println("Employee may not have been persisted; contact IT.");
        }
    }

    private static EmployeeType chooseRole(Scanner sc) {
        while (true) {
            System.out.println("\nSelect role:");
            int idx = 1;
            for (EmployeeType t : EmployeeType.values()) {
                System.out.println(idx + ") " + t);
                idx++;
            }
            System.out.println("0) Cancel");
            System.out.print("Choice: ");
            String input = sc.nextLine().trim();

            if (input.equals("0")) {
                return null;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= EmployeeType.values().length) {
                    return EmployeeType.values()[choice - 1];
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println("Invalid choice. Please enter a valid number.");
        }
    }

    private static String promptRequired(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field is required. Please enter a value.");
        }
    }

    private static Employee findByRoleAndPhone(HotelSystem system,
                                               EmployeeType type,
                                               String phone) {
        for (Employee e : system.getEmployees()) {
            if (e.type() == type &&
                    e.getPhone().equalsIgnoreCase(phone.trim())) {
                return e;
            }
        }
        return null;
    }
}
