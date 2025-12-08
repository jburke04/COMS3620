package models;

import helpers.Parser;
import java.util.*;

/**
 * Guest System that handles all operations involving Guest management
 * for the Hotel.
 */
public class GuestSystem implements SubSystem {
    private final List<Guest> guests = new ArrayList<>();
    private final String guestPath = "src/assets/Guests.json";

    /**
     * Loads data from JSON file of Guests
     */
    @Override
    public void load() {
        Parser.parseGuests(this.guestPath, this.guests);
    }

    /**
     * Saves data to JSON file of Guests
     */
    @Override
    public void save() {
        Parser.saveGuests(this.guestPath, this.guests);
    }

    /**
     * Finds a Guest by Phone Number or Name
     * @param phoneOrName the String to search the Guest list for. Can be either a phone number or name, it will work with either. BUT NOT BOTH.
     * @return A Guest object with the name/phone number.
     */
    public Guest findGuestByPhoneOrName(String phoneOrName) {
        // compare provided String:
        for (Guest g : this.guests) {
            // match found:
            if (g.getPhoneNumber().equalsIgnoreCase(phoneOrName) || g.getName().equalsIgnoreCase(phoneOrName)) {
                return g;
            }
        }

        // no matching guest, return null:
        return null;
    }

    /**
     * Find a Guest by the provided ID.
     * @param id Guest ID to search for.
     * @return Guest with corresponding Guest ID.
     */
    public Guest findGuestByID(int id) {
        // compare all guests in the list:
        for (Guest g : this.guests) {
            if (g.getGuestId() == id)
            return g;
        }

        // no matching guest, return null:
        return null;
    }

    /**
     * Since I only want this class to see the full guest list, this method will get called straight from HotelSystem. Hiding all I can.
     * I'm aware that printing in this class is probably taboo. This is how it will work for now, feel free to find a way
     * to do this that maintains the information hiding and also doesn't directly print to console. Maybe writing to an IO stream?
     */
    public void listGuests() {
        for (Guest g : this.guests) {
            System.out.println(g.toString());
        }
    }

    /**
     * Gets the list of Guests in this Guest System
     * @return list of Guests
     */
    public List<Guest> getGuests() {
        return this.guests;
    }

    /**
     * Adds Guest to the system
     * @param guest Guest to add
     */
    public void addGuest(Guest guest) {
        this.guests.add(guest);
        this.save();
    }


}