package com.nelson.ecommerce_app.Controllers;


import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationRequest;
import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationResponse;
import com.nelson.ecommerce_app.Service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping()
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("AuthenticationRequest", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("AuthenticationRequest") AuthenticationRequest authenticationRequest,
                            RedirectAttributes redirectAttributes) {

        AuthenticationResponse response = authenticationService.authenticateUsers(authenticationRequest);
        return "redirect:/home";
    }
}