package com.nelson.ecommerce_app.Repository;

import com.nelson.ecommerce_app.Token.Token;
import com.nelson.ecommerce_app.Users.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value =
            """
                     select t from Token t inner join AppUser u  on t.appUser.id = u.id\s
                     where u.id = :id and (t.isExpired = false or t.isRevoked = false )\s
                    """
    )
    List<Token> findAllTokenByAppUserId(Long id);
    Optional<Token> findByToken(String token);
}
