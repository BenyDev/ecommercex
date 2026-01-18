package com.s23358.ecommercex.admin;

import com.s23358.ecommercex.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "Admin")
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends Customer {

    @Column(nullable = false)
    private LocalDateTime lastLoginAt;

    @Column(nullable = false)
    private boolean isActive;

}
