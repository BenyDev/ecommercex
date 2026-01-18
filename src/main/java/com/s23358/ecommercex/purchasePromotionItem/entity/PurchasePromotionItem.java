package com.s23358.ecommercex.purchasePromotionItem.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "PurchasePromotionItem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePromotionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Double price;
}
