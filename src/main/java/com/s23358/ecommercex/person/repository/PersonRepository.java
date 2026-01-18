package com.s23358.ecommercex.person.repository;

import com.s23358.ecommercex.person.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
