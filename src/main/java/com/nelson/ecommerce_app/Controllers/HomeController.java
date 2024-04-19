package com.nelson.ecommerce_app.Controllers;

import com.nelson.ecommerce_app.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("hello from secured endpoint home");
    }

}
