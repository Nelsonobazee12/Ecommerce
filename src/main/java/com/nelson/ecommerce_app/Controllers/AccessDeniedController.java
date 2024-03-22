package com.nelson.ecommerce_app.Controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // return the name of the HTML view for the access denied page
    }
}
