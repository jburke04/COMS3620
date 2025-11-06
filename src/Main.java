package src;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		boolean close = false;
		Scanner input = new Scanner(System.in);
		while (!close) {
			System.out.println("[C]ustomer Services | [E]mployee Services | [Q]uit");
			switch(input.nextLine().toLowerCase()) { //toLowerCase() will catch if they don't input strictly uppercase letters.
			case "c":
				customersTab(input);
				break;
			case "e":
				employeesTab(input);
				break;
			case "q":
				close = true;
				break;
			}
		}
		input.close();
		System.exit(1);
	}
	//These functions down here are for code cleanliness: I do not want to have to read through 500 nested if-else statements
	private static void customersTab(Scanner input) {
		boolean back = false;
		while (!back) {
			System.out.println("[B]ook Room | [U]pdate Booking | [C]ancel Booking | Check [I]n | Check [O]ut | C[H]ange Room | [R]oom Service | [Q]uit menu");
			switch(input.nextLine().toLowerCase()) {
			case "b":
				//TODO
				break;
			case "u":
				//TODO
				break;
			case "c":
				//TODO
				break;
			case "i":
				//TODO
				break;
			case "o":
				//TODO
				break;
			case "h":
				//TODO
				break;
			case "r":
				//TODO
				break;
			case "q":
				back = true;
				break;
			}
		}
	}
	
	private static void employeesTab(Scanner input) {
		
	}
}