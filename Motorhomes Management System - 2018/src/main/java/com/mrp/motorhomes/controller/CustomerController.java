package com.mrp.motorhomes.controller;

import com.mrp.motorhomes.form.CustomerForm;
import com.mrp.motorhomes.model.Customer;
import com.mrp.motorhomes.repositories.CrudRepository;
import com.mrp.motorhomes.repositories.CustomerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.mrp.motorhomes.controller.MainController.currentUser;

//#Marius
//Controller responsible for handling operations on the customers
@Controller
public class CustomerController {
	private CrudRepository<Customer> repository;
	
	public CustomerController(){
		//grabs the instance of the customer database repository
		repository = CustomerRepository.getInstance();
	}

	
	//main page for the customers, showing a table with all the customers
	@GetMapping("/customers")
	public String index(Model model){
		//adds the list of customers to the Model
		model.addAttribute("customers", repository.readAll());
		//adds the current user to the Model
		model.addAttribute("currentUser", currentUser);
		return "customers/index";
	}

	//Shows the page to create a new customer
	@GetMapping("/customers/create")
	public String create(Model model){
		//Creates and passes a new customer form to the Model
		model.addAttribute("customerForm", new CustomerForm());
		model.addAttribute("currentUser", currentUser);
		return "customers/create";
	}
	//Processes the form
	@PostMapping("/customers/create")
	public String create(Model model, @ModelAttribute("customerForm") CustomerForm customerForm){
		System.out.println(customerForm);
		
		//validates the fields entered by the user
		if(!customerForm.validate()){
			//some field were invalid, show an error message
			model.addAttribute("errorMessage", MainController.ERROR_MESSAGE + " " + MainController.ERROR_CUSTOMER);
            model.addAttribute("currentUser", currentUser);
			//redirect to the creation page
			return "customers/create";
		}
		else
		{
			//all field were valid, convert the form object to Customer
			//and add them to the database
			repository.create(customerForm.toModel());
			//return to the index page for customers
			return "redirect:/customers/";
		}
	}
	
	//page that shows details for a customer
	@GetMapping("/customers/details")
	public String details(@RequestParam("id") int id, Model model){
		//reads the customer from the database and adds them to the Model
		model.addAttribute("customer", repository.read(id));
		model.addAttribute("currentUser", currentUser);
		return "customers/details";
	}
	
	//**UNUSED**
	//updates a customer in the database
	@PostMapping("/customer/details/update")
	public String update(@ModelAttribute Customer customer) {
		repository.update(customer);
		return "redirect:/customer/details/";
	}
	
	//deletes a customer from the database
	@GetMapping("/customers/delete")
	public String delete(@RequestParam("id") int id){
		//deletes the customer with the given id
		repository.delete(id);
		return "redirect:/customers/";
	}
}