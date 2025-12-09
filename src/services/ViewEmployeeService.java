package services;

import models.*;
import java.util.*;
import javax.lang.model.util.ElementScanner14;

/**
 * Service for Viewing Employee(s) within this Hotel's System
 */
public class ViewEmployeeService {

    /**
     * Starter loop for Viewing Employee Service
     * @param sc User input scanner
     * @param system Hotel SYstem to utilize
     */
    public static void start(Scanner sc, HotelSystem system) {
        String input = "";

        String role = "";
        EmployeeType type = null;
        String nameOrPhoneNumber = "";
        int id = 0;
        List<Employee> temp = new ArrayList<>();
        List<Employee> results = new ArrayList<>();

        System.out.println("\n============ VIEW EMPLOYEES ============\n");

        do { 
            System.out.println("[Q]uit to return to main menu, Enter for no input for the following search criteria.");
            // get search criteria:

            do { 
                // ID needs to be a number:
                System.out.print("ID: ");
                input = sc.nextLine().trim();

                if (check(input) == 0)
                    return;
                if (check(input) == 1)
                    break;
                else if (check(input) == 2) {
                    if (Double.isNaN(Double.parseDouble(input)))
                        System.out.println("Invalid input. ID should be a numerical value.");
                    else {
                        id = Integer.parseInt(input);
                        break;
                    }
                }
                    
            } while (true);
            
            System.out.print("Phone Number or Name: ");
            input = sc.nextLine().trim();
            
            if (check(input) == 0)
                return;
            else if (check(input) == 2)
                nameOrPhoneNumber = input;
            
            System.out.print("Role: ");
            input = sc.nextLine().trim();

            if (check(input) == 0)
                return;
            else if (check(input) == 2)
                role = input;

            // narrow down criteria:
            if (id != 0) {
                Employee e = system.getEmployeeByID(id);
                if (e != null)
                    results.add(e);
            }
            else
                results = new ArrayList<>(system.getEmployees());

            if (!nameOrPhoneNumber.isEmpty())
                results = filter(results, nameOrPhoneNumber);
                
            if (!role.isEmpty() && (type = findType(role)) != null)
                results = filter(results, type);

            if (results.isEmpty())
                System.out.println("\nNo Employees matched provided criteria.\n");
            else
                stringify(results);
        } while (true);

    }

    /**
     * Checking if the user is quitting, put no criteria, or put criteria.
     * @param input String to check
     * @return integer representing which--0 for quitting, 1 for no criteria, 2 for criteria entered.
     */
    private static int check(String input) {
        // check if quitting:
        if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
            System.out.println("\nReturning to Main Menu.");
            return 0;
        }
        // check if no criteria:
        else if (input.isEmpty())
            return 1;
        
        return 2;
    }

    /**
     * Converts String to EmployeeType
     * @param role String to compare and then convert
     * @return EmployeeType
     */
    private static EmployeeType findType(String role) {
        if (role.equalsIgnoreCase("reception"))
            return EmployeeType.RECEPTION;
        if (role.equalsIgnoreCase("housekeeping"))
            return EmployeeType.HOUSEKEEPING;
        if (role.equalsIgnoreCase("technician"))
            return EmployeeType.TECHNICIAN;
        if (role.equalsIgnoreCase("bellboy"))
            return EmployeeType.BELLBOY;
        return null;
    }

    /**
     * Filter selection based on Name or Phone.
     * @param emps List to filter.
     * @param nameOrPhoneNumber Name or Phone Number to search for.
     * @return Resulting list that matches criteria.
     */
    private static List<Employee> filter(List<Employee> emps, String nameOrPhoneNumber) {
        List<Employee> results = new ArrayList<>();

        if (emps.isEmpty() || emps == null)
            return null;

        for (Employee e : emps) {
            if (e.getName().equalsIgnoreCase(nameOrPhoneNumber) || e.getPhone().equalsIgnoreCase(nameOrPhoneNumber))
                results.add(e);
        }

        return results;
    }

    /**
     * Filter selection based on type.
     * @param emps List to filter.
     * @param role Type to search for.
     * @return Resulting list that matches criteria.
     */
    private static List<Employee> filter(List<Employee> emps, EmployeeType role) {
        List<Employee> results = new ArrayList<>();

        if (emps.isEmpty() || emps == null)
            return null;

        for (Employee e : emps) {
            if (e.type() == role)
                results.add(e);
        }

        return results;
    }

    /**
     * Takes list of Employees and produces a String.
     * @param emps List to Stringify.
     */
    private static void stringify(List<Employee> emps) {
        System.out.println("\nCriteria matched:\n");

        for (Employee e : emps) {
            System.out.println("\n\tName: " + e.getName()
                    + "\n\tID: " + e.getId()
                    + "\n\tPhone Number: " + e.getPhone()
                    + "\n\tEmail: " + e.getEmail());
        }
    }
}