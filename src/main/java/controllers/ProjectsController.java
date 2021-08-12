package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectsController {

	@GetMapping("projects")
    public String main() {
        return "projects";
    }
}
