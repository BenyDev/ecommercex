package com.s23358.ecommercex.admin.dto;

import com.s23358.ecommercex.role.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RegistrationAdminRequest {

    @NotNull
    @NotBlank(message = "FirstName is required")
    private String firstName;

    @NotNull
    @NotBlank(message = "LastName is required")
    private String lastName;

    @NotNull
    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotBlank(message = "Email is required")
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String phoneNumber;


    private List<String> roles;


}
