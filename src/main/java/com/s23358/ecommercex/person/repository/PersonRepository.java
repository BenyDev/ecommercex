package com.s23358.ecommercex.person.repository;

import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByHasCustomer(Customer customer);
    Optional<Person> findByHasCustomer_Email(String email);
}
