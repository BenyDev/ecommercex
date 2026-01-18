package com.s23358.ecommercex.admin.controller;

import com.s23358.ecommercex.admin.dto.RegistrationAdminRequest;
import com.s23358.ecommercex.admin.service.AdminService;
import com.s23358.ecommercex.res.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<Response<String>> registerAdmin(@Valid @RequestBody RegistrationAdminRequest request){
        return ResponseEntity.ok(adminService.register(request));
    }
}
