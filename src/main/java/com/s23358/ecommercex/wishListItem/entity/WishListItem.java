package com.s23358.ecommercex.wishListItem.entity;


import com.s23358.ecommercex.product.entity.Product;
import com.s23358.ecommercex.wishList.entity.WishList;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "WishListItem",
        uniqueConstraints = {@UniqueConstraint(
                name = "uk_wishlist_product",
                columnNames = {
                        "product_id",
                        "wishlist_id"
                })}

)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    private int quantity;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime addedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product containProduct;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_id", nullable = false)
    private WishList belongsTo;

    @PrePersist
    void onInsert(){
        if (addedAt == null) addedAt = LocalDateTime.now();
    }


}
