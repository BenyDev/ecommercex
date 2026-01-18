package com.s23358.ecommercex.person.entity;

import com.s23358.ecommercex.Guest.entity.Guest;
import com.s23358.ecommercex.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

@Entity
@Table(name = "Person")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "hasProfilCustomer", unique = true)
    private Customer hasCustomer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "hasProfilGuest")
    private Guest hasGuest;
}
