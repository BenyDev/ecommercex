package com.s23358.ecommercex.customer.entity;

import com.s23358.ecommercex.address.Address;
import com.s23358.ecommercex.discount.entity.Discount;
import com.s23358.ecommercex.enums.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Customer")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Customer  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "belongsTo" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
            @Builder.Default
    List<Address> hasAddresses =  new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "belongsTo")
            @Builder.Default
    List<Discount> receivesDiscounts = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void validateAddresses() {
        if (hasAddresses == null || hasAddresses.isEmpty()) {
            throw new IllegalStateException("Customer must have at least one address");
        }
        if (hasAddresses.size() > 3) {
            throw new IllegalStateException("Customer can have at most 3 addresses");
        }
    }

    public void addAddress(Address address) {
        if(hasAddresses.size() >= 3) throw new IllegalStateException("Customer can have at most 3 addresses");
        address.setBelongsTo(this);
        hasAddresses.add(address);
    }
    public void removeAddress(Address address) {
        if (hasAddresses.size() <= 1) throw new IllegalStateException("Customer must have at least one address");
        hasAddresses.remove(address);
        address.setBelongsTo(null);
    }



}
