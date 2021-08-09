package controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

	@GetMapping({ "/", "/index"})
    public String main() {
        return "index";
    }
}
