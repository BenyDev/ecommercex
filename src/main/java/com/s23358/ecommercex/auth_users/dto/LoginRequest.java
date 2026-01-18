package com.s23358.ecommercex.auth_users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @Email
    @NotNull
    @NotBlank(message = "Email is required")
    private String email;


    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

}
