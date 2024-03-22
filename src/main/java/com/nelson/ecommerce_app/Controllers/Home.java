package com.nelson.ecommerce_app.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class Home {

    @GetMapping()
    public String homePage() {
        return "home";
    }
}
