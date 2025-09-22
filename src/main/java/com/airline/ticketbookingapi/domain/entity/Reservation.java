package com.airline.ticketbookingapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    Long idReservation;

    @Column(name = "reservation_code", nullable = false, length = 30, unique = true)
    String reservationCode;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdDate;

    @Column(name = "is_cancelled", nullable = false, columnDefinition = " boleano predeterminado de FALSE")
    private boolean isCancelled;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_to_user"))
    @JsonIgnore
    User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket", nullable = false, foreignKey = @ForeignKey(name = "fk_reservation_to_ticket"))
    @JsonIgnore
    Ticket ticket;
}
