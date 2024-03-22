package com.nelson.ecommerce_app.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/service")
public class ServiceController {

    @GetMapping()
    public String serviceLayer() {
        return "service";
    }
}
