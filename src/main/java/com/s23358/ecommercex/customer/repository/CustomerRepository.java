package com.s23358.ecommercex.customer.repository;

import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.res.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);


}
