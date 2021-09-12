package controllers;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import services.EmailSender;

@Controller
public class IndexController {
	
	@GetMapping({ "/", "/index", "/home" })
	public String main(Model model) {
		addPages(model);
		return "index";
	}

	@PostMapping("/response")
	public String response(
			@RequestParam("from") String from,
			@RequestParam("subject") String subject,
			@RequestParam("body") String body, Model model) {
		
		EmailSender.sendSimpleEmail(subject, body, from);
		addPages(model);
		
		return "response";
	}
	
	public static void addPages(Model model){
		LinkedList<String> pages = new LinkedList<String>();
		pages.add("Home");
		pages.add("Projects");
		pages.add("About me");
		pages.add("Resume");
		
		model.addAttribute("headers", pages);
	}
	
}
