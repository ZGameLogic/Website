package controllers.API;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/health")
public class HealthCheck {
    @GetMapping
    public String health(){
        return "Healthy";
    }
}
