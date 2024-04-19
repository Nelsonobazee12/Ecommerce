package com.nelson.ecommerce_app;

import com.nelson.ecommerce_app.Registration.RegistrationRequest;
import com.nelson.ecommerce_app.Service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import static com.nelson.ecommerce_app.Users.Role.ADMIN;

@SpringBootApplication
@ComponentScan(basePackages = {"com.nelson"})
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegistrationRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("admin@mail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            System.out.println(STR."Admin token: \{service.registerNewUser(admin).getAccessToken()}");

//            var manager = RegistrationRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
//                    .role(MANAGER)
//                    .build();
//            System.out.println(STR."Manager token: \{service.registerNewUser(manager).getAccessToken()}");


}
