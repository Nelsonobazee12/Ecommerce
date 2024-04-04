package com.nelson.ecommerce_app.Repository;

import java.util.List;
import java.util.Optional;

import com.nelson.ecommerce_app.Token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from Token t inner join AppUser u\s
      on t.appUser.id = u.id\s
      where u.id = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}

