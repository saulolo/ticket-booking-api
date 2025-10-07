package com.airline.ticketbookingapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    Long idUser;

    @Column(name = "name", nullable = false, length = 30)
    String name;

    @Column(name = "username", nullable = false, length = 30, unique = true)
    String username;

    @Column(name = "password", nullable = false)
    String password;

    @Builder.Default
    @Column(name = "is_enabled")
    boolean isEnabled = true;

    @Builder.Default
    @Column(name = "account_no_expired")
    boolean accountNoExpired = true;

    @Builder.Default
    @Column(name = "account_no_locked")
    boolean accountNoLocked = true;

    @Builder.Default
    @Column(name = "credential_no_expired")
    boolean credentialNoExpired = true;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    List<Reservation> reservations;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
    Set<Role> roles = new HashSet<>();

}
