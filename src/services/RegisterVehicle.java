package services;
import models.Guest;
import models.HotelSystem;
import java.util.Scanner;

public class RegisterVehicle {

    public static void start(Scanner scanner, HotelSystem system) {
        System.out.println("=== Vehicle Registration ===");
        System.out.println("Enter guest's name:");
        Guest guest = null;
        while(true){
            String name = scanner.nextLine().trim();
            guest = system.findGuestByPhoneOrName(name);

            if (guest == null) {
                System.out.println("Guest not found.");
                continue;
            }else{
                break;
            }
        }


        System.out.println("Enter vehicle license plate:");
        String plate = scanner.nextLine().trim();

        system.registerVehicle(guest,plate);
        System.out.println("Vehicle registered successfully for: " + guest.getName());
        System.out.println("Plate on file: " + plate);
    }
}