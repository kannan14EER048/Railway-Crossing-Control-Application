package service;

public interface UserService {

	public void addUser(int userType);
	public boolean isAccountExist(int userType) throws Exception;  //login
	
}
