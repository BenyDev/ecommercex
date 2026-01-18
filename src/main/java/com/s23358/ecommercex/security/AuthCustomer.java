package com.s23358.ecommercex.security;

import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.person.entity.Person;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Data
public class AuthCustomer implements UserDetails {

//    private Customer customer;
    private Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public  String getPassword() {
        return person.getHasCustomer().getPassword();
//        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getHasCustomer().getEmail();
//        return customer.getEmail();
    }
}
