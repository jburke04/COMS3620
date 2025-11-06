package src;

import java.util.Scanner;



public class Main {
	private static void exampleBookingScreen(int input){
		System.out.println("Yay Booking");
	}
	private static void exampleMaintenanceScreen(int input){
		System.out.println("Yay Maintenance");
	}
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Basic Functionality (press 0 to exit)");

		while (true) {
			System.out.print("1: Booking\n2:Maintenance\n");
			String line = scanner.nextLine();
			line = line.trim();

			
			// Validate: must be exactly one character and a digit 0-9
			if (line.length() != 1 || !Character.isDigit(line.charAt(0))) {
				System.out.println("Invalid input. Please press a single number key (0-9).\n");
				continue;
			}

			int digit = Character.getNumericValue(line.charAt(0));

			if (digit == 0) {
				System.out.println("Received 0 — exiting loop.");
				break;
			} else if (digit == 1){
				exampleBookingScreen(digit);
			} else if (digit == 2){
				exampleMaintenanceScreen(digit);
			}else {
				System.out.println("Invalid input. Select Booking or Maintenance.\n");
			}
		}

		scanner.close();
	}
}