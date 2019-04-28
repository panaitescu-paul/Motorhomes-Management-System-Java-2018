package com.mrp.motorhomes.model;

//#Paul
//Representation of an accessory registered in the database of the system
public class Accessory {
	
	//A String array containing the possible types of accessories
	public static final String[] ALL_ACCESSORIES = {
			"Bike rack", "Bed linen", "Picnic table and chairs", "Child seat", "TV", "Fishing tools"};

	private String title;
	private int rentalId;
	
	public Accessory(String title, int rentalId) {
		this.title = title;
		this.rentalId = rentalId;
	}
	
	public Accessory(String title) {
		this.title = title;
	}
	
	//prints the model to the console, used for debugging
	@Override
	public String toString() {
		return "Accessory{" +
				"title='" + title + '\'' +
				'}';
	}
	
	//Getters and Setters
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getRentalId() {
		return rentalId;
	}
	
	public void setRentalId(int rentalId) {
		this.rentalId = rentalId;
	}
}