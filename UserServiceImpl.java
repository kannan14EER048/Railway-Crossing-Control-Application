package service;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.UserDAO;
import model.Address;
import model.User;

//All the operations related to users have been implemented here. 
//operations involving database are forwarded to UserDAO class
public class UserServiceImpl implements UserService {

	User user;
	UserDAO udao = new UserDAO();

	Scanner sc = new Scanner(System.in);

	public void addUser(int userType) {										//getting new user details
		Address userAddress = new Address();
		user = new User();
		try {
			System.out.println("\nEnter your detaile here ");
			System.out.print("\nName : ");
			user.setName(sc.nextLine());
			
			//email address validation
			while(true) {
				System.out.print("\nEmail : ");
				String email = sc.nextLine().toLowerCase();
				if(isEmailValid(email)==true) {
					user.setEmail(email);
					System.out.println("\nEmail has been set successfully...");
					break;
				}
				else {
					System.err.println("\nInvalid email!");
				}
			}
			
			System.out.print("\nPhone : ");
			user.setPhone(sc.nextLine());

			System.out.print("\nNew password : ");
			String password = sc.nextLine();
			while (true) {
				System.out.print("\nConfirm password : ");
				if (password.equals(sc.nextLine())) {
					System.out.println("\nPassword has been set successfully!!!");
					break;
				} else {
					System.out.println("\nPassword doesn't match!");
				}
			}
			user.setPassword(password);
			System.out.println("\nAddress details ");
			System.out.print("\nBuildingNumber : ");
			userAddress.setBuildingNo(sc.nextLine());
			System.out.print("\nStreet : ");
			userAddress.setStreet(sc.nextLine());
			System.out.print("\nCity : ");
			userAddress.setCity(sc.nextLine());
			user.setAddress(userAddress);

			try {
				udao.createAccount(user, userType);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (InputMismatchException ime) {
			System.out.println("\nInvalid input");
		}

	}

	public boolean isAccountExist(int userType) {							//checks if the account exist
		boolean out = false;
		System.out.print("\nEmail address :");
		String email = sc.next().toLowerCase(); 							// email will be stored in lower cases
		System.out.print("\nPassword : ");
		String password = sc.next();
		try {
			out = udao.isAccountExist(userType, email, password);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	public boolean isEmailValid(String email) {								//checks if email is valid
		String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";	//RFC5322 standard for email
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		
	}

}
