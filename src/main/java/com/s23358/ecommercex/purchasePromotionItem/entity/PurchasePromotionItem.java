package com.s23358.ecommercex.purchasePromotionItem.entity;

import com.s23358.ecommercex.promotion.entity.Promotion;
import com.s23358.ecommercex.purchase.entity.Purchase;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(
        name = "PurchasePromotionItem",
        uniqueConstraints = {@UniqueConstraint(
                name = "uk_promotion_purchase",
                columnNames = {
                        "promotion_id",
                        "purchase_id"
                }
        )}
)
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion refersToPromotion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;


}
