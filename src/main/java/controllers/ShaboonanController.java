package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShaboonanController {

	@GetMapping("/shaboonan")
    public String main(Model model) {
		IndexController.addPages(model);
        return "shaboonan";
    }
}
