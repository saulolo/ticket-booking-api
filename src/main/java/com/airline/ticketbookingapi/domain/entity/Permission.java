package com.airline.ticketbookingapi.domain.entity;

import com.airline.ticketbookingapi.domain.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permission")
    Long idPermission;

    @Column(name = "name", unique = true, updatable = false, nullable = false)
    private String name;
}
