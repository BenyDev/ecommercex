package com.s23358.ecommercex.auth_users.controller;

import com.s23358.ecommercex.auth_users.dto.LoginRequest;
import com.s23358.ecommercex.auth_users.dto.LoginResponse;
import com.s23358.ecommercex.auth_users.dto.RegistrationCustomerRequest;
import com.s23358.ecommercex.auth_users.service.AuthService;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> register(@Valid @RequestBody RegistrationCustomerRequest request){
        return ResponseEntity.ok(authService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
