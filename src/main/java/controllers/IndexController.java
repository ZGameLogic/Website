package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
	
	@Autowired
    private JavaMailSender emailSender;
	
	@GetMapping({ "/", "/index" })
	public String main() {
		return "index";
	}

	@PostMapping("/response")
	public String response(
			@RequestParam("from") String from,
			@RequestParam("subject") String subject,
			@RequestParam("body") String body) {
		
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(from);
        message.setTo("ben@zgamelogic.com"); 
        message.setSubject(subject); 
        message.setText(body + "\nThis was submitted via the website");
        emailSender.send(message);
		
		return "response";
	}
	
}
