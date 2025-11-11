//package src;
//import java.util.Scanner;

//public class Main {
//	public static void main(String[] args) {
//		boolean close = false;
//		Scanner input = new Scanner(System.in);
//		while (!close) {
//			System.out.println("[C]ustomer Services | [E]mployee Services | [Q]uit");
//			switch(input.nextLine().toLowerCase()) { //toLowerCase() will catch if they don't input strictly uppercase letters.
//			case "c":
//				customersTab(input);
//				break;
//			case "e":
//				employeesTab(input);
//				break;
//			case "q":
//				close = true;
//				break;
//			}
//		}
//		input.close();
//		System.exit(1);
//	}
//	//These functions down here are for code cleanliness: I do not want to have to read through 500 nested if-else statements
//	private static void customersTab(Scanner input) {
//		boolean back = false;
//		while (!back) {
//			System.out.println("[B]ook Room | [U]pdate Booking | [C]ancel Booking | Check [I]n | Check [O]ut | C[H]ange Room | [R]oom Service | [Q]uit menu");
//			switch(input.nextLine().toLowerCase()) {
//			case "b":
//				//TODO
//				break;
//			case "u":
//				//TODO
//				break;
//			case "c":
//				Cancel.start(input, null, null);
//				break;
//			case "i":
//				//TODO
//				break;
//			case "o":
//				checkout.start(input);
//				break;
//			case "h":
//				//TODO
//				break;
//			case "r":
//				//TODO
//				break;
//			case "q":
//				back = true;
//				break;
//			}
//		}
//	}
//
//	private static void employeesTab(Scanner input) {
//
//	}
//}

package src;

import src.models.BookingSystem;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		BookingSystem system = new BookingSystem();
		system.loadAll();

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("\n=== HOTEL SYSTEM DEMO ===");
			System.out.println("1) Book a Room");
			System.out.println("2) Cancel a Booking");
			System.out.println("3) Change Room");
			System.out.println("4) In-Stay Maintenance");
			System.out.println("5) Check-In (mark booking as CHECKEDIN)");
			System.out.println("6) Checkout & Payment");
			System.out.println("0) Exit");
			System.out.print("Choose: ");
			String choice = sc.nextLine().trim();
			switch (choice) {
				case "1": BookRoom.start(sc, system); break;
				case "2": Cancel.start(sc, system); break;
				case "3": ChangeRoom.start(sc, system); break;
				case "4": InStayMaintenance.start(sc, system); break;
				case "5":
					System.out.print("Enter confirmation #: ");
					int c = Integer.parseInt(sc.nextLine().trim());
					boolean ok = system.checkIn(c);
					System.out.println(ok ? "Checked in." : "Failed to check in.");
					break;
				case "6": checkout.start(sc, system); break;
				case "0": sc.close(); return;
				default: System.out.println("Invalid.");
			}
		}
	}
}

