package com.s23358.ecommercex.role;

import com.s23358.ecommercex.role.entity.Role;
import com.s23358.ecommercex.role.repository.RoleRepository;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RoleInitialization implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role admin = Role.builder()
                .name("ADMIN").build();

        Role user = Role.builder()
                .name("USER").build();
        roleRepository.save(admin);
        roleRepository.save(user);
    }
}
