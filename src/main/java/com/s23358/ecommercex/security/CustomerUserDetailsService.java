package com.s23358.ecommercex.security;

import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.customer.repository.CustomerRepository;
import com.s23358.ecommercex.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = customerRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundException(String.format("Username %s not found", username)));
        return AuthCustomer.builder()
                .customer(user)
                .build();
    }
}
