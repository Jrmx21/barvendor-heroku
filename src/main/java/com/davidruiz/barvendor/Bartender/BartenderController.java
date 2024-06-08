package com.davidruiz.barvendor.Bartender;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bartender")
public class BartenderController {

    //Redirige a bartender.html
    
    @RequestMapping
    public String bartender() {
        return "bartender";
    }
}
