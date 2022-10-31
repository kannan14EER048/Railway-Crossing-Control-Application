package model;

import java.util.HashMap;

/****** creates a railway crossing object when called ******/
public class RailwayCrossing {
	
	private String name;
	private Address address;
	private User inchargeName;
	private boolean currentStatus;
	private HashMap<Integer, String> timings;
	private boolean isOpened; 												// default status

	public RailwayCrossing() {												//default constructor

	}

	public RailwayCrossing(String name, Address address, User inchargeName, boolean currentStatus,
			HashMap<Integer, String> timings, boolean isOpened) {
		super();
		this.name = name;
		this.address = address;
		this.inchargeName = inchargeName;
		this.currentStatus = currentStatus;
		this.timings = timings;
		this.isOpened = isOpened;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getInchargeName() {
		return inchargeName;
	}

	public void setInchargeName(User inchargeName) {
		this.inchargeName = inchargeName;
	}

	public boolean isCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(boolean currentStatus) {
		this.currentStatus = currentStatus;
	}

	public HashMap<Integer, String> getTimings() {
		return timings;
	}

	public void setTimings(HashMap<Integer, String> timings) {
		this.timings = timings;
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

	@Override
	public String toString() {
		return "RailwayCrossing [name=" + name + ", address=" + address + ", inchargeName=" + inchargeName
				+ ", currentStatus=" + currentStatus + ", timings=" + timings + ", isOpened=" + isOpened + "]";
	}

}
