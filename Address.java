package model;

/****** creates address object for user and railway crossing *****/
public class Address {

	private String buildingNo;
	private String street;
	private String city;

	public Address() {											//default constructor

	}

	public Address(String buildingNo, String street, String city) {
		super();
		this.buildingNo = buildingNo;
		this.street = street;
		this.city = city;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "Address [buildingNo=" + buildingNo + ", street=" + street + ", city=" + city + "]";
	}

}
