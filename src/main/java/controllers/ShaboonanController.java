package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShaboonanController {

	@GetMapping("/shaboonan")
    public String main() {
        return "shaboonan";
    }
}
