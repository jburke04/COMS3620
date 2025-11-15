package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import models.*;

public class PostCheckoutMaintenanceServiceImpl {

    private BookingSystem system;
    private List<MaintenanceTicket> tickets = new ArrayList<>();
    private AtomicInteger ticketId = new AtomicInteger(1);

    public PostCheckoutMaintenanceServiceImpl(BookingSystem system) {
        this.system = system;
    }

    public void cleaningDone(Room room) {
        System.out.println("Cleaning recorded for room " + room.getRoomNumber() + ". Room ready for inspection.");
    }

    public void inspectionResult(Room room, boolean pass, String issue) {
        if (pass) {
            System.out.println("Room " + room.getRoomNumber() + " passed inspection. Now available.");
            return;
        }
        int id = ticketId.getAndIncrement();
        String createdAt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
        MaintenanceTicket t = new MaintenanceTicket(
                id,
                room.getRoomNumber(),
                issue,
                createdAt,
                MaintenanceStatus.OPEN
        );
        tickets.add(t);
        System.out.println("Room failed inspection. Maintenance ticket created: #" + id);
    }

    public void finishMaintenance(int ticketNumber) {
        for (MaintenanceTicket t : tickets) {
            if (ticketNumber == ticketNumber && t.getStatus() == MaintenanceStatus.OPEN) {
                t.setStatus(MaintenanceStatus.RESOLVED);
                System.out.println("Ticket #" + ticketNumber + " resolved. Room needs re-inspection.");
                return;
            }
        }
        System.out.println("Ticket not found.");
    }

    public List<MaintenanceTicket> listTickets() {
        return tickets;
    }
}
