package com.nelson.ecommerce_app.Registration;


import com.nelson.ecommerce_app.Users.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role = Role.USER;
}
