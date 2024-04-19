package com.nelson.ecommerce_app.Controllers;

import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationRequest;
import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationResponse;
import com.nelson.ecommerce_app.Registration.RegistrationRequest;
import com.nelson.ecommerce_app.Service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("registration")
public class RegistrationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegistrationRequest request) {

        return ResponseEntity.ok(authenticationService.registerNewUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(authenticationService.authenticateUsers(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

}
