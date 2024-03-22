package com.nelson.ecommerce_app.Configuration.AuthenticationConfig;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
}
