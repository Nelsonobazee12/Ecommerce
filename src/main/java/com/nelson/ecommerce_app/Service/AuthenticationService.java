package com.nelson.ecommerce_app.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationRequest;
import com.nelson.ecommerce_app.Configuration.AuthenticationConfig.AuthenticationResponse;
import com.nelson.ecommerce_app.Exception.UserAlreadyExistException;
import com.nelson.ecommerce_app.Registration.RegistrationRequest;
import com.nelson.ecommerce_app.Repository.TokenRepository;
import com.nelson.ecommerce_app.Token.Token;
import com.nelson.ecommerce_app.Token.TokenType;
import com.nelson.ecommerce_app.Users.AppUser;
import com.nelson.ecommerce_app.Users.Role;
import com.nelson.ecommerce_app.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse registerNewUser(RegistrationRequest registration) {
        // Build the new user object
        var user = AppUser.builder()
                .firstname(registration.getFirstname())
                .lastname(registration.getLastname())
                .email(registration.getEmail())
                .password(passwordEncoder.encode(registration.getPassword()))
                .role(registration.getRole())
                .isEnabled(String.valueOf(true))
                .build();

        // Check if user already exists
        Optional<AppUser> userExists = userRepository.findByEmail(user.getEmail());
        if (userExists.isPresent()) {
            throw new UserAlreadyExistException("User already exists with this email");
        }

        // Save the new user
        var savedUser = userRepository.save(user);

        // Generate JWT token and refresh token
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        // Save user token
        saveUserToken(savedUser, jwtToken);

        // Build and return authentication response
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
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
            var refreshToken = jwtService.generateRefreshToken(appUser);
            revokeAllUserTokens(appUser);
            saveUserToken(appUser,jwtToken);
            return AuthenticationResponse.builder()
                 .accessToken(jwtToken)
                    .refreshToken(refreshToken)
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
        var validUserTokens = tokenRepository.findAllValidTokenByUser(appUser.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}


