package com.s23358.ecommercex.auth_users.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RegistrationCustomerRequest {

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

//    private List<String> roles;

    @NotNull
    @NotBlank
    private String street;

    @NotNull
    @NotBlank
    private String streetNum;

    @NotNull
    @NotBlank
    private String localNum;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String zipCode;

    @NotNull
    @NotBlank
    private String country;

}
