package com.mrp.motorhomes.controller;

import com.mrp.motorhomes.form.MotorhomeForm;
import com.mrp.motorhomes.model.Motorhome;
import com.mrp.motorhomes.repositories.CrudRepository;
import com.mrp.motorhomes.repositories.MotorhomeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.mrp.motorhomes.controller.MainController.currentUser;

//#Constantin
//Controller responsible for handling operations on the motorhomes
@Controller
public class MotorhomeController {
	private CrudRepository<Motorhome> repository;
	
	public MotorhomeController() {
        //grabs the instance of the motorhome database repository
		repository = MotorhomeRepository.getInstance();
	}
    
    //main page for the motorhomes, showing a table with all the motorhomes
	@GetMapping("/motorhomes")
	public String index(Model model) {
        //adds the list of motorhomes to the Model
		model.addAttribute("motorhomes", repository.readAll());
        //adds the current user to the Model
		model.addAttribute("currentUser", currentUser);
		return "motorhomes/index";
	}
    
    //Shows the page to create a new motorhome
	@GetMapping("/motorhomes/create")
	public String create(Model model) {
		//Creates and passes a new motorhome form to the Model
		model.addAttribute("motorhomeForm", new MotorhomeForm());
		//Adds the motorhome types to the Model, used in the dropdown to choose a type
		model.addAttribute("types", Motorhome.TYPES);
		model.addAttribute("currentUser", currentUser);
		return "motorhomes/create";
	}
	
    //Processes the form
	@PostMapping("/motorhomes/create")
	public String create(Model model, @ModelAttribute("motorhomeForm") MotorhomeForm motorhomeForm) {
		System.out.println(motorhomeForm);
        
        //validates the fields entered by the user
		if(!motorhomeForm.validate()){
            //some field were invalid, show an error message
			model.addAttribute("errorMessage", MainController.ERROR_MESSAGE + " " + MainController.ERROR_MOTORHOME);
			model.addAttribute("types", Motorhome.TYPES);
			model.addAttribute("currentUser", currentUser);
            //redirect to the creation page
			return "motorhomes/create";
		}
		else {
			//all field were valid, convert the form object to Motorhome
			//and add it to the database
			repository.create(motorhomeForm.toModel());
            //return to the index page for motorhomes
			return "redirect:/motorhomes/";
		}
	}
    
    //page that shows details for a motorhome
	@GetMapping("/motorhomes/details")
	public String details(@RequestParam("id") int id, Model model) {
        //reads the motorhome from the database and adds it to the Model
        model.addAttribute("motorhome", repository.read(id));
		model.addAttribute("currentUser", currentUser);
		return "motorhomes/details";
	}

	//"cleans" or "uncleans" a motorhome
	@GetMapping("/motorhomes/clean")
	public String clean(@RequestParam("id") int id) {
	    //reads the motorhome from the database
		Motorhome motorhome = repository.read(id);
		//inverts the current cleaning state of the motorhome
		motorhome.setCleaned(!motorhome.isCleaned());
		//updates the motorhome in the database
		repository.update(motorhome);
		return "redirect:/motorhomes/";
	}

	//"services" or "unservices" a motorhome
	@GetMapping("/motorhomes/service")
	public String service(@RequestParam("id") int id) {
        //reads the motorhome from the database
		Motorhome motorhome = repository.read(id);
        //inverts the current service state of the motorhome
        motorhome.setServiced(!motorhome.isServiced());
        //updates the motorhome in the database
		repository.update(motorhome);
		return "redirect:/motorhomes/";
	}

	//**UNUSED**
	//updates a motorhome in the database
	@PostMapping("/motorhomes/details/update")
	public String update(@ModelAttribute Motorhome motorhome) {
        repository.update(motorhome);
        return "redirect:/motorhomes/";
    }

    //deletes a motorhome from the database
	@GetMapping("/motorhomes/delete")
	public String delete(@RequestParam("id") int id) {
        //deletes the motorhome with the given id
        repository.delete(id);
        return "redirect:/motorhomes/";
	}

}