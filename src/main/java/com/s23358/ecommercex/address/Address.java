package com.s23358.ecommercex.address;

import jakarta.persistence.*;
import lombok.*;

import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String streetNum;

    @Column(nullable = false)
    private String localNum;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private boolean isDefault;
}
