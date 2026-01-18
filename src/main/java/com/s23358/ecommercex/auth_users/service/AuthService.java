package com.s23358.ecommercex.auth_users.service;

import com.s23358.ecommercex.auth_users.dto.LoginRequest;
import com.s23358.ecommercex.auth_users.dto.LoginResponse;
import com.s23358.ecommercex.auth_users.dto.RegistrationCustomerRequest;
import com.s23358.ecommercex.res.Response;

public interface AuthService {
    Response<String> register(RegistrationCustomerRequest registrationCustomerRequest);
//    Response<LoginResponse> login(LoginRequest loginRequest);
}
