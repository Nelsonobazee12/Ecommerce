package com.nelson.ecommerce_app.Users;


import com.nelson.ecommerce_app.Token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class AppUser implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String isEnabled;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "appUser")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) {
            // Return an empty collection or handle the case appropriately
            return Collections.emptyList();
        }
        return role.getAuthorities();
    }

    public void setRole(Role role) {
        if (role == null) {
            this.role = Role.USER;
        } else {
            this.role = role;
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


