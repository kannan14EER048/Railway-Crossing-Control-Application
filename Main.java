/***************************************** Application Name : Railway crossing control system ***************************************/
/* A user can see get the railway crossing status based on time ,  
 * train details and contact details of the railway crossing (or) station **/
/************************************************************************************************************************************/

package main;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.RailwayCrossingController;
import controller.UserController;

public class Main extends Thread {  											//Application supports multiple users

	static Scanner sc = new Scanner(System.in);

	public static void displayTitle() {											//opening message

		System.out.println("********* Welcome to Indian Railways *********");
		System.out.println("******* Railway crossing control system ******");
	}

	public static void endApplication() {
		System.out.println("\\n******************** Thank you, have a nice day! **********************");
	} 

	
	public static boolean registerOrLogin(int userType) throws Exception {		//Registers new user/administrator
																				//also checks if user has an account already
		UserController uc = new UserController();
		System.out.println("\n1) Create your account");
		System.out.println("\n2) Login");
		System.out.print("\nYour choice : ");
		int choice = 0;
		try {
			choice = sc.nextInt();
		} catch (InputMismatchException ime) {
			System.out.println("Enter 1 or 2");
		}
		if (choice == 1) {
			uc.createAccount(userType);
			return true;
		} else if (choice == 2) {
			return uc.loginCheck(userType);
		} else
			return false;

	}

	public static void main(String[] args) throws IOException {
		
		Main mainObj = new Main();
		mainObj.start();														//Shows menu for the user -> user can be an administrator or public 

		RailwayCrossingController rc = new RailwayCrossingController();
		displayTitle();
		System.out.println("\n1)Public");
		System.out.println("\n2)Government");
		System.out.println("\n3)Exit");											//User can exit if they don't want to use the application
		System.out.print("\nYour choice : ");
		int choice;
		int userType = 0;
		try {
			userType = sc.nextInt();
			if (userType == 3) {

				System.out.println("\n******************** Thank you, have a nice day! **********************");
				return;
			}
		} catch (InputMismatchException imme) {
			System.err.println("\nEnter 1 or 2");
		}

		boolean isLoggedIn = false;
		
		while (!isLoggedIn) {													//Asks user to enter credentials again if they are wrong
			try {
				isLoggedIn = registerOrLogin(userType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!isLoggedIn) {
				System.err.println("\nInvalid credentials");
				System.out.println("\nIf you do not have an account, please sign up!");

			} else {

				System.out.println("\n------Hello there, you are logged in now!!------");
				break;
			}

		}
		if ((userType == 1) && isLoggedIn) {									//Menu for a public user 
			while (true) {
				System.out.println("\n1) See railway crossing status");
				System.out.println("\n2) search for a railway gate");
				System.out.println("\n3) Sort based on trains");
				System.out.print("\nYour choice : ");
				try {
					choice = sc.nextInt();
					switch (choice) {
					case 1:
						rc.displayData();
						break;
					case 2:
						rc.searchRc();
						break;
					case 3:
						rc.sortRc();
						break;

					}

				} catch (InputMismatchException ime) {
					System.err.println("Please enter a valid input");
				}
				System.out.print("\n\nWould you like to continue? (y / n) : ");
				if (sc.next().charAt(0) == 'n') {
					endApplication();
					return;
				}

			}

		}

		if ((userType == 2) && isLoggedIn) {										//Menu for Administrator

			while (true) {
				System.out.println("\nWhat would you like to do? ");
				System.out.println("\n1) Add Railway Crossing to Database");
				System.out.println("\n2) Update status of Railway Crossing");
				System.out.println("\n3) Delete Railway Crossing");

				try {
					System.out.print("\nYour choice : ");
					choice = sc.nextInt();
					switch (choice) {
					case 1:
						rc.addRailwayCross();
						break;
					case 2:
						rc.updateRc();
						break;
					case 3:
						rc.deleteRc();
						break;
					}

				} catch (InputMismatchException ime) {
					System.err.println("\nPlease enter a valid input");
				}

				System.out.print("\n\nWould you like to continue? (y / n) : ");
				if (sc.next().charAt(0) == 'n') {
					endApplication();
					return;
				}

			}
		}

	}

}
