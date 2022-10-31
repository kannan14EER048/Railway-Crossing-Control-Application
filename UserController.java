package controller;

import dao.UserDAO;
import service.UserServiceImpl;

public class UserController {								//controls all user-related operations
															//Control will be given to UserServiceImpl class
	UserDAO user = new UserDAO();
	UserServiceImpl us = new UserServiceImpl();

	public void createAccount(int userType) {
		 us.addUser(userType);
	}

	public boolean loginCheck(int userType) throws Exception {

		return us.isAccountExist(userType);
	}

}
