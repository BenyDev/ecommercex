package com.s23358.ecommercex.wishListItem.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "WishListItem")
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


}
