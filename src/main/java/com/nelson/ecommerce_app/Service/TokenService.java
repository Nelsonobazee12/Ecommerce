package com.nelson.ecommerce_app.Service;

import com.nelson.ecommerce_app.Repository.TokenRepository;
import com.nelson.ecommerce_app.Repository.UserRepository;
import com.nelson.ecommerce_app.Token.Token;
import com.nelson.ecommerce_app.Users.AppUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Token> getTokensForUser(Long userId) {
        // Accessing the tokens collection within a transactional method
        AppUser user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getTokens();
        } else {
            return Collections.emptyList();
        }
    }
}
