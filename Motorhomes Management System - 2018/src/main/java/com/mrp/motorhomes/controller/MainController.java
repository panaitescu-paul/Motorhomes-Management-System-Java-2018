package com.mrp.motorhomes.controller;

import com.mrp.motorhomes.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//#Marius & Paul & Razvan
//The main class, used to log into the system
@Controller
public class MainController {
	
	//static error messages for when submitting forms
	static final String ERROR_MESSAGE = "All fields are required." ;
	static final String ERROR_CUSTOMER = "Email must have an @ and end with .com or .dk. Phone and SSN must " +
			"contain only digits. Names must contain at least 3 letters.";
	static final String ERROR_MOTORHOME = "";
	static final String ERROR_RENTAL   = "Start date cannot be before today's " +
			"date. End date cannot be before start date.";
   
	//the current user logged in throughout the system
    static User currentUser = User.ALL_USERS[0];
	
    //shows the login page
	@GetMapping("/")
	public String login(Model model) {
		//adds the list of users to the Model, to let the user choose one
		model.addAttribute("users", User.ALL_USERS);
		model.addAttribute("userTypes", User.TYPES);
		return "login";
	}
	
	//logins with the chosen user
	@GetMapping("/login")
	public String login(@RequestParam("name") String name){
	    //finds the user with the given name and sets them as the current user
		for(User user : User.ALL_USERS) {
			if(user.getName().equals(name)){
				currentUser = user;
				break;
			}
		}
		
		System.out.println(currentUser);
		if(currentUser.getType().equals(User.TYPES[0])){
			return "redirect:/customers/";
		}
		else if(currentUser.getType().equals(User.TYPES[2])){
			return "redirect:/rentals/";
		}
		else if(currentUser.getType().equals(User.TYPES[1]) || currentUser.getType().equals(User.TYPES[3])){
			return "redirect:/motorhomes/";
		} else {
			return "redirect:/login/";
		}

	}
}