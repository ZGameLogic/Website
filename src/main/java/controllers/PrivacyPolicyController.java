package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyPolicyController {

    @GetMapping("/pp/BookNook")
    public String privacyPolicyBookNook(Model model){
        return "pp-book-nook";
    }
}
