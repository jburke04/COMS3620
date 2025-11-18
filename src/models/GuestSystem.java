package models;

import helpers.Parser;

import java.io.OutputStream;
import java.util.*;

public class GuestSystem {
    private final List<Guest> guests = new ArrayList<>();
    private final String guestPath = "src/assets/Guests.json";

    public GuestSystem() {
        Parser.parseGuests(guestPath, guests);
    }

    public void saveGuests() {
        Parser.saveGuests(guestPath, guests);
    }

    /**
     * Finds a Guest by Phone Number or Name
     * @param phoneOrName the String to search the Guest list for. Can be either a phone number or name, it will work with either. BUT NOT BOTH.
     * @return A Guest object with the name/phone number.
     */
    public Guest findGuestByPhoneOrName(String phoneOrName) {
        for (Guest g : guests) {
            if (g.getPhoneNumber().equalsIgnoreCase(phoneOrName) || g.getName().equalsIgnoreCase(phoneOrName)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Since I only want this class to see the full guest list, this method will get called straight from HotelSystem. Hiding all I can.
     * I'm aware that printing in this class is probably taboo. This is how it will work for now, feel free to find a way
     * to do this that maintains the information hiding and also doesn't directly print to console. Maybe writing to an IO stream?
     */
    public void listGuests() {
        for (Guest g : guests) {
            System.out.println(g.getName()+", #"+g.getGuestId()+", Phone: "+g.getPhoneNumber()+", Email:"+g.getEmail());
        }
    }

    public List<Guest> getGuests() {
        return guests;
    }


}