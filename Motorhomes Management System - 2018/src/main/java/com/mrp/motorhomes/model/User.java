package com.mrp.motorhomes.model;

//#Razvan
//Class representing the user of the system
public class User {
	
	//All types of users
	public static final String[] TYPES = {"Sales Assistant", "Auto Mechanic", "Bookkeeper", "Cleaning Staff"};
	//All users registered in the system
	public static final User[] ALL_USERS = {
			new User("Dave", TYPES[0]),
			new User("Andrew", TYPES[0]),
			new User("Matthias", TYPES[0]),
			new User("Erik", TYPES[0]),
			new User("Ionel", TYPES[1]),
			new User("Jones", TYPES[2]),
			new User("Valentina", TYPES[3]),
			new User("Joseph", TYPES[3])
	};
	private String name;
	private String type;
	
	private User(String name, String type){
		this.name = name;
		this.type = type;
	}
	
	//prints to the console, used for debugging
	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", type='" + type + '\'' +
				'}';
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
}