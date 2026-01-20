package com.s23358.ecommercex.product.entity;

import com.s23358.ecommercex.brand.entity.Brand;
import com.s23358.ecommercex.category.entity.Category;
import com.s23358.ecommercex.enums.Unit;
import com.s23358.ecommercex.promotion.entity.Promotion;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private BigDecimal weight;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "images",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(nullable = false)
    private boolean isActive;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category belongsToCategory;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Promotion> hasPromotions = new ArrayList<>();


}
