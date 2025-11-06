package src;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. Room gets cleaned
 * 2. Room gets inspected
 * 3. If problem -> create maintenance ticket
 * 4. When fixed -> set room ready for inspection again
 */
public class PostCheckoutMaintenanceServiceImpl {

    private BookingSystem system;

    // Storing ticket numbers in arraylist for now.
    private List<MaintenanceTicket> tickets = new ArrayList<>();
    private AtomicInteger ticketId = new AtomicInteger(1);

    public PostCheckoutMaintenanceServiceImpl(BookingSystem system) {
        this.system = system;
    }

    // Called when housekeeper finishes cleaning
    public void cleaningDone(int roomNumber) {
        // TODO: room in CLEANING
        boolean ok = system.clean(roomNumber);
        if (!ok) {
            System.out.println("Error: cleaning update failed");
        } else {
            System.out.println("Cleaning recorded. Room ready for inspection.");
        }
    }

    public void inspectionResult(int roomNumber, boolean pass, String issue, Severity severity) {
        if (pass) {
            boolean ok = system.inspect(roomNumber);
            if (ok) System.out.println("Room passed inspection. Now available.");
            return;
        }

        // If inspection failed then we make maintenance ticket
        MaintenanceTicket t = new MaintenanceTicket(
            ticketId.getAndIncrement(),
            roomNumber,
            issue,
            severity
        );
        tickets.add(t);

        System.out.println("Room failed inspection. Maintenance ticket created: #" + t.id);
        // TODO: mark room AWAITING or OUT_OF_SERVICE
    }

    public void finishMaintenance(int ticketNumber) {
        for (MaintenanceTicket t : tickets) {
            if (t.id == ticketNumber && t.status == MaintenanceStatus.OPEN) {
                t.status = MaintenanceStatus.RESOLVED;
                t.resolvedAt = java.util.Calendar.getInstance();
                System.out.println("Ticket #" + ticketNumber + " resolved. Room needs re-inspection.");
                // TODO: move room to INSPECTING state 
                return;
            }
        }
        System.out.println("Ticket not found.");
    }

    public List<MaintenanceTicket> listTickets() {
        return tickets;
    }
}
