package com.nelson.ecommerce_app.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class ServiceController {
    @GetMapping("/service")
    public ResponseEntity<String> service() {
        return ResponseEntity.ok("hello from secured endpoint service");
    }
}
