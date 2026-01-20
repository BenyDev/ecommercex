package com.s23358.ecommercex.wishList.entity;

import com.s23358.ecommercex.person.entity.Person;
import com.s23358.ecommercex.wishListItem.entity.WishListItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WishList")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, unique = true)
    private Person ownedBy;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "belongsTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishListItem> containsItems = new ArrayList<>();

    @PrePersist
    void onCreateEntity(){
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = createdAt;
    }
}
