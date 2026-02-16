package com.s23358.ecommercex.auth_users.service.impl;

import com.s23358.ecommercex.address.Address;
import com.s23358.ecommercex.admin.Admin;
import com.s23358.ecommercex.auth_users.dto.LoginRequest;
import com.s23358.ecommercex.auth_users.dto.LoginResponse;
import com.s23358.ecommercex.auth_users.dto.RegistrationCustomerRequest;
import com.s23358.ecommercex.auth_users.service.AuthService;
import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.customer.repository.CustomerRepository;
import com.s23358.ecommercex.enums.AccountStatus;
import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.person.entity.Person;
import com.s23358.ecommercex.person.repository.PersonRepository;
import com.s23358.ecommercex.res.Response;
import com.s23358.ecommercex.role.entity.Role;
import com.s23358.ecommercex.role.repository.RoleRepository;
import com.s23358.ecommercex.security.TokenService;
import com.s23358.ecommercex.wishList.entity.WishList;
import com.s23358.ecommercex.wishList.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    @Transactional
    public Response<String> register(RegistrationCustomerRequest request) {

        List<Role> roles;

//        if(request.getRoles() == null || request.getRoles().isEmpty()){
//            Role role = roleRepository.findByName("CUSTOMER")
//                    .orElseThrow(() -> new NotFoundException("CUSTOMER role not found"));
//            roles = Collections.singletonList(role);
//
//        }else{
//            roles = request.getRoles().stream()
//                    .map(roleName -> roleRepository.findByName(roleName)
//                            .orElseThrow(() -> new NotFoundException("Role not found"))).toList();
//        }
        Role role = roleRepository.findByName("CUSTOMER")
                    .orElseThrow(() -> new NotFoundException("CUSTOMER role not found"));
            roles = Collections.singletonList(role);



        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(AccountStatus.ACTIVE)
                .build();

        Address address = Address.builder()
                .street(request.getStreet())
                .streetNum(request.getStreetNum())
                .localNum(request.getLocalNum())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .isDefault(true)
                .build();

        customer.addAddress(address);


        Person person = Person.builder()
                .hasCustomer(customer)
                .hasGuest(null)
                .roles(roles)
                .build();


        WishList wishList = WishList.builder().build();

        person.setWishList(wishList);

        personRepository.save(person);


        return Response.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Your account has been created successfully")
                .data("Account has been created successfully")
                .build();
    }


    @Override
    public Response<LoginResponse> login(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Email not found"));

        if(!passwordEncoder.matches(password, customer.getPassword())){
            throw new BadRequestException("Wrong password");
        }

        String token = tokenService.generateToken(customer.getEmail());
        Person person = personRepository.findByHasCustomer(customer)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        LoginResponse loginResponse = LoginResponse.builder()
                .roles(person.getRoles().stream().map(Role::getName).toList())
                .token(token)
                .build();

        return Response.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login Successful")
                .data(loginResponse)
                .build();

    }
}
