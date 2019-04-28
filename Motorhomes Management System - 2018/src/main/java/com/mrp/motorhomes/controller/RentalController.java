package com.mrp.motorhomes.controller;

import com.mrp.motorhomes.form.RentalForm;
import com.mrp.motorhomes.model.Accessory;
import com.mrp.motorhomes.model.Motorhome;
import com.mrp.motorhomes.model.Rental;
import com.mrp.motorhomes.repositories.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static com.mrp.motorhomes.controller.MainController.currentUser;

//#Paul
//Controller responsible for handling operations on the rentals
@Controller
public class RentalController {

	
	private CrudRepository<Rental> rentalCrudRepository;
	private CrudRepository<Motorhome> motorhomeCrudRepository;
	
	public RentalController() {
		//grabs the instances of the rental and the motorhome database repositories
		rentalCrudRepository = RentalRepository.getInstance();
		motorhomeCrudRepository = MotorhomeRepository.getInstance();
	}
	
	//main page for the rentals, showing a table with all the rentals
	@GetMapping("/rentals")
	public String index(Model model) {
		//adds the list of active rentals to the Model
		model.addAttribute("rentals", getActiveRentals());
		//adds the current user to the Model
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("readsAll", false);
		return "rentals/index";
	}
	
	//Shows the page to create a new rental
	@GetMapping("/rentals/create")
	public String create(Model model) {
		//Creates and passes a new rental form to the Model
		model.addAttribute("rentalForm", new RentalForm());
		//adds multiple attributes to the model
		prepareForm(model);
		return "rentals/create";
	}
	
	//Processes the form
	@PostMapping("/rentals/create")
	public String create(Model model, @ModelAttribute("rentalForm") RentalForm rentalForm) {
		System.out.println(rentalForm);
		System.out.println(Arrays.toString(rentalForm.getAccessories()));
		
		//validates the fields entered by the user
		if(!rentalForm.validate()){
			//some field were invalid, show an error message
			model.addAttribute("errorMessage", MainController.ERROR_MESSAGE + " " + MainController.ERROR_RENTAL);
			//adds multiple attributes to the model
			prepareForm(model);
			//redirect to the creation page
			return "rentals/create";
		}
		
		//all field were valid, convert the form object to Rental
		Rental rental = rentalForm.toModel();
		
		//add the rental to the database, set the id to the value returned by the repository
		//and update the "rentalId" field of the accessories with that value
		rental.setId(rentalCrudRepository.create(rental));
		
		if(rental.getId() == -1){
			//the rental was not recorded in the database, show an error message
			model.addAttribute("errorMessage", MainController.ERROR_MESSAGE);
			//adds multiple attributes to the model
			prepareForm(model);
			//redirects to the creation page
			return "rentals/create";
		}
		
		//gets the motorhome assigned to the rental
		Motorhome motorhome = motorhomeCrudRepository.read(rental.getMotorhomeId());
		//resets the cleaned and serviced attributes to false
		motorhome.prepareForRental();
		//updates the motorhome in the database
		motorhomeCrudRepository.update(motorhome);
		
		//adds the accessories to the database
		AccessoryRepository.getInstance().create(rental.getAccessories());
		
		//returns to the index page for rentals
		return "redirect:/rentals/";
	}
	
	//page that shows details for a rental
	@GetMapping("/rentals/details")
	public String details(@RequestParam("id") int id, Model model) {
		//reads the motorhome from the database and adds it to the Model
		Rental rental = rentalCrudRepository.read(id);
		System.out.println("details - rental " + rental);
		model.addAttribute("rental", rental);
		model.addAttribute("currentUser", currentUser);
		return "rentals/details";
	}
	
	//"pays" or "unpays" a rental
	@GetMapping("/rentals/pay")
	public String pay(@RequestParam("id") int id) {
		//reads the rental from the database
		Rental rental = rentalCrudRepository.read(id);
		//inverts the current paid state of the rental
		rental.setPaid(!rental.isPaid());
		//updates the rental in the database
		rentalCrudRepository.update(rental);
		return "redirect:/rentals/";
	}
	
	
	//page showing all the rentals ever recorded
	@GetMapping("/rentals/history")
	public String history(Model model) {
		//adds the list of all rentals to the Model
		model.addAttribute("rentals", rentalCrudRepository.readAll());
		model.addAttribute("currentUser", currentUser);
		//adds a "readsAll" attribute so the page does not show the "add new rental" button
		model.addAttribute("readsAll", true);
		return "rentals/index";
	}
	
	//**UNUSED**
	//updates a rental in the database
	@PostMapping("/rentals/details/update")
	public String update(@ModelAttribute Rental rental) {
		rentalCrudRepository.update(rental);
		return "redirect:/rentals/details/";
	}
	
	//deletes a rental from the database
	@GetMapping("/rentals/delete")
	public String delete(@RequestParam("id") int id) {
		//deletes the rental with the given id
		rentalCrudRepository.delete(id);
		return "redirect:/rentals/";
	}
	
	//returns a list of all rental currently active
	private ArrayList<Rental> getActiveRentals(){
		//gets all the rentals from the database
		ArrayList<Rental> rentals = rentalCrudRepository.readAll();
		//for each rental that is ended, remove it from the list
		//else, go to the next rental
		for(int i = 0; i < rentals.size();) {
			if(rentals.get(i).isEnded()) {
				rentals.remove(i);
			}
			else{
				i++;
			}
		}
		
		return rentals;
	}
	
	//returns a list of all motorhomes currently available
	private ArrayList<Motorhome> getAvailableMotorhomes(){
		//gets a list of the active rental
		ArrayList<Rental> rentals = getActiveRentals();
		//gets all the motorhomes from the database
		ArrayList<Motorhome> motorhomes = motorhomeCrudRepository.readAll();
		//for each active rental, look for a motorhome whose id matches the one
		//stored in that rental, and if found, remove the motorhome from the list
		//of motorhomes and skip to the next rental
        for(Rental rental : rentals) {
            for(int j = 0; j < motorhomes.size(); j++) {
                if(motorhomes.get(j).getId() == rental.getMotorhomeId()) {
                    motorhomes.remove(j);
                    break;
                }
            }
        }
		
		return motorhomes;
	}
	
	//adds attributes used in multiple places to a model
	private void prepareForm(Model model){
		//all customers
		model.addAttribute("customers", CustomerRepository.getInstance().readAll());
		//motorhomes not in an active rental
		model.addAttribute("motorhomes",getAvailableMotorhomes());
		//all possible accessories
		model.addAttribute("allAccessories", Accessory.ALL_ACCESSORIES);
		//the minimum start date for the rental, which is one week ahead of today's date
		model.addAttribute("minStartDate", LocalDate.now().plusWeeks(1));
		//the minimum end date for the rental, which is the next day after the start date
		model.addAttribute("minEndDate", LocalDate.now().plusWeeks(1).plusDays(1));
		//the current user
		model.addAttribute("currentUser", currentUser);
	}
}