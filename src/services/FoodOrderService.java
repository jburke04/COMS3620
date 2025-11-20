package services;

import models.*;
import java.util.*;

public class FoodOrderService {

    private static class MenuItem {
        final int id;
        final String name;
        final String category;
        final double price;

        MenuItem(int id, String name, String category, double price) {
            this.id = id;
            this.name = name;
            this.category = category;
            this.price = price;
        }
    }

    private static final List<MenuItem> MENU = List.of(
            new MenuItem(1, "Pancakes with Syrup", "Breakfast", 9.00),
            new MenuItem(2, "Scrambled Eggs & Toast", "Breakfast", 8.00),
            new MenuItem(3, "Club Sandwich", "Snacks", 10.50),
            new MenuItem(4, "French Fries", "Snacks", 5.00),
            new MenuItem(5, "Cheeseburger", "Main", 12.00),
            new MenuItem(6, "Grilled Chicken Plate", "Main", 14.50),
            new MenuItem(7, "Coffee", "Drinks", 3.00),
            new MenuItem(8, "Orange Juice", "Drinks", 4.00),
            new MenuItem(9, "Chocolate Cake", "Dessert", 6.00),
            new MenuItem(10, "Vanilla Ice Cream", "Dessert", 4.50)
    );


    private static int nextOrderId = 1;

    public static void start(Scanner scanner, HotelSystem system) {
        System.out.println("\n=== FOOD MENU & ROOM ORDERING ===");

        Room room = selectRoom(scanner, system);
        if (room == null) {
            return;
        }

        Map<MenuItem, Integer> cart = new LinkedHashMap<>();
        buildOrder(scanner, cart);

        if (cart.isEmpty()) {
            System.out.println("No items selected. Order cancelled.");
            return;
        }

        double total = showSummaryAndConfirm(scanner, room, cart);
        if (total < 0) {
            System.out.println("Order cancelled.");
            return;
        }

        int orderId = nextOrderId++;
        System.out.println("\nOrder ticket created: #" + orderId);
        System.out.println("Order sent to kitchen for Room " + room.getRoomNumber() + ".");
        System.out.println("Total due (added to room folio): $" + String.format("%.2f", total));
    }

    private static Room selectRoom(Scanner scanner, HotelSystem system) {
        System.out.print("Enter guest room number: ");
        String input = scanner.nextLine().trim();
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid room number.");
            return null;
        }

        if (!system.roomInSystem(roomNumber)) {
            System.out.println("Room does not exist in system.");
            return null;
        }

        Room room = system.getRoomByNumber(roomNumber);
        Status status = room.getStatus();

        // Prefer to allow orders only if room is in-use
        if (!(status == Status.OCCUPIED || status == Status.USED || status == Status.AWAITING)) {
            System.out.println("Warning: Room is not currently occupied (status: " + status + ").");
            System.out.print("Proceed with order anyway? (y/n): ");
            String ans = scanner.nextLine().trim().toLowerCase();
            if (!ans.startsWith("y")) {
                System.out.println("Order cancelled.");
                return null;
            }
        }

        return room;
    }

    private static void buildOrder(Scanner scanner, Map<MenuItem, Integer> cart) {
        while (true) {
            printMenu(cart);

            System.out.print("Enter item ID to add, [R]emove item, or [F]inish: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("f")) {
                return;
            } else if (choice.equals("r")) {
                removeItem(scanner, cart);
                continue;
            }

            int id;
            try {
                id = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice.");
                continue;
            }

            MenuItem item = findMenuItem(id);
            if (item == null) {
                System.out.println("Item not found.");
                continue;
            }

            System.out.print("Quantity for " + item.name + ": ");
            String qStr = scanner.nextLine().trim();
            int qty;
            try {
                qty = Integer.parseInt(qStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity.");
                continue;
            }
            if (qty <= 0) {
                System.out.println("Quantity must be positive.");
                continue;
            }

            cart.put(item, cart.getOrDefault(item, 0) + qty);
            System.out.println("Added " + qty + " x " + item.name + " to order.");
        }
    }

    private static double showSummaryAndConfirm(Scanner scanner, Room room, Map<MenuItem, Integer> cart) {
        System.out.println("\n=== ORDER SUMMARY ===");
        System.out.println("Room: " + room.getRoomNumber());
        double total = 0.0;
        for (Map.Entry<MenuItem, Integer> e : cart.entrySet()) {
            MenuItem item = e.getKey();
            int qty = e.getValue();
            double line = qty * item.price;
            total += line;
            System.out.printf(" - %dx %-20s @ $%.2f = $%.2f%n", qty, item.name, item.price, line);
        }
        System.out.printf("TOTAL: $%.2f%n", total);

        System.out.print("Confirm and place order? (y/n): ");
        String ans = scanner.nextLine().trim().toLowerCase();
        if (!ans.startsWith("y")) {
            return -1.0;
        }
        return total;
    }

    private static void removeItem(Scanner scanner, Map<MenuItem, Integer> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.print("Enter item ID to remove: ");
        String s = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return;
        }
        MenuItem target = findMenuItem(id);
        if (target == null || !cart.containsKey(target)) {
            System.out.println("That item is not in the current order.");
            return;
        }
        cart.remove(target);
        System.out.println("Removed " + target.name + " from order.");
    }

    private static void printMenu(Map<MenuItem, Integer> cart) {
        System.out.println("\n--- FOOD MENU ---");
        String currentCategory = "";
        for (MenuItem item : MENU) {
            if (!item.category.equals(currentCategory)) {
                currentCategory = item.category;
                System.out.println("[" + currentCategory + "]");
            }
            System.out.printf("%2d) %-20s $%.2f%n", item.id, item.name, item.price);
        }

        if (!cart.isEmpty()) {
            System.out.println("\nCurrent Order:");
            for (Map.Entry<MenuItem, Integer> e : cart.entrySet()) {
                System.out.printf(" - %dx %s%n", e.getValue(), e.getKey().name);
            }
        }
    }

    private static MenuItem findMenuItem(int id) {
        for (MenuItem m : MENU) {
            if (m.id == id) return m;
        }
        return null;
    }
}
