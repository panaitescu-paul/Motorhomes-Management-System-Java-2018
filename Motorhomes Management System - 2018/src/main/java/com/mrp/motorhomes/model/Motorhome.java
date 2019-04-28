package com.mrp.motorhomes.model;

//#Constantin
//Representation of a motorhome registered in the database of the system
public class Motorhome {
	//All types of motorhomes
	public static final String[] TYPES = {"Class A", "Class B", "Class C", "Truck Camper", "Popup Camper", "Travel Trailer", "Teardrop Trailer", "Hybrid Trailer"};
	
	private int id;
	private String brand;
    private String type;
	private String model;
	private int year;
	private double basePrice;
    private boolean cleaned;
    private boolean serviced;

	public Motorhome(int id, String brand, String type, String model, int year, double basePrice, boolean cleaned,
	                 boolean serviced) {
		this.id = id;
		this.brand = brand;
		this.type = type;
		this.model = model;
		this.year = year;
		this.basePrice = basePrice;
		this.cleaned = cleaned;
		this.serviced = serviced;
	}

    public Motorhome() {}
    
    public void prepareForRental(){
		cleaned = false;
		serviced = false;
    }

    //Getters and Setters
    public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
    public double getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public String getFullName(){
		return brand + " " + model;
	}

    public boolean isCleaned() {
        return cleaned;
    }

    public void setCleaned(boolean cleaned) {
        this.cleaned = cleaned;
    }

    public boolean isServiced() {
        return serviced;
    }

    public void setServiced(boolean serviced) {
        this.serviced = serviced;
    }

}