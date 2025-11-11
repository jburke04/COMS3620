//package src;
//
//import java.util.Scanner;
//import src.models.*;

//public class InStayMaintenance {
//    public static void main(String[] args) {
//        InStayMaintenanceService svc = new InStayMaintenanceService();
//        Scanner sc = new Scanner(System.in);
//        final int GUEST_ID = 1;
//
//        System.out.println("=== In-Stay Maintenance ===");
//        System.out.println("Guest: " + svc.getGuest(GUEST_ID));
//        System.out.println();
//
//        System.out.print("Describe the issue: ");
//        String description = sc.nextLine().trim();
//
//        System.out.print("Severity [BLOCKING/MINOR] (default MINOR): ");
//        String sevIn = sc.nextLine().trim();
//        Severity severity = sevIn.isEmpty() ? Severity.MINOR : Severity.valueOf(sevIn.toUpperCase());
//
//        System.out.print("Preferred visit window (e.g., 2-4 PM): ");
//        String visitWindow = sc.nextLine().trim();
//
//        MaintenanceTicket ticket = svc.reportIssue(GUEST_ID, description, severity, visitWindow);
//        System.out.println("Ticket created: " + ticket);
//        System.out.println();
//
//        System.out.print("At the scheduled time, are you available? [y/n]: ");
//        boolean available = sc.nextLine().trim().toLowerCase().startsWith("y");
//        if (!available) {
//            System.out.print("New preferred window: ");
//            String newWindow = sc.nextLine().trim();
//            svc.rescheduleVisit(ticket.getID(), newWindow);
//            System.out.println("Visit rescheduled to: " + newWindow);
//            System.out.println("Proceeding to visit...");
//        }
//
//        System.out.print("Maintenance attempt: fixed on the spot? [y/n]: ");
//        boolean fixedNow = sc.nextLine().trim().toLowerCase().startsWith("y");
//
//        if (fixedNow) {
//            // Main success path
//            svc.visitAndFix(ticket.getID());
//            System.out.print("Is the fix acceptable? [y/n]: ");
//            boolean accepted = sc.nextLine().trim().toLowerCase().startsWith("y");
//            svc.guestConfirm(ticket.getID(), accepted);
//            System.out.println(accepted ? "Great — ticket resolved and accepted."
//                                        : "Noted — ticket resolved but guest did not accept.");
//        } else {
//            // Only ask what’s needed next
//            System.out.print("Is it a comfort/safety impact requiring a room change today? [y/n]: ");
//            boolean needsRelocation = sc.nextLine().trim().toLowerCase().startsWith("y");
//            if (needsRelocation) {
//                System.out.print("Choose an available room (e.g., 102 or 103): ");
//                int newRoom = Integer.parseInt(sc.nextLine().trim());
//                svc.relocateGuestSameRate(GUEST_ID, newRoom);
//                System.out.println("Relocated to room " + newRoom + ". Original room marked AWAITING.");
//            } else {
//                System.out.print("Parts needed. Follow-up visit window: ");
//                String followUp = sc.nextLine().trim();
//                svc.markNeedsParts(ticket.getID(), followUp);
//                System.out.println("Follow-up scheduled: " + followUp + " (ticket remains OPEN).");
//            }
//        }
//
//        System.out.println();
//        System.out.println("Current ticket state:");
//        for (MaintenanceTicket t : svc.getAllTickets()) {
//            System.out.println(" - " + t);
//        }
//        System.out.println("Guest state: " + svc.getGuest(GUEST_ID));
//        System.out.println("Room101: " + svc.getRoom(101));
//        System.out.println("Room102: " + svc.getRoom(102));
//        System.out.println("Room103: " + svc.getRoom(103));
//    }
//}
/**
 * For iteration 1
 *
 */

package src;

import src.models.*;

import java.util.Scanner;

public class InStayMaintenance {
    public static void start(Scanner sc, BookingSystem system) {
        System.out.println("\n=== IN-STAY MAINTENANCE ===");
        System.out.println("1) Create ticket");
        System.out.println("2) Resolve ticket");
        System.out.print("Choose: ");
        String c = sc.nextLine().trim();
        if ("1".equals(c)) {
            System.out.print("Room number: ");
            int room = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Severity (LOW/MEDIUM/HIGH): ");
            String sev = sc.nextLine().trim();
            System.out.print("Description: ");
            String desc = sc.nextLine().trim();
            MaintenanceTicket t = system.createTicket(room, desc, sev);
            System.out.println("✅ Created: " + t);
        } else if ("2".equals(c)) {
            System.out.print("Ticket id: ");
            int id = Integer.parseInt(sc.nextLine().trim());
            boolean ok = system.resolveTicket(id);
            System.out.println(ok ? "✅ Resolved." : "⚠️ Not found.");
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
