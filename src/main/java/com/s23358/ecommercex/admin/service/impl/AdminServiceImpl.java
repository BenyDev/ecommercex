package com.s23358.ecommercex.admin.service.impl;

import com.s23358.ecommercex.admin.Admin;
import com.s23358.ecommercex.admin.dto.RegistrationAdminRequest;
import com.s23358.ecommercex.admin.service.AdminService;
import com.s23358.ecommercex.customer.repository.CustomerRepository;
import com.s23358.ecommercex.enums.AccountStatus;
import com.s23358.ecommercex.exception.BadRequestException;
import com.s23358.ecommercex.exception.NotFoundException;
import com.s23358.ecommercex.person.entity.Person;
import com.s23358.ecommercex.person.repository.PersonRepository;
import com.s23358.ecommercex.res.Response;
import com.s23358.ecommercex.role.entity.Role;
import com.s23358.ecommercex.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    @Override
    @Transactional
    public Response<String> register(RegistrationAdminRequest request) {

//        List<Role> roles;
//
//
//
//        if(request.getRoles() == null || request.getRoles().isEmpty()){
//            Role role = roleRepository.findByName("ADMIN")
//                    .orElseThrow(() -> new NotFoundException("ADMIN role not found"));
//            roles = Collections.singletonList(role);
//        }else{
//            roles = request.getRoles().stream()
//                    .map(roleName -> roleRepository.findByName(roleName)
//                            .orElseThrow(() -> new NotFoundException("Role not found"))).toList();
//        }
//        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
//            throw new BadRequestException("Email already exists");
//        }
//
//        Admin admin = Admin.builder()
//                .firstName(request.getFirstName())
//                .lastName(request.getLastName())
//                .password(request.getPassword())
//                .email(request.getEmail())
//                .roles(roles)
//                .phoneNumber(request.getPhoneNumber())
//                .lastLoginAt(LocalDateTime.now())
//                .isActive(true)
//                .status(AccountStatus.ACTIVE)
//                .build();
//        Admin savedAdmin = customerRepository.save(admin);
//
//        Person person = Person.builder()
//                    .hasCustomer(savedAdmin)
//                    .hasGuest(null)
//                    .build();
//        personRepository.save(person);


        return Response.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Your account has been created successfully")
                .data("Admin account has been created successfully")
                .build();
    }
}
