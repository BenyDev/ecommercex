package com.s23358.ecommercex.admin.service;


import com.s23358.ecommercex.admin.dto.RegistrationAdminRequest;
import com.s23358.ecommercex.res.Response;

public interface AdminService {
    Response<String> register(RegistrationAdminRequest request);
}
