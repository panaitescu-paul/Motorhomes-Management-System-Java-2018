package com.mrp.motorhomes.form;

import com.mrp.motorhomes.model.Motorhome;

//#Marius
//Form submitted when adding a new Motorhome to the system
public class MotorhomeForm implements Form {
    private static final int MIN_YEAR = 1950;
    private static final int MAX_YEAR = 2018;
    
    private String brand;
    private String type;
    private String model;
    private int year;
    private double basePrice;
    
    //validates the fields entered by the user
    @Override
    public boolean validate() {
        boolean brand 	    = this.brand != null && this.brand.length() > 0;
        boolean type        = this.type != null && this.type.length() > 0;
        boolean model 		= this.model != null && this.model.length() > 0;
        boolean year        = this.year >= MIN_YEAR && this.year <= MAX_YEAR;
        boolean basePrice   = this.basePrice > 0;
        return brand && type && model && year && basePrice;
    }
    
    @Override
    //Returns a new Motorhome based on the fields of the form
    public Motorhome toModel(){
        return new Motorhome(-1, this.brand, this.type, this.model, this.year, this.basePrice, false, false);
    }
    
    //prints the form to the console, used for debugging
    @Override
    public String toString() {
        return "MotorhomeForm{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", basePrice=" + basePrice +
                '}';
    }
    
    ///Getters and Setters
    public static int getMinYear() {
        return MIN_YEAR;
    }
    
    public static int getMaxYear() {
        return MAX_YEAR;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public double getBasePrice() {
        return basePrice;
    }
    
    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }
}
