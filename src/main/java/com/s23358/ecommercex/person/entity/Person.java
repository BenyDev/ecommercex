package com.s23358.ecommercex.person.entity;

import com.s23358.ecommercex.Guest.entity.Guest;
import com.s23358.ecommercex.customer.entity.Customer;
import com.s23358.ecommercex.role.entity.Role;
import com.s23358.ecommercex.wishList.entity.WishList;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.FetchMode;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Person")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "hasProfilCustomer", unique = true)
    private Customer hasCustomer;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "hasProfilGuest")
    private Guest hasGuest;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @OneToOne(mappedBy = "ownedBy", orphanRemoval = true, cascade = CascadeType.ALL, optional = true)
    private WishList hasWishList;

    public void setWishList(WishList wishList) {
        this.hasWishList = wishList;
        if(wishList != null) {
            wishList.setOwnedBy(this);
        }
    }
}
