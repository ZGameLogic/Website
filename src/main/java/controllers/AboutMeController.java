package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutMeController {

	
	@GetMapping("/about me")
	public String aboutme(Model model) {
		IndexController.addPages(model);
		return "aboutme";
	}
}
