package com.s23358.ecommercex.purchaseItem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PurchaseItem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Double price;
}
