package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import model.User;

/************************** This has all the user related operations **************/
public class UserDAO {

	Scanner sc = new Scanner(System.in);

	//add new user to the database
	public void addNewUser(User user, BufferedWriter userBuffer, BufferedWriter credentials, FileWriter userDetails,
			FileWriter logFile) throws IOException {
		userBuffer.append(user.getEmail() + ":");
		userBuffer.append(user.getPassword() + ":");
		userBuffer.append(user.getName() + ":");
		userBuffer.append(user.getPhone() + ":");
		userBuffer.append(user.getAddress().getBuildingNo() + ":");
		userBuffer.append(user.getAddress().getStreet() + ":");
		userBuffer.append(user.getAddress().getCity());
		userBuffer.newLine();

		credentials.append(user.getEmail() + ":" + user.getPassword() + "\n");

		System.out.println("\nAccount created!!!");

	}

	//it creates new account for the user with their details and saves to the database
	public void createAccount(User user, int userType) throws IOException {
		UserDAO udao = new UserDAO();
		String detailsFile = "";
		String credentialsFile = "";
		if (userType == 1) {
			detailsFile = "Public_Users.txt";
			credentialsFile = "Public_User_Credentials.txt";
		}
		if (userType == 2) {
			detailsFile = "Admin.txt";
			credentialsFile = "Admin_Credentials.txt";
		}

		FileWriter userDetails = new FileWriter(detailsFile, true); 
		FileWriter logFile = new FileWriter(credentialsFile, true);
		BufferedWriter userBuffer = new BufferedWriter(userDetails);
		BufferedWriter credentials = new BufferedWriter(logFile);
		udao.addNewUser(user, userBuffer, credentials, userDetails, logFile);
		credentials.close();
		userBuffer.close();
		logFile.close();
		userDetails.close();
	}
	
	//checks if the account is already exist 
	public boolean isAccountExist(int userType, String email, String password) throws IOException {
		boolean isAccountExist = false;
		String crdentialsFile = "";
		if (userType == 1) {
			crdentialsFile = "Public_User_Credentials.txt";
		}
		if (userType == 2) {
			crdentialsFile = "Admin_Credentials.txt";
		}
		FileReader logCheck = new FileReader(crdentialsFile);
		BufferedReader loginCheck = new BufferedReader(logCheck);
		isAccountExist = isUserValid(userType, email, password, logCheck, loginCheck);
		loginCheck.close();
		logCheck.close();
		return isAccountExist;
	}
	
	//authentication the user with their user name and password
	public boolean isUserValid(int userType, String email, String password, FileReader logCheck,
			BufferedReader loginCheck) throws IOException {
		boolean isAccountPresent = false;
		String login = "";
		while ((login = loginCheck.readLine()) != null) {
			if (login.equals(email + ":" + password)) {
				isAccountPresent = true;
				break;
			}
		}
		return isAccountPresent;
	}

	
}
