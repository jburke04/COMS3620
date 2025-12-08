package models;

import java.util.*;

/**
 * Awesome new class that will take pressure off the BookingSystem and distribute the work more evenly across
 * many new subsystem classes. Ideally, we have HotelSystem make calls to individual Booking, Guest, Employee, and
 * whatever other Systems we choose to add in the future. The HotelSystem will also handle getting info from appropriate
 * System classes and piping them to other System classes that need them.
 *
 * I think for now the design principle here is to receive calls from the Menuing and direct them to the appropriate systems.
 * We can also create collaboration between systems without them knowing anything about each other.
 */

public class HotelSystem {

    //These should only be accessible from this class. No other class needs to see how this system works.
    private final BookingSystem bookingSystem = new BookingSystem();
    private final GuestSystem guestSystem = new GuestSystem();
    private final RoomSystem roomSystem = new RoomSystem();
    private final MaintenanceSystem maintenanceSystem;
    private final PaymentSystem paymentSystem;
    private final RoomUtils utils;
    //EmployeeSystem employeeSystem; FOR LATER USE.


    /**
     * Where it all starts. This will give us our subsystems to play with.
     */
    public HotelSystem() {
        this.roomSystem.load();
        this.bookingSystem.load();
        this.guestSystem.load();
        this.utils = new RoomUtils(this.bookingSystem.getBookings(), this.roomSystem.getRooms());
        this.maintenanceSystem = new MaintenanceSystem(utils);
        this.paymentSystem = new PaymentSystem();
    }

    //GUEST METHODS
    /**
     * Lists all the guests in the guest system.
     */
    public void listGuests() {
        guestSystem.listGuests();
    }

    /**
     * Returns a value based on if a Guest is in the system or not. This can replace the current call to BookingSystem.findGuest
     * as this returns what that function is looking for. This function will do all the checks without returning a guest object.
     * We want to keep Guest objects contained almost exclusively in guest system and occasionally passed from GuestSystem to other
     * subsystems.
     * @param phoneOrName A phone number or name to search for.
     * @return Whether the guest was found or not.
     */
    public boolean guestInSystem(String phoneOrName) {
        /*Really contrived return statement here. Basically, return the negation of whether the guest object is null.
        If a guest is found, it will not be null and therefore the entire statement evaluates to true. If no guest is found,
        the object will be null and the statement will evaluate to false. It works!*/
        return !Objects.isNull(guestSystem.findGuestByPhoneOrName(phoneOrName));
    }

    /**
     * Returns the list of Guests in this Hotel System.
     * @return The list of Guest objects.
     */
    public List<Guest> getGuests() {
        return guestSystem.getGuests();
    }

    public Guest findGuestByPhoneOrName(String phoneOrName) {
        return guestSystem.findGuestByPhoneOrName(phoneOrName);
    }

    /**
     * Finds a Guest by the corresponding Guest ID.
     * @param id Guest ID to search for.
     * @return Guest with the corresponding Guest ID.
     */
    public Guest findGuestByID(int id) {
        return guestSystem.findGuestByID(id);
    }

    public void addGuest(Guest guest) {
        guestSystem.addGuest(guest);
    }

    //ROOM METHODS

    /**
     * Unfortunately, I think for now returning a list of rooms is not optional. I wish it was, but how the menuing
     * is set up I don't think will allow it to return something else, so it stays like this for now.
     * @param type The type of room to search for.
     * @param startDate Start of the requested stay.
     * @param endDate End of the requested stay.
     * @return A list of rooms fitting the criteria.
     */
    public List<Room> findAvailableRooms(RoomType type, Calendar startDate, Calendar endDate) {
        //It's unfortunate I had to make a call to getBookings() but it's just that way sometimes.
        return roomSystem.findAvailableRooms(type, bookingSystem.getBookings(),startDate, endDate);
    }

    /**
     * Returns whether a room number exists or not.
     * @param roomNumber The room number to search for.
     * @return Whether the room number was found or not.
     */
    public boolean roomInSystem(int roomNumber) {
        //Another one of these return statements.
        return !Objects.isNull(roomSystem.findRoomByNumber(roomNumber));
    }

    public Room getRoomByNumber(int roomNumber) {
        return roomSystem.findRoomByNumber(roomNumber);
    }

    public boolean isRoomAvailable(int roomNumber, Calendar start, Calendar end) {
        return roomSystem.isRoomAvailable(roomSystem.findRoomByNumber(roomNumber), bookingSystem.getBookings(), start, end);
    }

    public boolean isRoomAvailable(Room room, Calendar start, Calendar end) {
        return roomSystem.isRoomAvailable(room, bookingSystem.getBookings(), start, end);
    }

    //BOOKING METHODS

    public List<Booking> getBookings() {
        return this.bookingSystem.getBookings();
    }

    /**
     * Returns whether a booking is in the system.
     * @param confirmationNumber A confirmation number to search for.
     * @return Whether the booking was found or not.
     */
    public boolean bookingInSystem(int confirmationNumber) {
        //Same principle as the guestInSystem() method.
        return !Objects.isNull(bookingSystem.findBookingByConfirmation(confirmationNumber));
    }

