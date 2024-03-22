package com.nelson.ecommerce_app.Service;

import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationRequest;
import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationResponse;
import com.nelson.ecommerce_app.Registration.RegistrationRequest;
import com.nelson.ecommerce_app.Repository.TokenRepository;
import com.nelson.ecommerce_app.Token.Token;
import com.nelson.ecommerce_app.Token.TokenType;
import com.nelson.ecommerce_app.Users.AppUser;
import com.nelson.ecommerce_app.Users.Role;
import com.nelson.ecommerce_app.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse registerNewUser(RegistrationRequest registration) {
        var user = AppUser.builder()
                .firstname(registration.getFirstname())
                .lastname(registration.getLastname())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .role(Role.USER)
                .build();
            var saveUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(saveUser, jwtToken);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }

    public AuthenticationResponse authenticateUsers(AuthenticationRequest authenticationRequest) {
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                           authenticationRequest.getPassword())
           );

            var appUser = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
            var jwtToken = jwtService.generateToken(appUser);
            revokeAllUserTokens(appUser);
            saveUserToken(appUser,jwtToken);
            return AuthenticationResponse.builder()
                 .token(jwtToken)
                 .build();
        }


    private void saveUserToken(AppUser appUser, String jwtToken) {
        var token = Token.builder()
                .appUser(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser appUser) {
        var validUserTokens = tokenRepository.findAllTokenByAppUserId(appUser.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}

