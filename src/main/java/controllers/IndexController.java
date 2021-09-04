package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import services.EmailSender;

@Controller
public class IndexController {
	
	@GetMapping({ "/", "/index" })
	public String main() {
		return "index";
	}

	@PostMapping("/response")
	public String response(
			@RequestParam("from") String from,
			@RequestParam("subject") String subject,
			@RequestParam("body") String body) {
		
		EmailSender.sendSimpleEmail(subject, body, from);
		
		return "response";
	}
	
}
