package com.nelson.ecommerce_app.Security;

import com.nelson.ecommerce_app.Exception.CustomAccessDeniedHandler;
import com.nelson.ecommerce_app.Configuration.JwtConfiguration.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.nelson.ecommerce_app.Users.Permission.*;
import static com.nelson.ecommerce_app.Users.Role.ADMIN;
import static org.springframework.http.HttpMethod.*;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        logger.debug("Configuring security filter chain");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(authorize ->
                        authorize

                                .requestMatchers(String.valueOf(PathRequest.
                                        toStaticResources().atCommonLocations()))
                                .permitAll()
                                .requestMatchers(
                                        "/registration/**"
                                        )
                                .permitAll()

                                .requestMatchers("/management/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(PUT, "/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                .anyRequest().authenticated()
                )

                .logout((logout) ->
                        logout
                                .logoutUrl("/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                                .logoutSuccessHandler((request, response, authentication) ->
                                        SecurityContextHolder.clearContext())
                )


                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .accessDeniedHandler(new CustomAccessDeniedHandler("/access-denied"))
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
