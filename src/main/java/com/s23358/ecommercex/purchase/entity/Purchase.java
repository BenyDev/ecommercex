package com.s23358.ecommercex.purchase.entity;

import com.s23358.ecommercex.address.Address;
import com.s23358.ecommercex.enums.PurchaseState;
import com.s23358.ecommercex.purchaseItem.entity.PurchaseItem;
import com.s23358.ecommercex.purchasePromotionItem.entity.PurchasePromotionItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Purchase")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private int totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseState state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveredToAddress;

    @OneToMany(mappedBy = "refersToPurchase", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> containsProductItems = new ArrayList<>();

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchasePromotionItem> containsPromotionItems = new ArrayList<>();


    public void addItem(PurchaseItem item) {
        if(item == null) throw new IllegalArgumentException("item cannot be null");
        item.setRefersToPurchase(this);
        containsProductItems.add(item);
    }
}
