package com.nelson.ecommerce_app.Exception;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@AllArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;


    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // You can perform actions here based on your application's requirements
        // For example, you can redirect the user to a login page or display an access denied message
        response.sendRedirect(errorPage);
    }
}
