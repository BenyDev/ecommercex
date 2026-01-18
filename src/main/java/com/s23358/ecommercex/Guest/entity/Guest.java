package com.s23358.ecommercex.Guest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Guest")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionToken;
}
