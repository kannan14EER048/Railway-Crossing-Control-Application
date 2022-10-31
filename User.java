package model;

/*****used to create a user -> public, administrator or railway crossing in-charge*****/
public class User {

	private String name;
	private String email;
	private String password;
	private String phone;
	private Address address;

	public User() {														//default constructor

	}

	public User(String name, String email, String password, String phone, Address address) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone + ", address="
				+ address + "]";
	}

}
