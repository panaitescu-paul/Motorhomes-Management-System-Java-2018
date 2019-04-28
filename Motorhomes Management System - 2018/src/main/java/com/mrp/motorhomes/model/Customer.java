package com.mrp.motorhomes.model;

//#Marius
//Representation of a customer registered in the database of the system
public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String phone;
	private String ssn;
	
	public Customer() {}
	
	public Customer(int id, String firstName, String lastName, String email, String address, String phone,
					String ssn) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.ssn = ssn;
	}
	
	//Getters and Setters
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSsn() {
		return this.ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	public String getFullName(){
		return firstName + " " + lastName;
	}

}