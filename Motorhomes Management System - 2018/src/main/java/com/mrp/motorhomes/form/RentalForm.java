package com.mrp.motorhomes.form;

import com.mrp.motorhomes.model.Accessory;
import com.mrp.motorhomes.model.Rental;
import com.mrp.motorhomes.repositories.CustomerRepository;
import com.mrp.motorhomes.repositories.MotorhomeRepository;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;

//#Marius
//Form submitted when adding a new Rental to the system
public class RentalForm implements Form {
	private int customerId;
	private int motorhomeId;
	protected double price;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate startDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate endDate;
	private String pickUp;
	private String dropOff;
	private String[] accessories;
	
	//validates the fields entered by the user
	@Override
	public boolean validate(){
		//checks if the customer and the motorhome are in the database
		boolean customerId 	= CustomerRepository.getInstance().read(this.customerId) != null;
		boolean motorhomeId = MotorhomeRepository.getInstance().read(this.motorhomeId) != null;
		boolean price 		= this.price > 0;
		boolean startDate 	= this.startDate != null && this.startDate.isAfter(LocalDate.now());
		boolean endDate 	= startDate && this.endDate != null  && this.endDate.isAfter(this.startDate);
		boolean pickUp 		= this.pickUp != null && this.pickUp.length() > 0;
		boolean dropOff 	= this.dropOff != null && this.dropOff.length() > 0;
		return customerId && motorhomeId && price && startDate && endDate && pickUp && dropOff;
	}
	
	@Override
	//Returns a new Rental based on the fields of the form
	public Rental toModel(){
		ArrayList<Accessory> accessories = new ArrayList<>();
		for(String accessory: this.accessories) {
			accessories.add(new Accessory(accessory));
		}
		return new Rental(customerId, motorhomeId, price, startDate,
		                  endDate, pickUp, dropOff, false, accessories);
	}
	
	//prints the form to the console, used for debugging
	@Override
	public String toString() {
		return "RentalForm{" +
					   "customerId=" + customerId +
					   ", motorhomeId=" + motorhomeId +
					   ", price=" + price +
					   ", startDate=" + startDate +
					   ", endDate=" + endDate +
					   ", pickUp='" + pickUp + '\'' +
					   ", dropOff='" + dropOff + '\'' +
					   '}';
	}
	
	
	///Getters and Setters
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public int getMotorhomeId() {
		return motorhomeId;
	}
	
	public void setMotorhomeId(int motorhomeId) {
		this.motorhomeId = motorhomeId;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	public String getPickUp() {
		return pickUp;
	}
	
	public void setPickUp(String pickUp) {
		this.pickUp = pickUp;
	}
	
	public String getDropOff() {
		return dropOff;
	}
	
	public void setDropOff(String dropOff) {
		this.dropOff = dropOff;
	}
	
	public String[] getAccessories() {
		return accessories;
	}
	
	public void setAccessories(String[] accessories) {
		this.accessories = accessories;
	}
}
