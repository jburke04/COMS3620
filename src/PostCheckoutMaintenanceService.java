package src;

import src.models.*;

public interface PostCheckoutMaintenanceService {

    void recordCleaningDone(int roomNumber);

    void recordInspectionResult(int roomNumber, boolean pass, String issueDescription, Severity severity);

    void resolveTicket(int ticketId);
}
