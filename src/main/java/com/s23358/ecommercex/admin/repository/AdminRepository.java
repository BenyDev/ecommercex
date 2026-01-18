package com.s23358.ecommercex.admin.repository;

import com.s23358.ecommercex.admin.Admin;
import com.s23358.ecommercex.admin.dto.RegistrationAdminRequest;
import com.s23358.ecommercex.res.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Long> {
}
