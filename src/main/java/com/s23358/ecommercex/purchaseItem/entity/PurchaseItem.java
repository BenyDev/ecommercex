package com.s23358.ecommercex.purchaseItem.entity;

import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.purchase.entity.Purchase;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(
        name = "PurchaseItem",
        uniqueConstraints = {@UniqueConstraint(
                name = "uk_product_purchase",
                columnNames = {
                        "product_id",
                        "purchase_id"
                }
        )}

)
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
    @Min(1)
    private int quantity;

    @Column(nullable = false)
    @Min(0)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product refersToProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase refersToPurchase;
}
