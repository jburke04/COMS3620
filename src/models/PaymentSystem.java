package models;

public class PaymentSystem {
    public PaymentSystem() {

    }

    public boolean checkoutAndPay(Booking booking, Room room) {
        // simulate external payment success (method is not validated here)
        booking.setStatus(BookingStatus.COMPLETED);
        room.setStatus(Status.NEEDS_CLEANING);
        return true;
    }
}
