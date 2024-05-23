package com.davidruiz.barvendor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/")
    public String showWelcomePage() {
        return "home"; // Este es el nombre de la plantilla Thymeleaf (welcome.html)
    }
    @GetMapping("/home")
    public String home() {
        return "home"; // Este es el nombre de la plantilla Thymeleaf (welcome.html)
    }
}