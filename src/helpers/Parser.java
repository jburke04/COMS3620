package src.helpers;

import src.models.Booking;
import src.models.Guest;
import src.models.Room;

import java.io.FileReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

public class Parser {

    private static JSONParser parser = new JSONParser();

    public static void parseRooms(String filepath, ArrayList<Room> rooms) {

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader(filepath));
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }

        for (Object o : a)
        {
            JSONObject person = (JSONObject) o;

            String name = (String) person.get("name");
            System.out.println(name);

            String city = (String) person.get("city");
            System.out.println(city);

            String job = (String) person.get("job");
            System.out.println(job);

            JSONArray cars = (JSONArray) person.get("cars");

            for (Object c : cars)
            {
                System.out.println(c+"");
            }
        }
    }

    public static void parseBookings(String filepath, ArrayList<Booking> bookings) {

    }

    public static void parseGuests(String filepath, ArrayList<Guest> guests) {

    }
}
