package com.s23358.ecommercex.customer.entity;

import com.s23358.ecommercex.enums.AccountStatus;
import com.s23358.ecommercex.person.entity.Person;
import com.s23358.ecommercex.role.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

}
