package com.airline.ticketbookingapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tickets")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    Long idTicket;

    @Column(name = "seat_number", nullable = false, length = 10)
    String seatNumber;

    @Column(name = "price", nullable = false)
    BigDecimal price;

    @Column(name = "is_available", nullable = false)
    Boolean isAvailable = Boolean.TRUE;;

    @Column(name = "description", length = 100)
    String description;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_flight", nullable = false)
    @JsonIgnore
    Flight flight;

    @OneToOne(mappedBy = "ticket", fetch = FetchType.LAZY)
    @JsonIgnore
    Reservation reservation;
}