    /**
     * So this function serves a certain purpose. That is to not have to call isRoomAvailable() outside of the RoomSystem.
     * It also serves to concatenate isRoomAvailable and createBooking.
     * @param room The room to be booked.
     * @param startDate The start date of the booking.
     * @param endDate The end date of the booking.
     * @return Whether the booking was successful or not.
     */
    public boolean bookRoom(Guest guest, Room room, Calendar startDate, Calendar endDate) {
        if (!roomSystem.isRoomAvailable(room, bookingSystem.getBookings(), startDate, endDate)) {
            return false;
        }

        // room is available, book it:
        boolean success = !Objects.isNull(bookingSystem.createBooking(guest, room, startDate, endDate)); //Exclusively so I can save room state after this call.
        roomSystem.save();
        return success;
    }

    /**
     *  Changes the room associated with a booking.
     * @param confirmationNumber The confirmation number of a booking.
     * @param newRoomNumber The room number of the room to move to.
     * @return Whether the room change was successful.
     */
    public boolean changeRoom(Booking b, int newRoomNumber) {
        //TODO: MAKE SURE TO ADD THE COST RECALCULATION IN HERE!
        if (!roomSystem.isRoomAvailable(roomSystem.findRoomByNumber(newRoomNumber), 
            bookingSystem.getBookings(), 
            b.getStartTime(),
            b.getEndTime()))
            return false;

        Room prev = b.getRoom();
        boolean success = bookingSystem.changeRoom(b, roomSystem.findRoomByNumber(newRoomNumber));
        if (b.getStatus() == BookingStatus.CHECKEDIN)
            roomSystem.setOccupied(prev);
        return success;
    }

    /**
     * Cancels a booking.
     * @param b Booking to cancel.
     * @return Whether the booking could be cancelled or not.
     */
    public boolean cancelBooking(Booking b) {
        boolean success = bookingSystem.cancelBooking(b);
        roomSystem.setAvailable(b.getRoom());
        return success;
    }

    /**
     * The name hopefully implies a lot. I want to add check in by name or phone add some point. Stay tuned.
     * @param confirmationNumber Confirmation number of the booking to check in.
     * @return Whether the check in was successful or not.
     */
    public boolean checkInByNumber(int confirmationNumber) {
        boolean success = bookingSystem.checkIn(confirmationNumber);
        roomSystem.save();
        return success;
    }

    /**
     * Updates a Booking with the provided start and end dates.
     * @param b Booking to update.
     * @param start Updated Starting date for Booking.
     * @param end Updated Ending date for Booking.
     * @return true if operation was successful, false if it failed.
     */
    public boolean updateBooking(Booking b, Calendar start, Calendar end) {
        if (roomSystem.isRoomAvailable(b.getRoom(), bookingSystem.getBookings(), start, end) == false)
            return false;
        return bookingSystem.updateBooking(b, start, end);
    }

    /**
     * Method to get a list of bookings that the guest has that are currently waiting to be checked in.
     * @param phoneOrName Phone or Name of guest.
     * @return List of awaiting guest bookings.
     */
    public List<Booking> getConfirmedBookingsByGuestNameOrPhone(String phoneOrName) {
        Guest g = guestSystem.findGuestByPhoneOrName(phoneOrName);
        if (Objects.isNull(g)) {
            return null; //Guest was not found
        }
        return bookingSystem.getConfirmedBookingsByGuest(g);
    }

    public List<Booking> getBookingByGuest(Guest guest) {
        return bookingSystem.getBookingsForGuest(guest);
    }

    public List<Booking> getConfirmedBookings() {
        return bookingSystem.getConfirmedBookings();
    }

    public Booking getBookingByConfirmation(int confirmation) {
        return bookingSystem.findBookingByConfirmation(confirmation);
    }

    public boolean validateBooking(int confirmation) {
        return bookingSystem.validateBooking(confirmation);
    }

    //MAINTENANCE METHODS

    /**
     * Creates an in-stay maintenance ticket for the specific room with a description and severity.
     * @param roomNumber Number of the room to be maintained.
     * @param description Description of maintenance.
     * @param severity Description of severity.
     * @return Whether the ticket was able to be created or not.
     */
    public MaintenanceTicket createTicket(int roomNumber, String description, String severity) {
        Room r = roomSystem.findRoomByNumber(roomNumber);
        if (Objects.isNull(r)) {
            return null; //Room does not exist.
        }
        return maintenanceSystem.createTicket(r, description, severity);
    }

    /**
     * Method to resolve maintenance tickets. Also handles setting room status once they are resolved instead of maintenance system doing it.
     * @param ticketID ID# of ticket to resolve.
     * @return Whether the ticket was successfully resolved or not.
     */
    public boolean resolveTicket(int ticketID) {
        if (maintenanceSystem.resolveTicket(ticketID)) {
            Room r = maintenanceSystem.getTicketByID(ticketID).getRoom();
            if (roomSystem.isRoomCheckedIn(r, bookingSystem.getBookings())) {
                r.setStatus(Status.OCCUPIED);
            }
            else {
                r.setStatus(Status.AVAILABLE);
            }
            return true;
        }
        return false;
    }

    //PAYMENT METHODS

    public boolean checkout(int confirmation) {
        Booking b = bookingSystem.findBookingByConfirmation(confirmation);
        if (Objects.isNull(b)) {
            return false;
        }
        boolean success = paymentSystem.checkoutAndPay(b, b.getRoom());
        roomSystem.setCleaning(b.getRoom());
        return success;
    }
}