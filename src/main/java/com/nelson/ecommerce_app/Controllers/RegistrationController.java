package com.nelson.ecommerce_app.Controllers;

import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationResponse;
import com.nelson.ecommerce_app.Registration.RegistrationRequest;
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
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

     private final AuthenticationService authenticationService;
     @GetMapping("/registration-form")
     public String showRegistrationForm(Model model) {
         model.addAttribute("registrationRequest", new RegistrationRequest());
         return "registration";
     }

     @PostMapping("/register")
     public String registerUser(@ModelAttribute("registrationRequest")
                                     RegistrationRequest registrationRequest,
                                RedirectAttributes redirectAttributes) {
         try {
             AuthenticationResponse response = authenticationService.registerNewUser(registrationRequest);
             redirectAttributes.addAttribute("success",true);
             return "redirect:/registration/registration-form?success";
         }catch (Exception e) {
             redirectAttributes.addAttribute("error",true);
             return "redirect:/registration/registration-form?error";
         }

     }

}
