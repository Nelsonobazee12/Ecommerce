package com.nelson.ecommerce_app.AuthenticationConfig;

import com.nelson.ecommerce_app.AuthenticationConfig.Registration.RegistrationRequest;
import com.nelson.ecommerce_app.Exception.UserAlreadyExistException;
import com.nelson.ecommerce_app.JwtConfiguration.JwtService;
import com.nelson.ecommerce_app.Users.AppUser;
import com.nelson.ecommerce_app.Users.Role;
import com.nelson.ecommerce_app.Users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerNewUser(RegistrationRequest registration) {
        var user = AppUser.builder()
                .firstname(registration.getFirstname())
                .lastname(registration.getLastname())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .role(Role.USER)
                .build();
        var theUser = userRepository.findByEmail(user.getEmail());
        if (theUser.isEmpty()) {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
            throw new UserAlreadyExistException("User already exist");
        }
    }

    public AuthenticationResponse authenticateUsers(AuthenticationRequest authenticationRequest) {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                           authenticationRequest.getPassword())
           );
           var user = userRepository.findByEmail(authenticationRequest.getEmail())
                   .orElseThrow(()-> new UsernameNotFoundException("User name not found"));
           var jwtToken = jwtService.generateToken(user);
           return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
