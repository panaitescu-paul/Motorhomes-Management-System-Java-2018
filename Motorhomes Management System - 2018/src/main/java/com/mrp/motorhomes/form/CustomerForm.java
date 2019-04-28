package com.mrp.motorhomes.form;

import com.mrp.motorhomes.model.Customer;

//#Marius
//Form submitted when adding a new Customer to the system
public class CustomerForm implements Form<Customer> {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String ssn;
    
    //validates the fields entered by the user
    @Override
    public boolean validate(){
        boolean firstName 	= this.firstName != null && this.firstName.length() > 0;
        boolean lastName 	= this.lastName != null && this.lastName.length() > 0;
        boolean email 		= checkEmail(this.email);
        boolean address 	= this.address != null && this.address.length() > 0;
        //the regular expressions check if the String contains only digits
        boolean phone 		= this.phone != null && this.phone.length() == 8 && this.phone.matches("[0-9]+");
        boolean ssn 		= this.ssn != null && this.ssn.length() == 10 && this.ssn.matches("[0-9]+");
        return firstName && lastName && email && address && phone && ssn;
    }
    
    @Override
    //Returns a new Customer based on the fields of the form
    public Customer toModel() {
        return new Customer(-1, firstName, lastName, email, address, phone, ssn);
    }
    
    //prints the form to the console, used for debugging
    @Override
    public String toString() {
        return "CustomerForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", ssn='" + ssn + '\'' +
                '}';
    }
    
    //checks if the email has a valid format
    private static boolean checkEmail(String email){
        return email != null && email.length() > 0 && email.contains ("@") && (email.contains(".com")
            || email.contains(".dk"));
    }
    
    ///Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getSsn() {
        return ssn;
    }
    
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
